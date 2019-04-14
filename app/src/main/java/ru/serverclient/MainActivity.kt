package ru.serverclient

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.system.Os.socket







class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendButton.setOnClickListener {
            val backgroundTask = BackgroundTask()
            backgroundTask.execute(ipEdit.text.toString(), messageEdit.text.toString())
        }

        val handler = MyHandler(this)
        val thread = Thread(MyServer(handler))
        thread.start()
    }

    fun update(text: String, img: Bitmap){
        textView.text = text
        imageView.setImageBitmap(img)
    }

    class MyHandler(private val parent: MainActivity) : Handler(){

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)

            if(msg != null)
            {
                val img = msg.data.let {  msg.data.getParcelable<Bitmap>("BitmapImage") } ?: run { null }

                if(img != null)
                {
                    parent.update("dd", img)
                }

            }

            //Log.e("HANDLER", "COOL")

//            when (msg?.what) {
//                1 -> {
//                    val c = msg.obj as Int
//                }
//                2 -> {
//                    val m = msg.obj as String
//                }
//                else -> super.handleMessage(msg)
//            }
        }
    }

    class MyServer(handler: MyHandler) : Runnable
    {
        private var serverSocket: ServerSocket? = null
        private var socket: Socket? = null
        private var inStream: InputStream? = null
        private var buffReader: BufferedReader? = null
        private var handler: MyHandler? = handler
        private var message: String? = null

        override fun run() {
            serverSocket = ServerSocket(64499)

            Log.e("STARTED", "STARTED")

            while (true)
            {
                Log.e("ACCEPT", "ACCEPT")
                socket = serverSocket?.accept()
                Log.e("ACCEPTED", "ACCEPTED")

                inStream = socket?.getInputStream()
                val byteArray = inStream?.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size!!)
                inStream?.close()

                val msg = handler?.obtainMessage()
                val bundle = Bundle()
                bundle.putString("COOL", message)
                bundle.putParcelable("BitmapImage", bitmap)
                msg?.data = bundle
                handler?.sendMessage(msg)
            }
        }
    }

    class BackgroundTask : AsyncTask<String, Void, String>() {
        private var socket: Socket? = null
        private var outStream: DataOutputStream? = null
        private var ip: String? = null
        private var message: String? = null

        override fun doInBackground(vararg params: String?): String? {
            ip = params[0]
            message = params[1]

            socket = Socket("192.168.0.61", 64499)
            outStream = DataOutputStream(socket?.getOutputStream())
            outStream?.write(message?.toByteArray())

            Log.e("SENT", message)

            outStream?.close()

            return null
        }
    }
}


//Log.e("ACCEPT", "ACCEPT")
//socket = serverSocket?.accept()
//Log.e("ACCEPTED", "ACCEPTED")
//
//inStream = InputStreamReader(socket?.getInputStream())
//buffReader = BufferedReader(inStream)
//
//val size = buffReader?.read()
//var str = ""
//while (str.length < size!!)
//{
//    str += buffReader?.read()!!.toChar().toString()
//}
//
//val data = ByteArray(str.toInt())
//val count = inStream?.read(data)
//
////                message = buffReader?.readLine()
//val charBuffer = CharArray(str.length)
////val bitmapdata = buffReader?.read(charBuffer, size, str.length)
//val bitmap = BitmapFactory.decodeByteArray(charBuffer.toString().toByteArray(), size, str.length)
//
//val msg = handler?.obtainMessage()
//val bundle = Bundle()
//bundle.putString("COOL", message)
//bundle.putParcelable("BitmapImage", bitmap)
//msg?.data = bundle
//handler?.sendMessage(msg)
