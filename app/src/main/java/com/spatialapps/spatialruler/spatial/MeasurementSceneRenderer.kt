package com.spatialapps.spatialruler.spatial

import android.animation.ValueAnimator
import android.util.Log
import android.view.animation.DecelerateInterpolator
import com.pico.spatial.core.ecs.CollisionComponent
import com.pico.spatial.core.ecs.Entity
import com.pico.spatial.core.ecs.InteractableComponent
import com.pico.spatial.core.ecs.LookAtComponent
import com.pico.spatial.core.ecs.ModelEntity
import com.pico.spatial.core.ecs.TransformComponent
import com.pico.spatial.core.ecs.resource.BlendingMode
import com.pico.spatial.core.ecs.resource.MeshResource
import com.pico.spatial.core.ecs.resource.PhysicsMaterialResource
import com.pico.spatial.core.ecs.resource.PolygonFillMode
import com.pico.spatial.core.ecs.resource.ShapeResource
import com.pico.spatial.core.ecs.resource.UnlitMaterial
import com.pico.spatial.core.math.Color4
import com.pico.spatial.core.math.Quat
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.sense.base.AnchorUpdate
import com.pico.spatial.sense.plane.PlaneAnchor
import com.spatialapps.spatialruler.domain.model.MeasurementMode
import com.spatialapps.spatialruler.domain.model.SpatialPoint
import com.spatialapps.spatialruler.domain.usecase.BuildRulerTicksUseCase
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.sqrt

class MeasurementSceneRenderer {
    private var root: Entity? = null
    private var valueAttachment: Entity? = null
    private var majorTickAttachments: List<Entity> = emptyList()
    private val pointEntities = mutableListOf<Entity>()
    private val pointAnimators = mutableMapOf<Entity, ValueAnimator>()
    private val geometryEntities = mutableListOf<Entity>()
    private val geometryAnimators = mutableListOf<ValueAnimator>()
    private val planeEntities = ConcurrentHashMap<UUID, Entity>()
    private val stablePlaneUpdateLogs = ConcurrentHashMap.newKeySet<UUID>()
    private var tapPositionConverter: ((Vector3) -> SpatialPoint)? = null
    private var renderedPoints: List<SpatialPoint>? = null
    private var renderedMode: MeasurementMode? = null
    private val lineMeshResource = lazy {
        MeshResource.createCylinder(height = 1f, radius = LINE_RADIUS_METERS).apply { toGlobal() }
    }
    private val tickMeshResource = lazy {
        MeshResource.createCylinder(height = 1f, radius = TICK_RADIUS_METERS).apply { toGlobal() }
    }
    private val pointMeshResource = lazy { MeshResource.createSphere(0.018f).apply { toGlobal() } }
    private val cursorMeshResource = lazy { MeshResource.createSphere(0.012f).apply { toGlobal() } }
    private val lineMesh by lineMeshResource
    private val tickMesh by tickMeshResource
    private val pointMesh by pointMeshResource
    private val cursorMesh by cursorMeshResource
    private val rulerMaterialResource = lazy {
        UnlitMaterial.create(BlendingMode.TRANSPARENT).apply {
            setBaseColor(Color4(0f, 0.831f, 1f, 0.72f))
            toGlobal()
        }
    }
    private val cursorMaterialResource = lazy {
        UnlitMaterial.create(BlendingMode.TRANSPARENT).apply {
            setBaseColor(Color4(1f, 1f, 1f, 0.9f))
            toGlobal()
        }
    }
    private val planeMaterialResource = lazy {
        UnlitMaterial.create(BlendingMode.TRANSPARENT).apply {
            setBaseColor(Color4(0f, 0.831f, 1f, 0.05f))
            setPolygonFillMode(PolygonFillMode.LINE)
            setDepthWrite(false)
            toGlobal()
        }
    }
    private val rulerMaterial by rulerMaterialResource
    private val cursorMaterial by cursorMaterialResource
    private val planeMaterial by planeMaterialResource
    private var cursorEntity: Entity? = null

