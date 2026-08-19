package com.spatialapps.spatialruler.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.pico.spatial.ui.platform.containers.StageStyle
import com.pico.spatial.ui.platform.containers.openStage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementRecord
import com.spatialapps.spatialruler.domain.model.MeasurementUnit
import com.spatialapps.spatialruler.domain.model.MeasurementValue
import com.spatialapps.spatialruler.domain.model.SpatialPoint
import com.spatialapps.spatialruler.ui.measurement.MeasurementUiState

class StageSmokeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (
            intent.action != ACTION_OPEN_STAGE &&
                intent.action != ACTION_CAPTURE_SAMPLE &&
                intent.action != ACTION_RENDER_SAMPLE
        ) return
        val pendingResult = goAsync()
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch {
            when (intent.action) {
                ACTION_OPEN_STAGE -> openStage(context)
                ACTION_CAPTURE_SAMPLE -> captureSample(context)
                ACTION_RENDER_SAMPLE -> renderSample()
            }
            pendingResult.finish()
        }
    }

    private suspend fun openStage(context: Context) {
        runCatching { context.openStage("MeasurementStage", StageStyle.Mixed) }
            .onSuccess { Log.i(TAG, "MeasurementStage request result=$it") }
            .onFailure { Log.e(TAG, "MeasurementStage request failed", it) }
    }

    private suspend fun captureSample(context: Context) {
        val points = listOf(
            SpatialPoint(-0.65f, 0.15f, 0f),
            SpatialPoint(0.65f, 0.15f, 0f),
        )
        val value = MeasurementValue(1.3, false)
        val state = MeasurementUiState(
            mode = MeasurementMode.STRAIGHT,
            unit = MeasurementUnit.CENTIMETER,
            points = points,
            value = value,
            displayValue = "130.0 cm",
            isCompleted = true,
            message = "真机验证快照",
        )
        runCatching {
            val uri = MediaStoreMeasurementSnapshotRepository(context.applicationContext).capture(state)
            val recordId = SpatialRulerGraph.measurementRepository.save(
                MeasurementRecord(
                    name = "真机验证标尺",
                    mode = MeasurementMode.STRAIGHT,
                    value = value,
                    points = points,
                    displayUnit = MeasurementUnit.CENTIMETER,
                    screenshotUri = uri,
                    createdAtEpochMillis = System.currentTimeMillis(),
                ),
            )
            Log.i(TAG, "Capture saved uri=$uri recordId=$recordId")
        }.onFailure { Log.e(TAG, "Capture sample failed", it) }
    }

    private suspend fun renderSample() {
        val sink = MeasurementInteractionBridge.debugPointSink
        val clear = MeasurementInteractionBridge.debugClearSink
        if (sink == null || clear == null) {
            Log.e(TAG, "MeasurementStage is not ready for render sample")
            return
        }
        sink(SpatialPoint(-0.45f, 0.02f, -1.2f))
        kotlinx.coroutines.delay(40L)
        sink(SpatialPoint(0.45f, 0.02f, -1.2f))
        kotlinx.coroutines.delay(400L)
        clear()
        kotlinx.coroutines.delay(200L)
        sink(SpatialPoint(-0.35f, 0.02f, -1.1f))
        kotlinx.coroutines.delay(40L)
        sink(SpatialPoint(0.35f, 0.02f, -1.1f))
        Log.i(TAG, "Quick-tap points injected, cleared, and reinjected for endpoint retention test")
    }

    private companion object {
        const val ACTION_OPEN_STAGE = "com.spatialapps.spatialruler.DEBUG_OPEN_STAGE"
        const val ACTION_CAPTURE_SAMPLE = "com.spatialapps.spatialruler.DEBUG_CAPTURE_SAMPLE"
        const val ACTION_RENDER_SAMPLE = "com.spatialapps.spatialruler.DEBUG_RENDER_SAMPLE"
        const val TAG = "SpatialRulerSmoke"
    }
}
