package com.spatialapps.spatialruler.spatial

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.tracking.DataProvider
import com.pico.spatial.tracking.hand.HandJoint
import com.pico.spatial.tracking.hand.HandPose
import com.pico.spatial.tracking.hand.HandTrackingData
import com.pico.spatial.tracking.hand.HandTrackingProvider
import com.spatialapps.spatialruler.platform.MeasurementInteractionBridge
import com.spatialapps.spatialruler.ui.measurement.MeasurementEvent
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.abs
import kotlin.math.sign

class HandGestureController(
    private val surfaces: SurfaceTrackingController,
    private val renderer: MeasurementSceneRenderer,
    private val onEvent: (MeasurementEvent) -> Unit,
) : DataProvider.DataListener<HandTrackingData> {
    private val provider = HandTrackingProvider()
    private val mainHandler = Handler(Looper.getMainLooper())
    private val latestData = AtomicReference<HandTrackingData?>(null)
    private val updatePosted = AtomicBoolean(false)
    @Volatile private var started = false
    private var pinchActive = false
    private var pinchStartedAt = 0L
    private var finishSent = false
    private var openPalmStartedAt = 0L
    private var clearSent = false
    private var previousWristX = 0f
    private var previousWristAt = 0L
    private var previousWaveDirection = 0f
    private val waveFlips = ArrayDeque<Long>()

    fun start() {
        started = true
        provider.addListener(this)
        val result = provider.start()
        post(MeasurementEvent.SystemMessage("手势跟踪：$result，正在检测空间平面"))
    }

    fun stop() {
        started = false
        provider.removeListener(this)
        provider.stop()
        latestData.set(null)
        renderer.updateCursor(null)
        MeasurementInteractionBridge.currentCursorPoint = null
    }

    override fun onProvideData(data: HandTrackingData) {
        if (!started) return
        latestData.set(data)
        if (updatePosted.compareAndSet(false, true)) {
            mainHandler.post(::drainLatestDataOnMainThread)
        }
    }

    private fun drainLatestDataOnMainThread() {
        val data = latestData.getAndSet(null)
        if (started && data != null) handleDataOnMainThread(data)
        updatePosted.set(false)
        if (started && latestData.get() != null && updatePosted.compareAndSet(false, true)) {
            mainHandler.post(::drainLatestDataOnMainThread)
        }
    }

    private fun handleDataOnMainThread(data: HandTrackingData) {
        val hand = data.right ?: data.left ?: run {
            renderer.updateCursor(null)
            MeasurementInteractionBridge.currentCursorPoint = null
            return
        }
        val indexTip = hand[HandJoint.Index.INDEX_TIP].position
        val indexDistal = hand[HandJoint.Index.INDEX_DISTAL].position
        val direction = indexTip - indexDistal
        val hit = direction.takeIf { it.length() > 0.001f }?.let { surfaces.raycast(indexTip, it.normalize()) }
        renderer.updateCursor(hit?.worldPosition)
        MeasurementInteractionBridge.currentCursorPoint = hit?.worldPosition?.let(renderer::worldToPoint)
        interpretPinch(hand)
        interpretOpenPalm(hand)
        interpretWave(hand)
    }

    private fun interpretPinch(hand: HandPose) {
        val index = hand[HandJoint.Index.INDEX_TIP].position
        val thumb = hand[HandJoint.Index.THUMB_TIP].position
        val isPinching = Vector3.distance(index, thumb) < 0.028f
        val now = SystemClock.elapsedRealtime()
        if (isPinching && !pinchActive) {
            pinchActive = true
            pinchStartedAt = now
            finishSent = false
        }
        if (isPinching && !finishSent && now - pinchStartedAt >= 700L) {
            finishSent = true
            post(MeasurementEvent.Finish)
        }
        if (!isPinching) pinchActive = false
    }

    private fun interpretOpenPalm(hand: HandPose) {
        val palm = hand[HandJoint.Index.PALM].position
        val tips = listOf(
            HandJoint.Index.INDEX_TIP,
            HandJoint.Index.MIDDLE_TIP,
            HandJoint.Index.RING_TIP,
            HandJoint.Index.LITTLE_TIP,
        )
        val open = tips.all { Vector3.distance(hand[it].position, palm) > 0.075f } &&
            Vector3.distance(hand[HandJoint.Index.THUMB_TIP].position, palm) > 0.055f
        val now = SystemClock.elapsedRealtime()
        if (open && openPalmStartedAt == 0L) openPalmStartedAt = now
        if (open && !clearSent && now - openPalmStartedAt >= 900L) {
            clearSent = true
            post(MeasurementEvent.Clear)
        }
        if (!open) {
            openPalmStartedAt = 0L
            clearSent = false
        }
    }

    private fun interpretWave(hand: HandPose) {
        val now = SystemClock.elapsedRealtime()
        val x = hand[HandJoint.Index.WRIST].position.x
        if (previousWristAt != 0L) {
            val seconds = (now - previousWristAt).coerceAtLeast(1L) / 1000f
            val velocity = (x - previousWristX) / seconds
            val direction = velocity.sign
            if (abs(velocity) > 0.22f && previousWaveDirection != 0f && direction != previousWaveDirection) {
                waveFlips += now
                while (waveFlips.isNotEmpty() && now - waveFlips.first() > 1200L) waveFlips.removeFirst()
                if (waveFlips.size >= 3) {
                    waveFlips.clear()
                    post(MeasurementEvent.Undo)
                }
            }
            if (abs(velocity) > 0.22f) previousWaveDirection = direction
        }
        previousWristX = x
        previousWristAt = now
    }

    private fun post(event: MeasurementEvent) {
        mainHandler.post { onEvent(event) }
    }
}
