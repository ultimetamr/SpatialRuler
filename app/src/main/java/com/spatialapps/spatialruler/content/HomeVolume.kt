package com.spatialapps.spatialruler.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.pico.spatial.core.ecs.ModelEntity
import com.pico.spatial.core.ecs.TransformComponent
import com.pico.spatial.core.math.EulerAngles
import com.pico.spatial.core.math.Color4
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.core.ecs.resource.BlendingMode
import com.pico.spatial.core.ecs.resource.MeshResource
import com.pico.spatial.core.ecs.resource.UnlitMaterial
import com.pico.spatial.ui.foundation.content.SpatialView
import com.pico.spatial.ui.platform.containers.LocalSpatialNavigator
import com.pico.spatial.ui.platform.containers.StageStyle
import com.spatialapps.spatialruler.ui.hub.HubRoute
import kotlinx.coroutines.launch

@Composable
fun HomeVolume() {
    val navigator = LocalSpatialNavigator.current
    val coroutineScope = rememberCoroutineScope()
    SpatialView(
        initial = { content, attachments ->
            val material = UnlitMaterial.create(BlendingMode.TRANSPARENT).apply {
                setBaseColor(Color4(0f, 0.831f, 1f, 0.6f))
            }
            val line = ModelEntity(
                MeshResource.createCylinder(height = 0.7f, radius = 0.012f),
                material,
            ).apply {
                components[TransformComponent::class.java]?.apply {
                    setPosition(Vector3(0f, 0.34f, 0f))
                    setEulerAngles(EulerAngles(0f, 0f, 90f))
                }
            }
            val left = ModelEntity(MeshResource.createSphere(0.035f), material).apply {
                components[TransformComponent::class.java]?.setPosition(Vector3(-0.35f, 0.34f, 0f))
            }
            val right = ModelEntity(MeshResource.createSphere(0.035f), material).apply {
                components[TransformComponent::class.java]?.setPosition(Vector3(0.35f, 0.34f, 0f))
            }
            content.addEntity(line)
            content.addEntity(left)
            content.addEntity(right)

            attachments.entity(id = "hub")?.apply {
                components[TransformComponent::class.java]?.apply {
                    setPosition(Vector3(0f, -0.05f, 0.18f))
                }
                content.addEntity(this)
            }
        },
        attachments = {
            AttachmentPanel(id = "hub") {
                HubRoute(
                    onStartMeasurement = {
                        coroutineScope.launch {
                            navigator.openStage("MeasurementStage", style = StageStyle.Mixed)
                        }
                    },
                )
            }
        }
    )
}
