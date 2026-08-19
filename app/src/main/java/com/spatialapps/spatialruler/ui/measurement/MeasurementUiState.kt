package com.spatialapps.spatialruler.ui.measurement

import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementRecord
import com.spatialapps.spatialruler.domain.model.MeasurementUnit
import com.spatialapps.spatialruler.domain.model.MeasurementValue
import com.spatialapps.spatialruler.domain.model.SpatialPoint

data class MeasurementUiState(
    val mode: MeasurementMode = MeasurementMode.STRAIGHT,
    val unit: MeasurementUnit = MeasurementUnit.CENTIMETER,
    val points: List<SpatialPoint> = emptyList(),
    val value: MeasurementValue? = null,
    val displayValue: String = "等待标记",
    val isCompleted: Boolean = false,
    val isSaving: Boolean = false,
    val screenshotUri: String? = null,
    val history: List<MeasurementRecord> = emptyList(),
    val message: String = "将射线对准平面，捏合放置标记点",
)

sealed interface MeasurementEvent {
    data class SelectMode(val mode: MeasurementMode) : MeasurementEvent
    data class SelectUnit(val unit: MeasurementUnit) : MeasurementEvent
    data class AddPoint(val point: SpatialPoint) : MeasurementEvent
    data object Undo : MeasurementEvent
    data object Clear : MeasurementEvent
    data object Finish : MeasurementEvent
    data object Capture : MeasurementEvent
    data class Save(val name: String) : MeasurementEvent
    data class Rename(val id: Long, val name: String) : MeasurementEvent
    data class Delete(val id: Long) : MeasurementEvent
    data class SystemMessage(val text: String) : MeasurementEvent
}
