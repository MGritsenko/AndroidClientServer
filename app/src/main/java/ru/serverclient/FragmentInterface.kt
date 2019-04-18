package ru.serverclient

import android.graphics.Bitmap

interface FragmentInterface {
    fun update(text: String, img: Bitmap)
}

interface OnDataReadyCallBack {
    fun receiveData(text: String, img: Bitmap)
}