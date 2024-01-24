package com.example.imageresize

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.imageresize.databinding.FragmentHomeBinding
import com.itextpdf.kernel.geom.PageSize


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    private val PICK_IMAGE_REQUEST = 1

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

        // Resize max height & width
        binding.imageView.maxPageHeight = pageHeight
        binding.imageView.maxPageWidth = pageWidth

        // Resize min height & width
        binding.imageView.minPageHeight = (pageHeight / 4)
        binding.imageView.minPageWidth = (pageWidth / 3)

        binding.buSet.setOnClickListener {
            try {
                binding.imageView.removeStrokeColor()
                val frameBitmap = getBitmapFromView(binding.bgPage)
                viewModel.imageBitmap.value = frameBitmap
                findNavController().navigate(R.id.action_homeFragment_to_diplayFragment)
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
            }
        }

        binding.buPick.setOnClickListener {
            openGallery()
        }
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
            binding.imageView.setImageBitmap(bitmap)
        }
    }
}