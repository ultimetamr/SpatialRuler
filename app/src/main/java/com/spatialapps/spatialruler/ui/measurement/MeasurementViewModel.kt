package com.spatialapps.spatialruler.ui.measurement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spatialapps.spatialruler.data.repository.MeasurementRepository
import com.spatialapps.spatialruler.data.repository.MeasurementSnapshotRepository
import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementRecord
import com.spatialapps.spatialruler.domain.usecase.CalculateMeasurementUseCase
import com.spatialapps.spatialruler.domain.usecase.FormatMeasurementUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MeasurementViewModel(
    private val repository: MeasurementRepository,
    private val calculate: CalculateMeasurementUseCase = CalculateMeasurementUseCase(),
    private val format: FormatMeasurementUseCase = FormatMeasurementUseCase(),
    private val now: () -> Long = System::currentTimeMillis,
    private val snapshotRepository: MeasurementSnapshotRepository? = null,
    private val mutableState: MutableStateFlow<MeasurementUiState> = MutableStateFlow(MeasurementUiState()),
) : ViewModel() {
    val state: StateFlow<MeasurementUiState> = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeAll()
                .catch { error -> mutableState.update { it.copy(message = "读取历史失败：${error.message.orEmpty()}") } }
                .collect { records -> mutableState.update { it.copy(history = records) } }
        }
    }

    fun onEvent(event: MeasurementEvent) {
        when (event) {
            is MeasurementEvent.SelectMode -> reset(mode = event.mode)
            is MeasurementEvent.SelectUnit -> mutableState.update {
                it.copy(unit = event.unit, displayValue = format(it.value, event.unit))
            }
            is MeasurementEvent.AddPoint -> addPoint(event)
            MeasurementEvent.Undo -> updatePoints(mutableState.value.points.dropLast(1), "已撤销上一个标记点")
            MeasurementEvent.Clear -> reset(message = "已清除全部测量")
            MeasurementEvent.Finish -> finish()
            MeasurementEvent.Capture -> capture()
            is MeasurementEvent.Save -> save(event.name)
            is MeasurementEvent.Rename -> viewModelScope.launch { repository.rename(event.id, event.name) }
            is MeasurementEvent.Delete -> viewModelScope.launch { repository.delete(event.id) }
            is MeasurementEvent.SystemMessage -> mutableState.update { it.copy(message = event.text) }
        }
    }

    private fun addPoint(event: MeasurementEvent.AddPoint) {
        val current = mutableState.value
        if (current.isCompleted) return
        val limit = when (current.mode) {
            MeasurementMode.CONTINUOUS -> Int.MAX_VALUE
            else -> current.mode.minimumPoints
        }
        if (current.points.size >= limit) return
        val points = current.points + event.point
        val autoComplete = current.mode == MeasurementMode.AREA && points.size == 4
        updatePoints(points, if (autoComplete) "面积测量完成" else "已放置第 ${points.size} 个标记点", autoComplete)
    }

    private fun updatePoints(points: List<com.spatialapps.spatialruler.domain.model.SpatialPoint>, message: String, completed: Boolean = false) {
        mutableState.update { current ->
            val value = calculate(current.mode, points)
            current.copy(
                points = points,
                value = value,
                displayValue = format(value, current.unit),
                isCompleted = completed,
                message = message,
            )
        }
    }

    private fun finish() {
        mutableState.update { current ->
            if (current.value == null) current.copy(message = "标记点不足，暂时无法完成")
            else current.copy(isCompleted = true, message = "测量完成，可命名保存")
        }
    }

    private fun save(name: String) {
        val snapshot = mutableState.value
        val value = snapshot.value ?: return
        viewModelScope.launch {
            mutableState.update { it.copy(isSaving = true, message = "正在保存") }
            runCatching {
                repository.save(
                    MeasurementRecord(
                        name = name.trim().ifBlank { "未命名测量" },
                        mode = snapshot.mode,
                        value = value,
                        points = snapshot.points,
                        displayUnit = snapshot.unit,
                        screenshotUri = snapshot.screenshotUri,
                        createdAtEpochMillis = now(),
                    ),
                )
            }.onSuccess {
                mutableState.update { it.copy(isSaving = false, message = "已保存") }
            }.onFailure { error ->
                mutableState.update { it.copy(isSaving = false, message = "保存失败：${error.message.orEmpty()}") }
            }
        }
    }

    private fun capture() {
        val repository = snapshotRepository
        if (repository == null) {
            mutableState.update { it.copy(message = "当前环境不支持保存快照") }
            return
        }
        val snapshot = mutableState.value
        viewModelScope.launch {
            runCatching { repository.capture(snapshot) }
                .onSuccess { uri -> mutableState.update { it.copy(screenshotUri = uri, message = "快照已保存到系统相册") } }
                .onFailure { error -> mutableState.update { it.copy(message = "快照失败：${error.message.orEmpty()}") } }
        }
    }

    private fun reset(
        mode: MeasurementMode = mutableState.value.mode,
        message: String = "将射线对准真实平面，点击放置标记点",
    ) {
        mutableState.update {
            it.copy(
                mode = mode,
                points = emptyList(),
                value = null,
                displayValue = "等待标记",
                isCompleted = false,
                screenshotUri = null,
                message = message,
            )
        }
    }
}
