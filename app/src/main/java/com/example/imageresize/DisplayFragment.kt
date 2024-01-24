package com.example.imageresize

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imageresize.databinding.FragmentDisplayBinding

class DisplayFragment : Fragment() {
    private lateinit var binding: FragmentDisplayBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDisplayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        viewModel.imageBitmap.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.imageView2.setImageBitmap(it)
            }
        }
    }
}