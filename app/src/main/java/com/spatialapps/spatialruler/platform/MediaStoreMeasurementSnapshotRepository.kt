package com.spatialapps.spatialruler.platform

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Environment
import android.provider.MediaStore
import com.spatialapps.spatialruler.data.repository.MeasurementSnapshotRepository
import com.spatialapps.spatialruler.ui.measurement.MeasurementUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaStoreMeasurementSnapshotRepository(
    private val context: Context,
) : MeasurementSnapshotRepository {
    override suspend fun capture(state: MeasurementUiState): String = withContext(Dispatchers.IO) {
        val bitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.rgb(8, 17, 28))
        val blue = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(0, 212, 255)
            strokeWidth = 10f
            style = Paint.Style.STROKE
        }
        val white = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = 66f
            style = Paint.Style.FILL
        }
        val secondary = Paint(white).apply { textSize = 36f; color = Color.LTGRAY }
        canvas.drawText("实景空间标尺", 96f, 112f, white)
        canvas.drawText("${state.mode.displayName} · ${state.displayValue}", 96f, 196f, secondary)
        val projected = state.points.map { point ->
            Pair(960f + point.x * 420f, 610f - point.y * 420f - point.z * 80f)
        }
        projected.zipWithNext().forEach { (start, end) -> canvas.drawLine(start.first, start.second, end.first, end.second, blue) }
        projected.forEachIndexed { index, point ->
            canvas.drawCircle(point.first, point.second, 18f, blue)
            canvas.drawText("${index + 1}", point.first + 24f, point.second - 24f, secondary)
        }
        canvas.drawText("${state.points.size} 个标记点 · ${state.message}", 96f, 1000f, secondary)

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "SpatialRuler_${System.currentTimeMillis()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/SpatialRuler")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        val resolver = context.contentResolver
        val uri = checkNotNull(resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values))
        resolver.openOutputStream(uri)?.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
            ?: error("无法打开截图输出流")
        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(uri, values, null, null)
        bitmap.recycle()
        uri.toString()
    }
}
