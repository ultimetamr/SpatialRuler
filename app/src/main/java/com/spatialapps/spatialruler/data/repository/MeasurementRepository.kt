package com.spatialapps.spatialruler.data.repository

import com.spatialapps.spatialruler.domain.model.MeasurementRecord
import kotlinx.coroutines.flow.Flow

interface MeasurementRepository {
    fun observeAll(): Flow<List<MeasurementRecord>>
    suspend fun save(record: MeasurementRecord): Long
    suspend fun rename(id: Long, name: String)
    suspend fun delete(id: Long)
}
