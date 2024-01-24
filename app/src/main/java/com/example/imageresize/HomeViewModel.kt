package com.example.imageresize

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val imageBitmap: MutableLiveData<Bitmap> = MutableLiveData()
}