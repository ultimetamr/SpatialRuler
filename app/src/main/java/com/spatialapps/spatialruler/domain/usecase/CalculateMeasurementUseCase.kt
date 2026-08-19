package com.spatialapps.spatialruler.domain.usecase

import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementValue
import com.spatialapps.spatialruler.domain.model.SpatialPoint
import kotlin.math.abs
import kotlin.math.sqrt

class CalculateMeasurementUseCase {
    operator fun invoke(mode: MeasurementMode, points: List<SpatialPoint>): MeasurementValue? {
        if (points.size < mode.minimumPoints) return null
        return when (mode) {
            MeasurementMode.STRAIGHT -> MeasurementValue(points.first().distanceTo(points.last()), false)
            MeasurementMode.CONTINUOUS -> MeasurementValue(
                points.zipWithNext().sumOf { (start, end) -> start.distanceTo(end) },
                false,
            )
            MeasurementMode.AREA -> MeasurementValue(polygonArea(points.take(4)), true)
            MeasurementMode.HEIGHT -> MeasurementValue(abs(points.last().y - points.first().y).toDouble(), false)
        }
    }

    private fun polygonArea(points: List<SpatialPoint>): Double {
        var crossX = 0.0
        var crossY = 0.0
        var crossZ = 0.0
        points.indices.forEach { index ->
            val current = points[index]
            val next = points[(index + 1) % points.size]
            crossX += current.y * next.z - current.z * next.y
            crossY += current.z * next.x - current.x * next.z
            crossZ += current.x * next.y - current.y * next.x
        }
        return 0.5 * sqrt(crossX * crossX + crossY * crossY + crossZ * crossZ)
    }
}
