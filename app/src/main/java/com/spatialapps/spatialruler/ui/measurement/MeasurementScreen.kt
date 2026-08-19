package com.spatialapps.spatialruler.ui.measurement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pico.spatial.ui.platform.containers.LocalSpatialNavigator
import com.spatialapps.spatialruler.platform.SpatialRulerGraph
import kotlinx.coroutines.launch

@Composable
fun MeasurementControlsRoute() = MeasurementScreen()

@Composable
fun MeasurementScreen() {
    val viewModel: MeasurementViewModel = viewModel(factory = SpatialRulerGraph.viewModelFactory)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigator = LocalSpatialNavigator.current
    val scope = rememberCoroutineScope()
    MeasurementControls(
        state = state,
        onEvent = viewModel::onEvent,
        onExit = {
            scope.launch {
                navigator.closeWindowContainer()
                navigator.closeStage()
            }
        },
    )
}
