package com.spatialapps.spatialruler.platform

import android.app.Application
import com.pico.spatial.ui.foundation.dsl.launch
import com.spatialapps.spatialruler.mainApp

class SpatialApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SpatialRulerGraph.initialize(this)
        launch(::mainApp)
    }
}
