package com.example.imageresize

import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import com.example.imageresize.databinding.ActivityMainBinding
import com.itextpdf.kernel.geom.PageSize

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val pageHeight = PageSize.A4.height.toInt()
        val pageWidth = PageSize.A4.width.toInt()
        Log.d("pageHeight", pageHeight.toString())
        Log.d("pageWidth", pageWidth.toString())
        val pageLayoutParams = FrameLayout.LayoutParams(pageWidth, pageHeight)
        pageLayoutParams.gravity = Gravity.CENTER
        binding.bgPage.layoutParams = pageLayoutParams

        // Get the dimensions of ImageViewBorder (imageView)
        val tempBitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
        val imageViewHeight = tempBitmap.height
        val imageViewWidth = tempBitmap.width
        val padding = 70

        Log.d("imageViewHeight", imageViewHeight.toString())
        Log.d("imageViewWidth", imageViewWidth.toString())

        // Set the dimensions of ImageViewBorder based on imageView
        val borderLayoutParams = FrameLayout.LayoutParams(imageViewWidth - padding, imageViewHeight - padding)
        borderLayoutParams.gravity = Gravity.CENTER
        binding.imageView.layoutParams = borderLayoutParams
    }
}