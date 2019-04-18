package ru.serverclient

import android.os.AsyncTask
import android.util.Log
import java.io.DataOutputStream
import java.net.Socket

class SendDataTask : AsyncTask<String, Void, String>() {
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
        socket?.close()

        return null
    }
}