package com.spatialapps.spatialruler.ui.measurement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pico.spatial.ui.design.Button
import com.pico.spatial.ui.design.PicoTheme
import com.pico.spatial.ui.design.Text
import com.pico.spatial.ui.design.TextField
import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementUnit
import com.spatialapps.spatialruler.platform.MeasurementInteractionBridge
import com.spatialapps.spatialruler.ui.components.GlassPanel

@Composable
fun MeasurementControls(
    state: MeasurementUiState,
    onEvent: (MeasurementEvent) -> Unit,
    onExit: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    GlassPanel(modifier = Modifier.width(820.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("${state.mode.displayName}测量", style = PicoTheme.typography.titleLarge)
                    Text(state.message, color = PicoTheme.colorScheme.labelSecondary)
                }
                Text(state.displayValue, style = PicoTheme.typography.headlineLarge)
            }
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MeasurementMode.entries.forEach { mode ->
                    Button(onClick = { onEvent(MeasurementEvent.SelectMode(mode)) }) {
                        Text(if (state.mode == mode) "● ${mode.displayName}" else mode.displayName)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MeasurementUnit.entries.forEach { unit ->
                    Button(onClick = { onEvent(MeasurementEvent.SelectUnit(unit)) }) {
                        Text(if (state.unit == unit) "● ${unit.symbol}" else unit.symbol)
                    }
                }
                Button(onClick = {
                    val cursor = MeasurementInteractionBridge.currentCursorPoint
                    if (cursor == null) onEvent(MeasurementEvent.SystemMessage("未命中空间平面，请移动射线"))
                    else onEvent(MeasurementEvent.AddPoint(cursor))
                }) { Text("标记点") }
                Button(onClick = { onEvent(MeasurementEvent.Undo) }) { Text("撤销") }
                Button(onClick = { onEvent(MeasurementEvent.Clear) }) { Text("清除") }
                Button(onClick = { onEvent(MeasurementEvent.Capture) }) { Text("截图") }
                Button(onClick = { onEvent(MeasurementEvent.Finish) }) { Text("完成") }
            }
            if (state.isCompleted) {
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("测量对象名称") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                    )
                    Button(onClick = { onEvent(MeasurementEvent.Save(name)) }) {
                        Text(if (state.isSaving) "保存中" else "保存记录")
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("标记点 ${state.points.size} · 射线点击地面 / 长捏合完成 / 挥手撤销 / 张掌清除", color = PicoTheme.colorScheme.labelSecondary)
            Spacer(Modifier.height(8.dp))
            Button(onClick = onExit) { Text("返回 Shared Space") }
        }
    }
}
