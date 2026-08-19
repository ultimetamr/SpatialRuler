package com.spatialapps.spatialruler.spatial

import android.util.Log
import android.os.Handler
import android.os.Looper
import com.pico.spatial.core.lifecycle.Cancellable
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.sense.base.AnchorUpdate
import com.pico.spatial.sense.base.AnchorUpdateSubscriber
import com.pico.spatial.sense.base.SemanticLabelType
import com.pico.spatial.sense.base.TrackingState
import com.pico.spatial.sense.mesh.MeshAnchor
import com.pico.spatial.sense.mesh.MeshTrackingManager
import com.pico.spatial.sense.plane.PlaneAnchor
import com.pico.spatial.sense.plane.PlaneTrackingManager
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SurfaceHit(
    val worldPosition: Vector3,
    val normal: Vector3,
    val semantic: SemanticLabelType,
    val sourceId: UUID,
)

class SurfaceTrackingController(
    private val onPlaneUpdate: (PlaneAnchor?, UUID, AnchorUpdate.Event) -> Unit,
) {
    private val planes = ConcurrentHashMap<UUID, PlaneAnchor>()
    private val meshes = ConcurrentHashMap<UUID, MeshAnchor>()
    private var planeSubscription: Cancellable? = null
    private var meshSubscription: Cancellable? = null
    private var trackingScope: CoroutineScope? = null
    private var initialSyncJob: Job? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    @Volatile private var started = false
    @Volatile private var consumerReady = false

    fun start() {
        if (started) return
        started = true
        planeSubscription = PlaneTrackingManager.subscribeAnchorUpdate(
            AnchorUpdateSubscriber { update ->
                val anchor = update.anchor
                when (update.event) {
                    AnchorUpdate.Event.ADDED, AnchorUpdate.Event.UPDATED, AnchorUpdate.Event.LOADED -> planes[anchor.anchorUUID] = anchor
                    AnchorUpdate.Event.REMOVED -> planes.remove(anchor.anchorUUID)
                    else -> Unit
                }
                if (update.event != AnchorUpdate.Event.UPDATED) {
                    Log.i(
                        TAG,
                        "Plane ${update.event}: id=${anchor.anchorUUID}, semantic=${anchor.semantics}, " +
                            "vertices=${anchor.vertices.size}, indices=${anchor.indices.size}, cached=${planes.size}",
                    )
                }
                mainHandler.post {
                    if (started && consumerReady) {
                        onPlaneUpdate(
                            anchor.takeUnless { update.event == AnchorUpdate.Event.REMOVED },
                            anchor.anchorUUID,
                            update.event,
                        )
                    }
                }
            },
        )
        meshSubscription = MeshTrackingManager.subscribeAnchorUpdate(
            AnchorUpdateSubscriber { update ->
                val anchor = update.anchor
                when (update.event) {
                    AnchorUpdate.Event.ADDED, AnchorUpdate.Event.UPDATED, AnchorUpdate.Event.LOADED -> meshes[anchor.anchorUUID] = anchor
                    AnchorUpdate.Event.REMOVED -> meshes.remove(anchor.anchorUUID)
                    else -> Unit
                }
            },
        )
        PlaneTrackingManager.start()
        MeshTrackingManager.start()
        Log.i(
            TAG,
            "Surface tracking started: plane=${PlaneTrackingManager.state}, " +
                "mesh=${MeshTrackingManager.state}, consumerReady=$consumerReady",
        )
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        trackingScope = scope
        initialSyncJob = scope.launch { syncExistingAnchors() }
    }

    fun onConsumerReady() {
        if (!started) return
        consumerReady = true
        val cachedPlanes = planes.values.toList()
        cachedPlanes.forEach { anchor ->
            onPlaneUpdate(anchor, anchor.anchorUUID, AnchorUpdate.Event.LOADED)
        }
        Log.i(TAG, "Surface consumer ready: replayed=${cachedPlanes.size} cached planes")
        if (cachedPlanes.isEmpty() && initialSyncJob?.isActive != true) {
            initialSyncJob = trackingScope?.launch { syncExistingAnchors() }
        }
    }

    fun stop() {
        started = false
        consumerReady = false
        planeSubscription?.cancel()
        meshSubscription?.cancel()
        planeSubscription = null
        meshSubscription = null
        initialSyncJob?.cancel()
        initialSyncJob = null
        trackingScope?.cancel()
        trackingScope = null
        PlaneTrackingManager.stop()
        MeshTrackingManager.stop()
        planes.clear()
        meshes.clear()
    }

    fun surfaceCount(): Int = planes.size + meshes.size

    private suspend fun syncExistingAnchors() {
        var lastPlaneState: TrackingState? = null
        var lastMeshState: TrackingState? = null
        var meshSyncAttempted = false
        repeat(INITIAL_SYNC_ATTEMPTS) { attempt ->
            if (!started) return
            val planeRunning = PlaneTrackingManager.state == TrackingState.RUNNING
            val meshRunning = MeshTrackingManager.state == TrackingState.RUNNING
            if (PlaneTrackingManager.state != lastPlaneState || MeshTrackingManager.state != lastMeshState) {
                lastPlaneState = PlaneTrackingManager.state
                lastMeshState = MeshTrackingManager.state
                Log.i(
                    TAG,
                    "Surface state: plane=$lastPlaneState, mesh=$lastMeshState, attempt=${attempt + 1}",
                )
            }
            if (planeRunning || meshRunning) {
                if (planeRunning) {
                    runCatching { PlaneTrackingManager.loadAllAnchors() }
                        .onSuccess { anchors ->
                            anchors.forEach { anchor ->
                                val previous = planes.put(anchor.anchorUUID, anchor)
                                if (started && consumerReady && previous == null) {
                                    onPlaneUpdate(
                                        anchor,
                                        anchor.anchorUUID,
                                        AnchorUpdate.Event.LOADED,
                                    )
                                }
                            }
                            if (anchors.isNotEmpty() || attempt == 0) {
                                Log.i(
                                    TAG,
                                    "Plane sync: loaded=${anchors.size}, cached=${planes.size}, " +
                                        "consumerReady=$consumerReady, attempt=${attempt + 1}",
                                )
                            }
                        }
                        .onFailure { error -> Log.w(TAG, "Initial plane sync failed", error) }
                }
                if (meshRunning && !meshSyncAttempted) {
                    meshSyncAttempted = true
                    runCatching { MeshTrackingManager.loadAllAnchors() }
                        .onSuccess { anchors ->
                            anchors.forEach { anchor -> meshes[anchor.anchorUUID] = anchor }
                            Log.i(TAG, "Initial mesh sync: ${anchors.size} anchors")
                        }
                        .onFailure { error -> Log.w(TAG, "Initial mesh sync failed", error) }
                }
                if (planes.isNotEmpty()) {
                    Log.i(TAG, "Surface ready: ${planes.size} plane anchors available")
                    return
                }
            }
            if (
                attempt > 0 &&
                attempt % TRACKING_RECOVERY_INTERVAL_ATTEMPTS == 0 &&
                PlaneTrackingManager.state in RECOVERABLE_TRACKING_STATES
            ) {
                Log.i(TAG, "Restarting plane tracking from ${PlaneTrackingManager.state}")
                PlaneTrackingManager.start()
            }
            if (
                attempt > 0 &&
                attempt % TRACKING_RECOVERY_INTERVAL_ATTEMPTS == 0 &&
                MeshTrackingManager.state in RECOVERABLE_TRACKING_STATES
            ) {
                Log.i(TAG, "Restarting mesh tracking from ${MeshTrackingManager.state}")
                MeshTrackingManager.start()
            }
            delay(INITIAL_SYNC_DELAY_MILLIS)
            if (attempt == INITIAL_SYNC_ATTEMPTS - 1) {
                Log.w(
                    TAG,
                    "No plane anchors after warm-up: plane=${PlaneTrackingManager.state}, " +
                        "mesh=${MeshTrackingManager.state}, consumerReady=$consumerReady. " +
                        "Complete the system room scan and keep the headset awake in Full Space.",
                )
            }
        }
    }

    fun raycast(origin: Vector3, direction: Vector3, maxDistance: Float = 8f): SurfaceHit? {
        val normalized = direction.normalize()
        return nearestPlaneHit(origin, normalized, maxDistance)
            ?: nearestMeshHit(origin, normalized, maxDistance)
    }

    private fun nearestPlaneHit(origin: Vector3, direction: Vector3, maxDistance: Float): SurfaceHit? {
        var nearest: SurfaceHit? = null
        var nearestDistance = maxDistance
        planes.values.forEach { anchor ->
            forEachTriangle(anchor.vertices, anchor.indices, anchor.transform) { a, b, c ->
                val distance = SurfaceRayMath.intersectTriangle(origin, direction, a, b, c) ?: return@forEachTriangle
                if (distance < nearestDistance) {
                    nearestDistance = distance
                    nearest = SurfaceHit(
                        worldPosition = origin + direction * distance,
                        normal = Vector3.cross(b - a, c - a).normalize(),
                        semantic = anchor.semantics,
                        sourceId = anchor.anchorUUID,
                    )
                }
            }
        }
        return nearest
    }

    private fun nearestMeshHit(origin: Vector3, direction: Vector3, maxDistance: Float): SurfaceHit? {
        var nearest: SurfaceHit? = null
        var nearestDistance = maxDistance
        meshes.values.forEach { anchor ->
            forEachTriangle(anchor.vertices, anchor.indices, anchor.transform) { a, b, c ->
                val distance = SurfaceRayMath.intersectTriangle(origin, direction, a, b, c) ?: return@forEachTriangle
                if (distance < nearestDistance) {
                    nearestDistance = distance
                    nearest = SurfaceHit(
                        worldPosition = origin + direction * distance,
                        normal = Vector3.cross(b - a, c - a).normalize(),
                        semantic = anchor.semantics.firstOrNull() ?: SemanticLabelType.UNKNOWN,
                        sourceId = anchor.anchorUUID,
                    )
                }
            }
        }
        return nearest
    }

    private inline fun forEachTriangle(
        vertices: List<Vector3>,
        indices: List<Int>,
        transform: com.pico.spatial.core.math.Transform,
        block: (Vector3, Vector3, Vector3) -> Unit,
    ) {
        val worldVertices = vertices.map { vertex ->
            val scaled = vertex * transform.scale
            transform.position + transform.quaternion.rotateVector(scaled)
        }
        indices.chunked(3).forEach { triangle ->
            if (triangle.size == 3) {
                val a = worldVertices.getOrNull(triangle[0]) ?: return@forEach
                val b = worldVertices.getOrNull(triangle[1]) ?: return@forEach
                val c = worldVertices.getOrNull(triangle[2]) ?: return@forEach
                block(a, b, c)
            }
        }
    }

    private companion object {
        const val TAG = "SpatialRulerSurface"
        const val INITIAL_SYNC_ATTEMPTS = 48
        const val INITIAL_SYNC_DELAY_MILLIS = 250L
        const val TRACKING_RECOVERY_INTERVAL_ATTEMPTS = 4
        val RECOVERABLE_TRACKING_STATES = setOf(
            TrackingState.INITIALIZED,
            TrackingState.PAUSED,
            TrackingState.STOPPED,
        )
    }
}
