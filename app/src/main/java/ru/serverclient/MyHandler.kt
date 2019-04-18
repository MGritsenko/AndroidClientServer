package ru.serverclient

import android.graphics.Bitmap
import android.os.Handler
import android.os.Message

class MyHandler(private val parent: FragmentHolder) : Handler() {

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)

        if (msg != null) {
            val img = msg.data.let { msg.data.getParcelable<Bitmap>("BitmapImage") } ?: run { null }

            if (img != null) {
                parent.update("dd", img)
            }
        }
    }
}