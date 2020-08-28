package com.zy.ypro

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.blankj.utilcode.util.Utils
import com.zy.ypro.utils.ActivityHelper

/**
 * @Description:
 * Created by yong on 2019/12/5 15:27.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        ActivityHelper.init(this)
    }

    companion object {
        val prefs: SharedPreferences by lazy {
            Utils.getApp().getSharedPreferences("SP_NAME", Context.MODE_PRIVATE)
        }
    }
}
