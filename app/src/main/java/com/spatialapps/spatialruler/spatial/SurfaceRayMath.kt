package com.spatialapps.spatialruler.spatial

import com.pico.spatial.core.math.Vector3
import kotlin.math.abs

internal object SurfaceRayMath {
    fun intersectTriangle(
        origin: Vector3,
        direction: Vector3,
        a: Vector3,
        b: Vector3,
        c: Vector3,
    ): Float? {
        val epsilon = 0.000001f
        val edge1 = b - a
        val edge2 = c - a
        val h = Vector3.cross(direction, edge2)
        val determinant = Vector3.dot(edge1, h)
        if (abs(determinant) < epsilon) return null
        val inverse = 1f / determinant
        val s = origin - a
        val u = inverse * Vector3.dot(s, h)
        if (u !in 0f..1f) return null
        val q = Vector3.cross(s, edge1)
        val v = inverse * Vector3.dot(direction, q)
        if (v < 0f || u + v > 1f) return null
        val distance = inverse * Vector3.dot(edge2, q)
        return distance.takeIf { it > epsilon }
    }
}
