package ru.serverclient

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import java.io.InputStream
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

class MyServer(handler: MyHandler) : Runnable
{
    private val SERVERPORT = 64499

    private var serverSocket: ServerSocket? = null
    private var socket: Socket? = null
    private var inStream: InputStream? = null
    private var handler: MyHandler? = handler
    private var message: String? = null

    override fun run(){
        serverSocket = ServerSocket(SERVERPORT)
        Log.e("STARTED", "STARTED")

        while (true){
            Log.e("ACCEPT", "ACCEPT")
            socket = serverSocket?.accept()
            Log.e("ACCEPTED", "ACCEPTED")

            inStream = socket?.getInputStream()
            val byteArray = inStream?.readBytes()
            if(byteArray == null) {
                inStream?.close()
                continue
            }
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
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