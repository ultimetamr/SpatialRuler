package com.spatialapps.spatialruler.data.repository

import com.spatialapps.spatialruler.ui.measurement.MeasurementUiState

interface MeasurementSnapshotRepository {
    suspend fun capture(state: MeasurementUiState): String
}