    fun initialize(rootEntity: Entity, attachment: Entity?, tickAttachments: List<Entity>) {
        root = rootEntity
        valueAttachment = attachment?.apply {
            components.set(LookAtComponent().apply {
                setViewerAsTarget()
                alignLocalUpToWorldUp = true
            })
            rootEntity.addChild(this)
        }
        majorTickAttachments = tickAttachments.onEach { entity ->
            entity.components.set(LookAtComponent().apply {
                setViewerAsTarget()
                alignLocalUpToWorldUp = true
            })
            entity.components[TransformComponent::class.java]?.setScaleVector(Vector3.ZERO)
            rootEntity.addChild(entity)
        }
        cursorEntity = ModelEntity(cursorMesh, cursorMaterial).apply {
            components[TransformComponent::class.java]?.setScaleVector(Vector3.ZERO)
            rootEntity.addChild(this)
        }
    }

    fun render(points: List<SpatialPoint>, mode: MeasurementMode) {
        val rootEntity = root ?: return
        if (points == renderedPoints && mode == renderedMode) return
        val previousPoints = renderedPoints.orEmpty()
        syncPointEntities(rootEntity, previousPoints, points)
        renderedPoints = points.toList()
        renderedMode = mode
        geometryAnimators.forEach(ValueAnimator::cancel)
        geometryAnimators.clear()
        geometryEntities.forEach { it.destroy() }
        geometryEntities.clear()
        val segments = points.zipWithNext().toMutableList()
        if (mode == MeasurementMode.AREA && points.size == 4) segments += points.last() to points.first()
        segments.forEach { (start, end) ->
            addSegment(rootEntity, start, end)
            BuildRulerTicksUseCase()(start, end).forEach { tick -> addTick(rootEntity, start, end, tick.position, tick.isMajor) }
        }
        val majorTicks = segments.flatMap { (start, end) ->
            BuildRulerTicksUseCase()(start, end).filter { it.isMajor }
        }
        majorTickAttachments.forEachIndexed { index, entity ->
            val transform = entity.components[TransformComponent::class.java] ?: return@forEachIndexed
            val tick = majorTicks.getOrNull(index)
            if (tick == null) transform.setScaleVector(Vector3.ZERO)
            else {
                transform.setScaleVector(Vector3.ONE)
                transform.setPosition(tick.position.toVector() + Vector3(0f, 0.035f, 0f))
            }
        }
        val last = points.lastOrNull()
        valueAttachment?.components?.get(TransformComponent::class.java)?.setPosition(
            last?.let { Vector3(it.x, it.y + 0.06f, it.z) } ?: Vector3(0f, 0.25f, -1.1f),
        )
    }

    fun updateCursor(worldPosition: Vector3?) {
        val rootEntity = root ?: return
        val cursor = cursorEntity ?: return
        val transform = cursor.components[TransformComponent::class.java] ?: return
        if (worldPosition == null) {
            transform.setScaleVector(Vector3.ZERO)
            return
        }
        transform.setScaleVector(Vector3.ONE)
        transform.setPosition(rootEntity.convertPositionFrom(worldPosition, null))
    }

    fun worldToPoint(worldPosition: Vector3): SpatialPoint? {
        val local = root?.convertPositionFrom(worldPosition, null) ?: return null
        return SpatialPoint(local.x, local.y, local.z)
    }

    fun setTapPositionConverter(converter: (Vector3) -> SpatialPoint) {
        tapPositionConverter = converter
    }

    fun viewPositionToPoint(x: Float, y: Float, z: Float): SpatialPoint? =
        tapPositionConverter?.invoke(Vector3(x, y, z))

    fun isPlaneEntity(entity: Entity): Boolean = planeEntities.values.any { it == entity }

