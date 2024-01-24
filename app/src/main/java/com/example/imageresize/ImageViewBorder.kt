package com.example.imageresize

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

class ImageViewBorder(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    private val paint = Paint()
    private var startX = 0f
    private var startY = 0f
    private var resizing = false
    private val padding = 10
    private var originalWidth = 0
    private var originalHeight = 0
    var minPageWidth = 300
    var minPageHeight = 200
    var maxPageHeight = 842
    var maxPageWidth = 594
    private val cornerDrawable: Drawable = context.resources.getDrawable(R.drawable.resize_corner_circle)

    init {
        paint.color = Color.RED  // Set the color of the line
        paint.strokeWidth = 10f  // Set the width of the line
        paint.style = Paint.Style.STROKE  // Set the style to stroke
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true

        cornerDrawable.setTint(Color.RED)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw a rectangle around the ImageView
        canvas.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            paint
        )

        // Draw corners at each corner of the rectangle
        drawCorner(canvas, padding.toFloat(), padding.toFloat())
        drawCorner(canvas, (width - padding).toFloat(), padding.toFloat())
        drawCorner(canvas, padding.toFloat(), (height - padding).toFloat())
        drawCorner(canvas, (width - padding).toFloat(), (height - padding).toFloat())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                resizing = isResizingArea(startX, startY)
                originalWidth = width
                originalHeight = height
            }

            MotionEvent.ACTION_MOVE -> {
                if (resizing) {
                    val deltaX = event.x - startX
                    val deltaY = event.y - startY

                    // Calculate new dimensions while maintaining aspect ratio
                    val aspectRatio = originalWidth.toFloat() / originalHeight.toFloat()

                    // Adjust new width and height based on the aspect ratio
                    val newWidth = originalWidth + deltaX
                    val newHeight = originalHeight + deltaY
                    if (newWidth.toInt() in minPageWidth..maxPageWidth && newHeight.toInt() in minPageHeight..maxPageHeight) {
                        val adjustedWidth = newWidth.coerceAtLeast(minPageWidth.toFloat())
                        val adjustedHeight = adjustedWidth / aspectRatio

                        layoutParams.width = adjustedWidth.toInt()
                        layoutParams.height = adjustedHeight.toInt()

                        requestLayout()
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                performClick()
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()

        resizing = false

        return true
    }

    private fun isResizingArea(x: Float, y: Float): Boolean {
        val rect = RectF(
            padding.toFloat(),
            padding.toFloat(),
            (width - padding).toFloat(),
            (height - padding).toFloat()
        )
        return rect.contains(x, y)
    }

    private fun drawCorner(canvas: Canvas, x: Float, y: Float) {
        cornerDrawable.setBounds(
            (x - cornerDrawable.intrinsicWidth / 2).toInt(),
            (y - cornerDrawable.intrinsicHeight / 2).toInt(),
            (x + cornerDrawable.intrinsicWidth / 2).toInt(),
            (y + cornerDrawable.intrinsicHeight / 2).toInt()
        )
        cornerDrawable.draw(canvas)
    }

    fun removeStrokeColor() {
        paint.color = Color.TRANSPARENT
        cornerDrawable.setTint(Color.TRANSPARENT)
    }
}