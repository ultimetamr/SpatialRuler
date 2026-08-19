package com.spatialapps.spatialruler.platform

import com.spatialapps.spatialruler.domain.model.SpatialPoint

object MeasurementInteractionBridge {
    @Volatile
    var currentCursorPoint: SpatialPoint? = null

    @Volatile
    internal var debugPointSink: ((SpatialPoint) -> Unit)? = null

    @Volatile
    internal var debugClearSink: (() -> Unit)? = null
}
