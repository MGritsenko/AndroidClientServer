package ru.serverclient

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_video.view.*

class ReceiveVideoFragment : Fragment(), FragmentInterface {

    private var fragmentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_video, container, false)
        return fragmentView
    }

    override fun update(text: String, img: Bitmap) {
        fragmentView?.imageView2?.setImageBitmap(img)
    }
}
