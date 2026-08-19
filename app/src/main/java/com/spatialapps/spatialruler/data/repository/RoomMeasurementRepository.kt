package com.spatialapps.spatialruler.data.repository

import com.spatialapps.spatialruler.data.db.MeasurementDao
import com.spatialapps.spatialruler.data.db.MeasurementEntity
import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementRecord
import com.spatialapps.spatialruler.domain.model.MeasurementUnit
import com.spatialapps.spatialruler.domain.model.MeasurementValue
import com.spatialapps.spatialruler.domain.model.SpatialPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomMeasurementRepository(
    private val dao: MeasurementDao,
) : MeasurementRepository {
    override fun observeAll(): Flow<List<MeasurementRecord>> =
        dao.observeAll().map { rows -> rows.map { it.toDomain() } }

    override suspend fun save(record: MeasurementRecord): Long = dao.insert(record.toEntity())

    override suspend fun rename(id: Long, name: String) = dao.rename(id, name.trim().ifBlank { "未命名测量" })

    override suspend fun delete(id: Long) = dao.deleteById(id)

    private fun MeasurementRecord.toEntity() = MeasurementEntity(
        id = id,
        name = name,
        mode = mode.name,
        valueSi = value.valueSi,
        isArea = value.isArea,
        points = points.joinToString(";") { "${it.x},${it.y},${it.z}" },
        displayUnit = displayUnit.name,
        screenshotUri = screenshotUri,
        createdAtEpochMillis = createdAtEpochMillis,
    )

    private fun MeasurementEntity.toDomain() = MeasurementRecord(
        id = id,
        name = name,
        mode = MeasurementMode.valueOf(mode),
        value = MeasurementValue(valueSi, isArea),
        points = points.split(';').mapNotNull { encoded ->
            val values = encoded.split(',').mapNotNull(String::toFloatOrNull)
            values.takeIf { it.size == 3 }?.let { SpatialPoint(it[0], it[1], it[2]) }
        },
        displayUnit = MeasurementUnit.valueOf(displayUnit),
        screenshotUri = screenshotUri,
        createdAtEpochMillis = createdAtEpochMillis,
    )
}
