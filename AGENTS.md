<!-- pico-cli:plugin-context:pico-spatial-agentic-tools:start -->
## Plugin Context

Also read `./PICO-SPATIAL-AGENTIC-TOOLS.AGENTS.md` for PICO Spatial plugin guidance.
<!-- pico-cli:plugin-context:pico-spatial-agentic-tools:end -->

## Project Handoff

- App: 实景空间标尺 (`com.spatialapps.spatialruler`), Spatial BOM 6.0.0, Kotlin 2.1.20.
- Entry: `Main.kt`; default `SpatialRulerHub` is a Shared Space Volumetric WindowContainer.
- Measurement: `MeasurementStage` opens as `StageStyle.Mixed`; `MeasurementControls` is a Planar WindowContainer.
- Core code: `domain/` measurement math, `data/db` Room history, `spatial/` ECS ruler + Plane/Mesh + HandTracking, `ui/` SpatialUI screens.
- Build/test: `.\gradlew.bat testDebugUnitTest assembleDebug`.
- Device install: `pico-cli app install app/build/outputs/apk/debug/app-debug.apk --device <DEVICE_ID> --grant-permissions`.
- Launch: `pico-cli app launch com.spatialapps.spatialruler --activity .platform.LaunchActivity --device <DEVICE_ID>`.
- Debug-only broadcasts: `DEBUG_OPEN_STAGE` and `DEBUG_CAPTURE_SAMPLE` validate Stage and snapshot paths; neither exists in Release source.
- Renderer regression broadcast: `DEBUG_RENDER_SAMPLE` injects two points, clears, and injects two more to exercise mesh/material destruction and recreation; it exists only in Debug source.
- Device screenshot caveat: PICO rejects adb compositor capture (`screencap` non-PNG; record `INVALID_LAYER_STACK`). App-generated measurement snapshots are saved through MediaStore instead.
- Real-surface input: `SurfaceTrackingController` synchronizes existing Plane/Mesh anchors after start; `MeasurementSceneRenderer` gives plane meshes static colliders + `InteractableComponent`; `MeasurementStage` handles official `detectSpatialTapGesture` coordinates. Hand/anchor callbacks marshal ECS work to the Android main thread.
- Ground-tap verification: install the APK, wear the headset, enter `MeasurementStage`, scan until the pale-blue floor grid appears, then aim and quick-pinch. Watch `SpatialRulerSurface` for `semantic=FLOOR` and `SpatialRulerInput` for the converted tap point. An unattended/sleeping headset may report zero anchors and `stage proxy is null`.
- Resource lifecycle: reusable ruler/cursor/plane meshes and materials are persisted with `toGlobal()` and explicitly closed by `MeasurementSceneRenderer.close()`. Do not cache and reuse ordinary resources after destroying their owning `ModelEntity`.
- Endpoint animation: `MeasurementSceneRenderer` retains the unchanged endpoint prefix across renders. Only newly appended points play the 65%-to-100% placement animation; line/tick geometry may be rebuilt independently.
- Surface startup: `SurfaceTrackingController` caches early Plane updates until the `SpatialView` root is ready, then replays them. Empty initial plane loads retry for up to 12 seconds and recover paused/stopped managers instead of treating the first RUNNING+empty result as ready.
- Cylinder geometry: Spatial SDK 6.0.0 declares `MeshResource.createCylinder(height, radius)`. Always use named `height =` / `radius =` arguments; positional reversal produces meter-radius discs instead of thin ruler lines.
- Single-tap placement: detected planes keep their precise static-mesh collider and add an invisible 2cm-thick bounding-box collider for reliable two-sided/edge taps. `detectSpatialTapGesture` immediately adds one point, preferring the precise hand-ray Plane/Mesh intersection over the hit-box position.
- Stable plane targets: a Plane anchor creates its interactive entity/colliders once. `UPDATED` events only update the entity transform; only `REMOVED` destroys it. Recreating Plane entities during tracking updates invalidates an in-progress spatial tap between press and release.
