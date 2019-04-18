package ru.serverclient


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_init_connection.view.*

class InitConnectionFragment : Fragment(), FragmentInterface {

    private var fragmentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentView = inflater.inflate(R.layout.fragment_init_connection, container, false)

        fragmentView?.sendButton?.setOnClickListener {
            val backgroundTask = SendDataTask()
            backgroundTask.execute(fragmentView?.ipEdit?.text.toString(), fragmentView?.idEdit?.text.toString())
        }

        return fragmentView
    }

    override fun update(text: String, img: Bitmap) {
        fragmentView?.textView?.text = text
        fragmentView?.imageView?.setImageBitmap(img)
    }
}
