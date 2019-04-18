package ru.serverclient

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentCollectionAdapter(private val fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(p0: Int): Fragment? {
        when(p0){
            0 -> return InitConnectionFragment()
            1 -> return ReceiveVideoFragment()
        }

        return null
    }
}