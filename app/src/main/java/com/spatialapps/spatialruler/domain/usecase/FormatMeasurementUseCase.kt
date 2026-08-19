package com.spatialapps.spatialruler.domain.usecase

import com.spatialapps.spatialruler.domain.model.MeasurementUnit
import com.spatialapps.spatialruler.domain.model.MeasurementValue
import java.util.Locale

class FormatMeasurementUseCase {
    operator fun invoke(value: MeasurementValue?, unit: MeasurementUnit): String {
        if (value == null) return "等待标记"
        val factor = when (unit) {
            MeasurementUnit.CENTIMETER -> if (value.isArea) 10_000.0 else 100.0
            MeasurementUnit.METER -> 1.0
            MeasurementUnit.INCH -> if (value.isArea) 1_550.0031 else 39.3700787
        }
        val suffix = if (value.isArea) "${unit.symbol}²" else unit.symbol
        val decimals = when {
            unit == MeasurementUnit.METER -> 3
            value.valueSi * factor >= 100 -> 1
            else -> 2
        }
        return String.format(Locale.US, "%.${decimals}f %s", value.valueSi * factor, suffix)
    }
}
