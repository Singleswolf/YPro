package com.zy.ypro.utils

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.zy.ypro.core.onActivityCreated
import com.zy.ypro.core.onActivityDestroyed
import com.zy.ypro.ext.putExtras

object ActivityHelper {
    private val activities = mutableListOf<Activity>()

    fun init(application: Application) {
        application.onActivityCreated { activity, _ ->
            activities.add(activity)
        }
        application.onActivityDestroyed { activity ->
            activities.remove(activity)
        }
    }

    fun start(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    /**
     * finish指定的一个或多个Activity
     */
    fun finish(vararg clazz: Class<out Activity>) {
        activities.forEach { activiy ->
            if (clazz.contains(activiy::class.java)) {
                activiy.finish()
            }
        }
    }

}