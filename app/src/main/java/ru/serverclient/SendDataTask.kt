package ru.serverclient

import android.os.AsyncTask
import android.util.Log
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

class SendDataTask : AsyncTask<String, Void, String>() {
    private var socket: Socket? = null
    private var outStream: DataOutputStream? = null
    private var message: String? = null

    override fun doInBackground(vararg params: String?): String? {
        val param = params[0]
        val ipPort = param?.split(":")
        message = params[1]

        val ip: String? = ipPort?.get(0)
        val ipRes = ip.let{ip} ?: "192.168.0.61"
        val port: Int? = ipPort?.get(1)?.toInt()
        val portRes = port.let { port } ?: 64499

        socket = Socket(ipRes, portRes)
        outStream = DataOutputStream(socket?.getOutputStream())
        outStream?.write(message?.toByteArray())

        Log.e("SENT", message)

        outStream?.close()
        socket?.close()

        return null
    }
}