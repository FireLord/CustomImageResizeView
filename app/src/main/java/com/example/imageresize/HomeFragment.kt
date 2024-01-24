package com.example.imageresize

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.example.imageresize.databinding.FragmentHomeBinding
import com.itextpdf.kernel.geom.PageSize

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

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
        val borderLayoutParams =
            FrameLayout.LayoutParams(imageViewWidth - padding, imageViewHeight - padding)
        borderLayoutParams.gravity = Gravity.CENTER
        binding.imageView.layoutParams = borderLayoutParams

        binding.button.setOnClickListener {
            viewModel.imageBitmap.value = tempBitmap
            findNavController().navigate(R.id.action_homeFragment_to_diplayFragment)
        }
    }
}