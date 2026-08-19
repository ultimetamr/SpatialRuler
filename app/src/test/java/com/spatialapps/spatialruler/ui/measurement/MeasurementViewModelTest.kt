package com.spatialapps.spatialruler.ui.measurement

import com.spatialapps.spatialruler.data.repository.MeasurementRepository
import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementRecord
import com.spatialapps.spatialruler.domain.model.MeasurementUnit
import com.spatialapps.spatialruler.domain.model.SpatialPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MeasurementViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = FakeRepository()
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun addAndUndoRecalculateValue() = runTest(dispatcher) {
        val viewModel = MeasurementViewModel(repository)
        viewModel.onEvent(MeasurementEvent.AddPoint(SpatialPoint(0f, 0f, 0f)))
        viewModel.onEvent(MeasurementEvent.AddPoint(SpatialPoint(1f, 0f, 0f)))
        assertEquals(1.0, viewModel.state.value.value!!.valueSi, 0.0001)
        viewModel.onEvent(MeasurementEvent.Undo)
        assertEquals(null, viewModel.state.value.value)
    }

    @Test
    fun selectingModeClearsPreviousSession() = runTest(dispatcher) {
        val viewModel = MeasurementViewModel(repository)
        viewModel.onEvent(MeasurementEvent.AddPoint(SpatialPoint(0f, 0f, 0f)))
        viewModel.onEvent(MeasurementEvent.SelectMode(MeasurementMode.AREA))
        assertEquals(MeasurementMode.AREA, viewModel.state.value.mode)
        assertTrue(viewModel.state.value.points.isEmpty())
    }

    @Test
    fun fourAreaPointsCompleteAutomatically() = runTest(dispatcher) {
        val viewModel = MeasurementViewModel(repository)
        viewModel.onEvent(MeasurementEvent.SelectMode(MeasurementMode.AREA))
        listOf(
            SpatialPoint(0f, 0f, 0f), SpatialPoint(1f, 0f, 0f),
            SpatialPoint(1f, 1f, 0f), SpatialPoint(0f, 1f, 0f),
        ).forEach { viewModel.onEvent(MeasurementEvent.AddPoint(it)) }
        assertTrue(viewModel.state.value.isCompleted)
        assertEquals(1.0, viewModel.state.value.value!!.valueSi, 0.0001)
    }

    @Test
    fun changingUnitDoesNotChangeSiValue() = runTest(dispatcher) {
        val viewModel = MeasurementViewModel(repository)
        viewModel.onEvent(MeasurementEvent.AddPoint(SpatialPoint(0f, 0f, 0f)))
        viewModel.onEvent(MeasurementEvent.AddPoint(SpatialPoint(1f, 0f, 0f)))
        viewModel.onEvent(MeasurementEvent.SelectUnit(MeasurementUnit.INCH))
        assertEquals(1.0, viewModel.state.value.value!!.valueSi, 0.0001)
        assertTrue(viewModel.state.value.displayValue.endsWith("in"))
    }

    @Test
    fun finishAndSaveWritesHistory() = runTest(dispatcher) {
        val viewModel = MeasurementViewModel(repository, now = { 42L })
        viewModel.onEvent(MeasurementEvent.AddPoint(SpatialPoint(0f, 0f, 0f)))
        viewModel.onEvent(MeasurementEvent.AddPoint(SpatialPoint(1f, 0f, 0f)))
        viewModel.onEvent(MeasurementEvent.Finish)
        viewModel.onEvent(MeasurementEvent.Save("桌面"))
        dispatcher.scheduler.advanceUntilIdle()
        assertFalse(viewModel.state.value.isSaving)
        assertEquals("桌面", repository.records.value.single().name)
        assertEquals(42L, repository.records.value.single().createdAtEpochMillis)
    }

    private class FakeRepository : MeasurementRepository {
        val records = MutableStateFlow<List<MeasurementRecord>>(emptyList())
        override fun observeAll(): Flow<List<MeasurementRecord>> = records
        override suspend fun save(record: MeasurementRecord): Long {
            val withId = record.copy(id = (records.value.maxOfOrNull { it.id } ?: 0) + 1)
            records.value = listOf(withId) + records.value
            return withId.id
        }
        override suspend fun rename(id: Long, name: String) {
            records.value = records.value.map { if (it.id == id) it.copy(name = name) else it }
        }
        override suspend fun delete(id: Long) {
            records.value = records.value.filterNot { it.id == id }
        }
    }
}
