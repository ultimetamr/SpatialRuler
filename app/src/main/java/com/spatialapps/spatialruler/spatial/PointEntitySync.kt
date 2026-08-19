package com.spatialapps.spatialruler.spatial

import com.spatialapps.spatialruler.domain.model.SpatialPoint

internal object PointEntitySync {
    fun commonPrefixSize(previous: List<SpatialPoint>, current: List<SpatialPoint>): Int {
        val maximum = minOf(previous.size, current.size)
        var index = 0
        while (index < maximum && previous[index] == current[index]) index += 1
        return index
    }
}
