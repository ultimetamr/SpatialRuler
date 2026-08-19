package com.spatialapps.spatialruler.domain.usecase

import com.spatialapps.spatialruler.domain.model.SpatialPoint
import kotlin.math.floor

data class RulerTick(
    val position: SpatialPoint,
    val distanceMeters: Double,
    val isMajor: Boolean,
)

class BuildRulerTicksUseCase {
    operator fun invoke(start: SpatialPoint, end: SpatialPoint): List<RulerTick> {
        val length = start.distanceTo(end)
        if (length < 0.1) return emptyList()
        val count = floor(length / 0.1).toInt()
        return (1..count).map { index ->
            val distance = index * 0.1
            val ratio = (distance / length).toFloat().coerceAtMost(1f)
            RulerTick(
                position = SpatialPoint(
                    x = start.x + (end.x - start.x) * ratio,
                    y = start.y + (end.y - start.y) * ratio,
                    z = start.z + (end.z - start.z) * ratio,
                ),
                distanceMeters = distance,
                isMajor = index % 5 == 0,
            )
        }
    }
}
