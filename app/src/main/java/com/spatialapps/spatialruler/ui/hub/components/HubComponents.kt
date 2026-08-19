package com.spatialapps.spatialruler.ui.hub.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.spatialapps.spatialruler.domain.model.MeasurementRecord
import com.spatialapps.spatialruler.ui.measurement.MeasurementEvent

@Composable
fun HubSectionTitle(text: String) {
    Text(text, style = PicoTheme.typography.titleLarge)
    Spacer(Modifier.height(10.dp))
}

@Composable
fun HubChoiceRow(labels: List<String>, selectedIndex: Int, onSelect: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        labels.forEachIndexed { index, label ->
            Button(onClick = { onSelect(index) }) {
                Text(if (index == selectedIndex) "● $label" else label)
            }
        }
    }
}

@Composable
fun HubHistoryRow(record: MeasurementRecord, onEvent: (MeasurementEvent) -> Unit) {
    var name by remember(record.id, record.name) { mutableStateOf(record.name) }
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text("${record.mode.displayName} · ${record.value.valueSi} SI", color = PicoTheme.colorScheme.labelSecondary)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("测量对象名称") },
                modifier = Modifier.weight(1f),
                singleLine = true,
            )
            Button(onClick = { onEvent(MeasurementEvent.Rename(record.id, name)) }) { Text("改名") }
            Button(onClick = { onEvent(MeasurementEvent.Delete(record.id)) }) { Text("删除") }
        }
    }
}