    fun onPlaneUpdate(anchor: PlaneAnchor?, id: UUID, event: AnchorUpdate.Event) {
        if (event == AnchorUpdate.Event.REMOVED || anchor == null) {
            planeEntities.remove(id)?.destroy()
            stablePlaneUpdateLogs.remove(id)
            return
        }
        val rootEntity = root ?: return
        planeEntities[id]?.let { entity ->
            updatePlaneTransform(rootEntity, entity, anchor)
            if (stablePlaneUpdateLogs.add(id)) {
                Log.i(TAG, "Plane updates reuse stable entity id=$id")
            }
            return
        }
        runCatching {
            val mesh = MeshResource.loadFromPlaneAnchor(id)
            val interactionBounds = Vector3(
                anchor.boundingBoxSize.x.coerceAtLeast(MINIMUM_PLANE_HIT_SIZE_METERS),
                anchor.boundingBoxSize.y.coerceAtLeast(MINIMUM_PLANE_HIT_SIZE_METERS),
                PLANE_HIT_THICKNESS_METERS,
            )
            ModelEntity(mesh, planeMaterial).apply {
                components.set(
                    CollisionComponent(
                        collisionShape = listOf(
                            ShapeResource.createStaticMesh(mesh),
                            ShapeResource.createBox(interactionBounds),
                        ),
                        physicsMaterial = PhysicsMaterialResource(),
                    ),
                )
                components.set(InteractableComponent())
                updatePlaneTransform(rootEntity, this, anchor)
                rootEntity.addChild(this)
                planeEntities[id] = this
            }
            Log.i(
                TAG,
                "Interactive plane id=$id semantic=${anchor.semantics} hitBox=$interactionBounds",
            )
        }.onFailure { error -> Log.w(TAG, "Unable to create interactive plane $id", error) }
    }

    private fun updatePlaneTransform(rootEntity: Entity, entity: Entity, anchor: PlaneAnchor) {
        entity.components[TransformComponent::class.java]?.apply {
            setPosition(rootEntity.convertPositionFrom(anchor.transform.position, null))
            setQuaternion(rootEntity.convertRotationFrom(anchor.transform.quaternion, null))
        }
    }

    fun close() {
        pointAnimators.values.toList().forEach(ValueAnimator::cancel)
        pointAnimators.clear()
        geometryAnimators.forEach(ValueAnimator::cancel)
        geometryAnimators.clear()
        pointEntities.forEach { it.destroy() }
        pointEntities.clear()
        geometryEntities.forEach { it.destroy() }
        geometryEntities.clear()
        planeEntities.values.forEach { it.destroy() }
        planeEntities.clear()
        stablePlaneUpdateLogs.clear()
        cursorEntity?.destroy()
        cursorEntity = null
        valueAttachment = null
        majorTickAttachments = emptyList()
        tapPositionConverter = null
        renderedPoints = null
        renderedMode = null
        root = null
        closePersistentResources()
    }

    private fun syncPointEntities(
        rootEntity: Entity,
        previousPoints: List<SpatialPoint>,
        points: List<SpatialPoint>,
    ) {
        val retainedCount = PointEntitySync.commonPrefixSize(previousPoints, points)
        val removedCount = pointEntities.size - retainedCount
        for (index in pointEntities.lastIndex downTo retainedCount) {
            val entity = pointEntities.removeAt(index)
            pointAnimators.remove(entity)?.cancel()
            entity.destroy()
        }
        points.drop(retainedCount).forEach { point -> addPoint(rootEntity, point) }
        Log.i(
            TAG,
            "Point sync retained=$retainedCount added=${points.size - retainedCount} removed=$removedCount",
        )
    }

    private fun addPoint(rootEntity: Entity, point: SpatialPoint) {
        val entity = ModelEntity(pointMesh, rulerMaterial).apply {
            components[TransformComponent::class.java]?.apply {
                setPosition(point.toVector())
                setScaleVector(Vector3(POINT_PLACEMENT_START_SCALE))
            }
            rootEntity.addChild(this)
        }
        pointEntities += entity
        pointAnimators[entity] = animateScale(
            entity = entity,
            from = POINT_PLACEMENT_START_SCALE,
            to = POINT_FINAL_SCALE,
            durationMillis = POINT_PLACEMENT_DURATION_MILLIS,
        )
    }

