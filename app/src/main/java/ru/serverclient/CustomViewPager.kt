package ru.serverclient

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomViewPager : ViewPager {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    private var disable = false

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (disable) false else super.onInterceptTouchEvent(event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (disable) false else super.onTouchEvent(event)
    }

    fun disableScroll(disable: Boolean) {
        this.disable = disable
    }
}