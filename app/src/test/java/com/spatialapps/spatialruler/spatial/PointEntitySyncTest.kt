package com.spatialapps.spatialruler.spatial

import com.spatialapps.spatialruler.domain.model.SpatialPoint
import org.junit.Assert.assertEquals
import org.junit.Test

class PointEntitySyncTest {
    private val first = SpatialPoint(-0.45f, 0.02f, -1.2f)
    private val second = SpatialPoint(0.45f, 0.02f, -1.2f)

    @Test
    fun `appending a point retains existing endpoint`() {
        assertEquals(1, PointEntitySync.commonPrefixSize(listOf(first), listOf(first, second)))
    }

    @Test
    fun `undo retains the remaining endpoint`() {
        assertEquals(1, PointEntitySync.commonPrefixSize(listOf(first, second), listOf(first)))
    }

    @Test
    fun `changing the first point rebuilds affected endpoints`() {
        assertEquals(0, PointEntitySync.commonPrefixSize(listOf(first, second), listOf(second)))
    }
}
