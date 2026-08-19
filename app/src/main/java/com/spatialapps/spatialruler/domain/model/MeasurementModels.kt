package com.spatialapps.spatialruler.domain.model

enum class MeasurementMode(val displayName: String, val minimumPoints: Int) {
    STRAIGHT("直线", 2),
    CONTINUOUS("连续", 2),
    AREA("面积", 4),
    HEIGHT("高度", 2),
}

enum class MeasurementUnit(val displayName: String, val symbol: String) {
    CENTIMETER("厘米", "cm"),
    METER("米", "m"),
    INCH("英寸", "in"),
}

data class MeasurementValue(
    val valueSi: Double,
    val isArea: Boolean,
)

data class MeasurementRecord(
    val id: Long = 0,
    val name: String,
    val mode: MeasurementMode,
    val value: MeasurementValue,
    val points: List<SpatialPoint>,
    val displayUnit: MeasurementUnit,
    val screenshotUri: String? = null,
    val createdAtEpochMillis: Long,
)
