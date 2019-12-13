package com.zy.ypro

import android.app.Application
import android.content.Context

/**
 * @Description:
 * Created by yong on 2019/12/5 15:27.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        private var app: Application? = null
        fun getContext(): Context {
            return app!!
        }
    }
}
