package com.spatialapps.spatialruler.platform

import android.content.Context
import androidx.room.Room
import com.spatialapps.spatialruler.data.db.SpatialRulerDatabase
import com.spatialapps.spatialruler.data.repository.MeasurementRepository
import com.spatialapps.spatialruler.data.repository.RoomMeasurementRepository
import com.spatialapps.spatialruler.ui.measurement.MeasurementViewModel
import com.spatialapps.spatialruler.ui.measurement.MeasurementUiState
import com.spatialapps.spatialruler.ui.measurement.MeasurementViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow

object SpatialRulerGraph {
    lateinit var measurementRepository: MeasurementRepository
        private set
    lateinit var viewModelFactory: MeasurementViewModelFactory
        private set

    fun initialize(context: Context) {
        val database = Room.databaseBuilder(
            context.applicationContext,
            SpatialRulerDatabase::class.java,
            "spatial-ruler.db",
        ).build()
        measurementRepository = RoomMeasurementRepository(database.measurementDao())
        viewModelFactory = MeasurementViewModelFactory(
            repository = measurementRepository,
            snapshotRepository = MediaStoreMeasurementSnapshotRepository(context.applicationContext),
            sharedState = MutableStateFlow(MeasurementUiState()),
        )
    }
}
