package com.spatialapps.spatialruler.domain

import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.MeasurementUnit
import com.spatialapps.spatialruler.domain.model.SpatialPoint
import com.spatialapps.spatialruler.domain.usecase.BuildRulerTicksUseCase
import com.spatialapps.spatialruler.domain.usecase.CalculateMeasurementUseCase
import com.spatialapps.spatialruler.domain.usecase.FormatMeasurementUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MeasurementUseCasesTest {
    private val calculate = CalculateMeasurementUseCase()

    @Test
    fun straightUsesEuclideanDistance() {
        val value = calculate(
            MeasurementMode.STRAIGHT,
            listOf(SpatialPoint(0f, 0f, 0f), SpatialPoint(0.3f, 0.4f, 0f)),
        )
        assertEquals(0.5, value!!.valueSi, 0.00001)
    }

    @Test
    fun continuousSumsEverySegment() {
        val value = calculate(
            MeasurementMode.CONTINUOUS,
            listOf(SpatialPoint(0f, 0f, 0f), SpatialPoint(1f, 0f, 0f), SpatialPoint(1f, 1f, 0f)),
        )
        assertEquals(2.0, value!!.valueSi, 0.00001)
    }

    @Test
    fun areaWorksOnAnArbitrary3dPlane() {
        val value = calculate(
            MeasurementMode.AREA,
            listOf(
                SpatialPoint(0f, 0f, 0f),
                SpatialPoint(2f, 0f, 0f),
                SpatialPoint(2f, 0f, 1f),
                SpatialPoint(0f, 0f, 1f),
            ),
        )
        assertEquals(2.0, value!!.valueSi, 0.00001)
        assertTrue(value.isArea)
    }

    @Test
    fun heightUsesVerticalAxisOnly() {
        val value = calculate(
            MeasurementMode.HEIGHT,
            listOf(SpatialPoint(-5f, 0.2f, 4f), SpatialPoint(9f, 1.7f, -2f)),
        )
        assertEquals(1.5, value!!.valueSi, 0.00001)
    }

    @Test
    fun unitsAndTicksAreFormattedAtRequiredIntervals() {
        val value = calculate(
            MeasurementMode.STRAIGHT,
            listOf(SpatialPoint(0f, 0f, 0f), SpatialPoint(1f, 0f, 0f)),
        )
        assertEquals("100.0 cm", FormatMeasurementUseCase()(value, MeasurementUnit.CENTIMETER))
        assertEquals("1.000 m", FormatMeasurementUseCase()(value, MeasurementUnit.METER))
        assertEquals("39.37 in", FormatMeasurementUseCase()(value, MeasurementUnit.INCH))
        val ticks = BuildRulerTicksUseCase()(SpatialPoint(0f, 0f, 0f), SpatialPoint(1f, 0f, 0f))
        assertEquals(10, ticks.size)
        assertEquals(2, ticks.count { it.isMajor })
    }
}
