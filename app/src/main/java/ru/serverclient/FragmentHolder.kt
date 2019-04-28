package ru.serverclient

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.app.AppCompatActivity



class FragmentHolder : Fragment() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var mViewPager: CustomViewPager? = null
    var initConnectionFragment: InitConnectionFragment? = null
    var receiveVideoFragment: ReceiveVideoFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_holder, container, false)

        mViewPager = rootView?.findViewById(R.id.containerConnectView)
        initConnectionFragment = activity?.supportFragmentManager?.findFragmentByTag("android:switcher:" + R.id.containerConnectView + ":" + 0) as InitConnectionFragment?
        receiveVideoFragment = activity?.supportFragmentManager?.findFragmentByTag("android:switcher:" + R.id.containerConnectView + ":" + 1) as ReceiveVideoFragment?
        if(initConnectionFragment == null || receiveVideoFragment == null) {
            initConnectionFragment = InitConnectionFragment()
            receiveVideoFragment = ReceiveVideoFragment()
            mSectionsPagerAdapter = SectionsPagerAdapter(activity!!.supportFragmentManager)
        }

        mViewPager?.adapter = mSectionsPagerAdapter

        mViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                if(position == 1){
                    (activity as AppCompatActivity).supportActionBar!!.hide()
                } else {
                    (activity as AppCompatActivity).supportActionBar!!.show()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })

        mViewPager?.post {
            val item = mViewPager?.currentItem
            mViewPager?.currentItem = if(item == 0) 1 else 0
            mViewPager?.currentItem = item ?: 0
        }

        retainInstance = true

        startServer()

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()

        mSectionsPagerAdapter = null
        mViewPager = null

        initConnectionFragment = null
        receiveVideoFragment = null
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {

            when (position){
                0 -> {
                    return initConnectionFragment
                }

                1 -> {
                    return receiveVideoFragment
                }
            }

            return null
        }

        override fun getCount(): Int {
            return 2
        }
    }

    fun update(text: String, img: Bitmap){
        initConnectionFragment?.update(text, img)
        receiveVideoFragment?.update(text, img)
    }

    private fun startServer(){
        val handler = MyHandler(this)
        val thread = Thread(MyServer(handler))
        thread.start()
    }
}