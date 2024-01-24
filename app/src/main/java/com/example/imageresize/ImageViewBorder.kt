package com.example.imageresize

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

class ImageViewBorder(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    private val paint = Paint()
    private var startX = 0f
    private var startY = 0f
    private var resizing = false
    private val padding = 10  // Adjust the padding value as needed
    var minPageWidth = 300
    var minPageHeight = 200
    var maxPageHeight = 842
    var maxPageWidth = 594

    init {
        paint.color = Color.RED  // Set the color of the line
        paint.strokeWidth = 10f  // Set the width of the line
        paint.style = Paint.Style.STROKE  // Set the style to stroke
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
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
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                resizing = isResizingArea(startX, startY)
            }

            MotionEvent.ACTION_MOVE -> {
                if (resizing) {
                    val newWidth = width + (event.x - startX)
                    val newHeight = height + (event.y - startY)
                    Log.d("newHeight", newHeight.toString())


                    if ((newWidth.toInt() in minPageWidth..maxPageWidth && newHeight.toInt() in minPageHeight..maxPageHeight)) {
                        layoutParams.width = newWidth.toInt()
                        layoutParams.height = newHeight.toInt()
                        requestLayout()
                    }

                    startX = event.x
                    startY = event.y
                }
            }

            MotionEvent.ACTION_UP -> {
                resizing = false
            }
        }
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

    fun removeStrokeColor() {
        paint.color = Color.TRANSPARENT
    }
}