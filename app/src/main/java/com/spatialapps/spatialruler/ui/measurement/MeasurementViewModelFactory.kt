package com.spatialapps.spatialruler.ui.measurement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spatialapps.spatialruler.data.repository.MeasurementRepository
import com.spatialapps.spatialruler.data.repository.MeasurementSnapshotRepository
import kotlinx.coroutines.flow.MutableStateFlow

class MeasurementViewModelFactory(
    private val repository: MeasurementRepository,
    private val snapshotRepository: MeasurementSnapshotRepository,
    private val sharedState: MutableStateFlow<MeasurementUiState>,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(MeasurementViewModel::class.java))
        return MeasurementViewModel(
            repository = repository,
            snapshotRepository = snapshotRepository,
            mutableState = sharedState,
        ) as T
    }
}
