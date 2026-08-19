package com.spatialapps.spatialruler.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MeasurementEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class SpatialRulerDatabase : RoomDatabase() {
    abstract fun measurementDao(): MeasurementDao
}
