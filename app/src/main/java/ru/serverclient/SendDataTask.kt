package ru.serverclient

import android.os.AsyncTask
import android.util.Log
import java.io.DataOutputStream
import java.net.Socket

class SendDataTask : AsyncTask<String, Void, String>() {
    private var socket: Socket? = null
    private var outStream: DataOutputStream? = null
    private var message: String? = null

    override fun doInBackground(vararg params: String?): String? {
        val param = params[0]
        val ipPort = param?.split(":")!!

        message = params[1]

        var ip = "192.168.0.61"
        var port = 64499

        if (ipPort.size > 1) {
            ip = ipPort[0]
            port = ipPort[1].toInt()
        }

        socket = Socket(ip, port)
        outStream = DataOutputStream(socket?.getOutputStream())
        outStream?.write(message?.toByteArray())

        Log.e("SENT", message)

        outStream?.close()
        socket?.close()

        return null
    }
}