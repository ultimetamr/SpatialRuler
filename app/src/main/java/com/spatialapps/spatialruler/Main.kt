package com.spatialapps.spatialruler

import com.spatialapps.spatialruler.content.HomeVolume
import com.pico.spatial.ui.design.PicoTheme
import com.pico.spatial.ui.foundation.dsl.DefaultWindowContainer
import com.pico.spatial.ui.foundation.dsl.Form
import com.pico.spatial.ui.foundation.dsl.SpatialAppScope
import com.pico.spatial.ui.foundation.dsl.Stage
import com.pico.spatial.ui.foundation.dsl.WindowContainer
import com.spatialapps.spatialruler.ui.measurement.MeasurementControlsRoute
import com.spatialapps.spatialruler.ui.measurement.MeasurementStageRoute

fun mainApp(scope: SpatialAppScope) =
    with(scope) {
        DefaultWindowContainer {
            PicoTheme {
                HomeVolume()
            }
        }
        Stage(id = "MeasurementStage") {
            PicoTheme { MeasurementStageRoute() }
        }
        WindowContainer(id = "MeasurementControls", form = Form.Planar) {
            PicoTheme { MeasurementControlsRoute() }
        }
    }
