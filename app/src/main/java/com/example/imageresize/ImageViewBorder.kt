package com.example.imageresize

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class ImageViewBorder(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    private val paint = Paint()

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
}