package com.spatialapps.spatialruler.ui.measurement

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.pico.spatial.ui.foundation.content.SpatialView
import com.pico.spatial.core.ecs.ViewCoordinateSpace
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.ui.foundation.gesture.TargetEntity
import com.pico.spatial.ui.foundation.gesture.detectSpatialTapGesture
import com.pico.spatial.ui.design.Text
import com.pico.spatial.ui.design.PicoTheme
import com.spatialapps.spatialruler.domain.usecase.BuildRulerTicksUseCase
import com.pico.spatial.ui.platform.containers.LocalSpatialNavigator
import com.spatialapps.spatialruler.platform.MeasurementInteractionBridge
import com.spatialapps.spatialruler.platform.SpatialRulerGraph
import com.spatialapps.spatialruler.spatial.HandGestureController
import com.spatialapps.spatialruler.spatial.MeasurementSceneRenderer
import com.spatialapps.spatialruler.spatial.SurfaceTrackingController

@Composable
fun MeasurementStageRoute() {
    val context = LocalContext.current
    val navigator = LocalSpatialNavigator.current
    val viewModel: MeasurementViewModel = viewModel(factory = SpatialRulerGraph.viewModelFactory)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val renderer = remember { MeasurementSceneRenderer() }
    val surfaces = remember(renderer) { SurfaceTrackingController(renderer::onPlaneUpdate) }
    val gestures = remember(surfaces, renderer) {
        HandGestureController(surfaces, renderer, viewModel::onEvent)
    }
    val majorTickLabels = remember(state.points, state.mode, state.unit) {
        val segments = state.points.zipWithNext().toMutableList()
        if (state.mode == com.spatialapps.spatialruler.domain.model.MeasurementMode.AREA && state.points.size == 4) {
            segments += state.points.last() to state.points.first()
        }
        segments.flatMap { (start, end) ->
            BuildRulerTicksUseCase()(start, end)
                .filter { it.isMajor }
                .map { tick ->
                    when (state.unit) {
                        com.spatialapps.spatialruler.domain.model.MeasurementUnit.CENTIMETER -> "${(tick.distanceMeters * 100).toInt()} cm"
                        com.spatialapps.spatialruler.domain.model.MeasurementUnit.METER -> "${tick.distanceMeters} m"
                        com.spatialapps.spatialruler.domain.model.MeasurementUnit.INCH -> "${"%.1f".format(tick.distanceMeters * 39.3701)} in"
                    }
                }
        }.take(20)
    }
    val valueScale = remember { Animatable(1f) }
    LaunchedEffect(state.displayValue) {
        if (state.value != null) {
            valueScale.snapTo(0.82f)
            valueScale.animateTo(1f, spring(dampingRatio = 0.55f, stiffness = 520f))
        }
    }
    DisposableEffect(navigator, surfaces, gestures) {
        navigator.openWindowContainer("MeasurementControls")
        MeasurementInteractionBridge.debugPointSink = { point ->
            viewModel.onEvent(MeasurementEvent.AddPoint(point))
        }
        MeasurementInteractionBridge.debugClearSink = {
            viewModel.onEvent(MeasurementEvent.Clear)
        }
        surfaces.start()
        gestures.start()
        onDispose {
            MeasurementInteractionBridge.debugPointSink = null
            MeasurementInteractionBridge.debugClearSink = null
            gestures.stop()
            surfaces.stop()
            renderer.close()
            MeasurementInteractionBridge.currentCursorPoint = null
            navigator.closeWindowContainer("MeasurementControls")
        }
    }
    SpatialView(
        modifier = Modifier.pointerInput(renderer) {
            detectSpatialTapGesture(
                context = context,
                targetedToEntity = TargetEntity.any(renderer::isPlaneEntity),
            ) { tap ->
                val raycastPoint = MeasurementInteractionBridge.currentCursorPoint
                val point = raycastPoint
                    ?: renderer.viewPositionToPoint(tap.position.x, tap.position.y, tap.position.z)
                if (point == null) {
                    Log.w(INPUT_TAG, "Spatial tap targeted a plane but position conversion failed")
                    viewModel.onEvent(MeasurementEvent.SystemMessage("点击未命中地面，请先环视完成空间扫描"))
                } else {
                    val source = if (raycastPoint != null) "surface-ray" else "tap-hit"
                    Log.i(
                        INPUT_TAG,
                        "Single-tap marker source=$source point=(${point.x}, ${point.y}, ${point.z})",
                    )
                    viewModel.onEvent(MeasurementEvent.AddPoint(point))
                }
            }
        },
        initial = { content, attachments ->
            val root = com.pico.spatial.core.ecs.Entity()
            renderer.setTapPositionConverter { viewPosition ->
                val local = content.convertPosition(
                    viewPosition,
                    ViewCoordinateSpace.Local,
                    content.localSpatialCoordinateSpace,
                )
                com.spatialapps.spatialruler.domain.model.SpatialPoint(local.x, local.y, local.z)
            }
            renderer.initialize(
                root,
                attachments.entity("measurement_value"),
                (0 until 20).mapNotNull { attachments.entity("tick_label_$it") },
            )
            surfaces.onConsumerReady()
            content.addEntity(root)
            renderer.render(state.points, state.mode)
        },
        update = { _, _ -> renderer.render(state.points, state.mode) },
        attachments = {
            AttachmentPanel(id = "measurement_value") {
                Text(
                    state.displayValue,
                    modifier = Modifier.scale(valueScale.value),
                    style = PicoTheme.typography.titleLarge,
                    color = PicoTheme.colorScheme.labelPrimary,
                )
            }
            (0 until 20).forEach { index ->
                AttachmentPanel(id = "tick_label_$index") {
                    Text(
                        majorTickLabels.getOrNull(index).orEmpty(),
                        style = PicoTheme.typography.bodyLarge,
                        color = PicoTheme.colorScheme.labelPrimary,
                    )
                }
            }
        },
    )
}

private const val INPUT_TAG = "SpatialRulerInput"