    private fun addSegment(rootEntity: Entity, start: SpatialPoint, end: SpatialPoint) {
        val a = start.toVector()
        val b = end.toVector()
        val delta = b - a
        val length = delta.length()
        if (length <= 0.0001f) return
        val entity = ModelEntity(lineMesh, rulerMaterial).apply {
            components[TransformComponent::class.java]?.apply {
                setPosition((a + b) * 0.5f)
                setQuaternion(rotationFromUp(delta / length))
                setScaleVector(Vector3(1f, 0.01f, 1f))
            }
            rootEntity.addChild(this)
        }
        geometryEntities += entity
        Log.i(TAG, "Segment geometry length=$length radius=$LINE_RADIUS_METERS")
        val transform = entity.components[TransformComponent::class.java]
        ValueAnimator.ofFloat(0.01f, length).apply {
            duration = 220L
            interpolator = DecelerateInterpolator()
            addUpdateListener { transform?.setScaleVector(Vector3(1f, it.animatedValue as Float, 1f)) }
            start()
            geometryAnimators += this
        }
    }

    private fun addTick(rootEntity: Entity, start: SpatialPoint, end: SpatialPoint, point: SpatialPoint, major: Boolean) {
        val direction = (end.toVector() - start.toVector()).normalize()
        var perpendicular = Vector3.cross(direction, Vector3.UP)
        if (perpendicular.length() < 0.01f) perpendicular = Vector3.cross(direction, Vector3.RIGHT)
        perpendicular = perpendicular.normalize()
        val length = if (major) 0.045f else 0.024f
        val entity = ModelEntity(tickMesh, rulerMaterial).apply {
            components[TransformComponent::class.java]?.apply {
                setPosition(point.toVector())
                setQuaternion(rotationFromUp(perpendicular))
                setScaleVector(Vector3(1f, length, 1f))
            }
            rootEntity.addChild(this)
        }
        geometryEntities += entity
    }

    private fun rotationFromUp(direction: Vector3): Quat {
        val from = Vector3.UP
        val dot = Vector3.dot(from, direction)
        if (dot < -0.9999f) return Quat(Vector3.RIGHT, 180f)
        val cross = Vector3.cross(from, direction)
        val w = sqrt(from.length() * from.length() * direction.length() * direction.length()) + dot
        return Quat(cross.x, cross.y, cross.z, w).normalize()
    }

    private fun animateScale(entity: Entity, from: Float, to: Float, durationMillis: Long): ValueAnimator {
        val transform = entity.components[TransformComponent::class.java]
        return ValueAnimator.ofFloat(from, to).apply {
            duration = durationMillis
            interpolator = DecelerateInterpolator()
            addUpdateListener { transform?.setScaleVector(Vector3(it.animatedValue as Float)) }
            start()
        }
    }

    private fun SpatialPoint.toVector() = Vector3(x, y, z)

    private fun closePersistentResources() {
        if (lineMeshResource.isInitialized()) lineMeshResource.value.close()
        if (tickMeshResource.isInitialized()) tickMeshResource.value.close()
        if (pointMeshResource.isInitialized()) pointMeshResource.value.close()
        if (cursorMeshResource.isInitialized()) cursorMeshResource.value.close()
        if (rulerMaterialResource.isInitialized()) rulerMaterialResource.value.close()
        if (cursorMaterialResource.isInitialized()) cursorMaterialResource.value.close()
        if (planeMaterialResource.isInitialized()) planeMaterialResource.value.close()
    }

    private companion object {
        const val TAG = "SpatialRulerScene"
        const val POINT_PLACEMENT_START_SCALE = 0.65f
        const val POINT_FINAL_SCALE = 1f
        const val POINT_PLACEMENT_DURATION_MILLIS = 140L
        const val LINE_RADIUS_METERS = 0.006f
        const val TICK_RADIUS_METERS = 0.003f
        const val MINIMUM_PLANE_HIT_SIZE_METERS = 0.08f
        const val PLANE_HIT_THICKNESS_METERS = 0.02f
    }
}
