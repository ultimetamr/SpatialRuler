package com.spatialapps.spatialruler.spatial

import com.pico.spatial.core.math.Vector3
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SurfaceRayMathTest {
    private val a = Vector3(-1f, 0f, -1f)
    private val b = Vector3(0f, 0f, 1f)
    private val c = Vector3(1f, 0f, -1f)

    @Test
    fun downwardRayHitsFloorTriangle() {
        val distance = SurfaceRayMath.intersectTriangle(
            origin = Vector3(0f, 1f, 0f),
            direction = Vector3(0f, -1f, 0f),
            a = a,
            b = b,
            c = c,
        )

        assertEquals(1f, distance ?: error("expected floor hit"), 0.000001f)
    }

    @Test
    fun parallelRayDoesNotHitFloorTriangle() {
        assertNull(
            SurfaceRayMath.intersectTriangle(
                origin = Vector3(0f, 1f, 0f),
                direction = Vector3(1f, 0f, 0f),
                a = a,
                b = b,
                c = c,
            ),
        )
    }

    @Test
    fun rayPointingAwayDoesNotHitFloorTriangle() {
        assertNull(
            SurfaceRayMath.intersectTriangle(
                origin = Vector3(0f, 1f, 0f),
                direction = Vector3(0f, 1f, 0f),
                a = a,
                b = b,
                c = c,
            ),
        )
    }
}
