package com.spatialapps.spatialruler.domain.model

import kotlin.math.sqrt

data class SpatialPoint(
    val x: Float,
    val y: Float,
    val z: Float,
) {
    fun distanceTo(other: SpatialPoint): Double {
        val dx = (other.x - x).toDouble()
        val dy = (other.y - y).toDouble()
        val dz = (other.z - z).toDouble()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }
}
