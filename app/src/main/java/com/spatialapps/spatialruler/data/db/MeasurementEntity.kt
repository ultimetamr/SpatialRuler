package com.spatialapps.spatialruler.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurements")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val mode: String,
    val valueSi: Double,
    val isArea: Boolean,
    val points: String,
    val displayUnit: String,
    val screenshotUri: String?,
    val createdAtEpochMillis: Long,
)
