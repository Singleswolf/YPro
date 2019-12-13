package com.zy.ypro.imageloader

import android.graphics.Bitmap

/**
 * Created by yong on 2019/3/6.
 */

interface BitmapCallBack {

    fun onBitmapLoaded(bitmap: Bitmap)

    fun onBitmapFailed(e: Exception)

    class EmptyCallback : BitmapCallBack {


        override fun onBitmapLoaded(bitmap: Bitmap) {

        }

        override fun onBitmapFailed(e: Exception) {

        }
    }
}
