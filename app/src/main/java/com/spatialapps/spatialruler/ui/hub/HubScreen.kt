package com.spatialapps.spatialruler.ui.hub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pico.spatial.ui.design.Button
import com.pico.spatial.ui.design.PicoTheme
import com.pico.spatial.ui.design.Text
import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementUnit
import com.spatialapps.spatialruler.platform.SpatialRulerGraph
import com.spatialapps.spatialruler.ui.components.GlassPanel
import com.spatialapps.spatialruler.ui.measurement.MeasurementEvent
import com.spatialapps.spatialruler.ui.measurement.MeasurementUiState
import com.spatialapps.spatialruler.ui.measurement.MeasurementViewModel
import com.spatialapps.spatialruler.ui.hub.components.HubChoiceRow
import com.spatialapps.spatialruler.ui.hub.components.HubHistoryRow
import com.spatialapps.spatialruler.ui.hub.components.HubSectionTitle

@Composable
fun HubRoute(onStartMeasurement: () -> Unit) {
    val viewModel: MeasurementViewModel = viewModel(factory = SpatialRulerGraph.viewModelFactory)
    val state by viewModel.state.collectAsStateWithLifecycle()
    HubScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onStartMeasurement = onStartMeasurement,
    )
}

@Composable
fun HubScreen(
    state: MeasurementUiState,
    onEvent: (MeasurementEvent) -> Unit,
    onStartMeasurement: () -> Unit,
) {
    GlassPanel(modifier = Modifier.width(760.dp)) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text("实景空间标尺", style = PicoTheme.typography.headlineLarge)
            Text(
                "Shared Space 中管理模式与历史；开始后进入透视 Mixed Stage 精准测量。",
                style = PicoTheme.typography.bodyLarge,
                color = PicoTheme.colorScheme.labelSecondary,
            )
            Spacer(Modifier.height(24.dp))
            HubSectionTitle("测量模式")
            HubChoiceRow(
                labels = MeasurementMode.entries.map { it.displayName },
                selectedIndex = MeasurementMode.entries.indexOf(state.mode),
                onSelect = { onEvent(MeasurementEvent.SelectMode(MeasurementMode.entries[it])) },
            )
            Spacer(Modifier.height(16.dp))
            HubSectionTitle("显示单位")
            HubChoiceRow(
                labels = MeasurementUnit.entries.map { it.displayName },
                selectedIndex = MeasurementUnit.entries.indexOf(state.unit),
                onSelect = { onEvent(MeasurementEvent.SelectUnit(MeasurementUnit.entries[it])) },
            )
            Spacer(Modifier.height(24.dp))
            Button(onClick = onStartMeasurement, modifier = Modifier.fillMaxWidth()) {
                Text("开始 ${state.mode.displayName}测量")
            }
            Spacer(Modifier.height(28.dp))
            HubSectionTitle("测量记录 · ${state.history.size}")
            if (state.history.isEmpty()) {
                Text("完成一次测量并保存后，记录会显示在这里。", color = PicoTheme.colorScheme.labelSecondary)
            } else {
                state.history.take(8).forEach { record ->
                    HubHistoryRow(record = record, onEvent = onEvent)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}
