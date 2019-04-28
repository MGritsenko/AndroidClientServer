package ru.serverclient

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private var fragmentHolder: FragmentHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        fragmentHolder = (supportFragmentManager.findFragmentByTag("FRAGMENTHOLDER") as FragmentHolder?)
        if (fragmentHolder == null) {
            fragmentHolder = FragmentHolder()
            supportFragmentManager.beginTransaction().add(R.id.fragment_holder_main, fragmentHolder!!, "FRAGMENTHOLDER").commit()
            supportFragmentManager.executePendingTransactions()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        fragmentHolder = null
    }
}