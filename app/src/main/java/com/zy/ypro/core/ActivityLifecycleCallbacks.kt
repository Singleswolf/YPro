package com.zy.ypro.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.text.TextWatcher

/**
 * Created by yong on 2020/8/28 15:39.
 */
/**
 * Add an action which will be invoked before the text changed.
 *
 * @return the [TextWatcher] added to the TextView
 */
inline fun Application.onActivityCreated(
    crossinline action: (
        activity: Activity,
        savedInstanceState: Bundle?
    ) -> Unit
) = registerActivityLifecycleCallbacks(onActivityCreated = action)

inline fun Application.onActivityStarted(
    crossinline action: (
        activity: Activity,
    ) -> Unit
) = registerActivityLifecycleCallbacks(onActivityStarted = action)

inline fun Application.onActivityResumed(
    crossinline action: (
        activity: Activity,
    ) -> Unit
) = registerActivityLifecycleCallbacks(onActivityResumed = action)

inline fun Application.onActivitySaveInstanceState(
    crossinline action: (
        activity: Activity,
        savedInstanceState: Bundle?
    ) -> Unit
) = registerActivityLifecycleCallbacks(onActivitySaveInstanceState = action)

inline fun Application.onActivityPaused(
    crossinline action: (
        activity: Activity,
    ) -> Unit
) = registerActivityLifecycleCallbacks(onActivityPaused = action)

inline fun Application.onActivityStopped(
    crossinline action: (
        activity: Activity,
    ) -> Unit
) = registerActivityLifecycleCallbacks(onActivityStopped = action)

inline fun Application.onActivityDestroyed(
    crossinline action: (
        activity: Activity,
    ) -> Unit
) = registerActivityLifecycleCallbacks(onActivityDestroyed = action)

inline fun Application.registerActivityLifecycleCallbacks(
    crossinline onActivityCreated: (
        activity: Activity,
        savedInstanceState: Bundle?
    ) -> Unit = { _, _ -> },
    crossinline onActivityStarted: (
        activity: Activity,
    ) -> Unit = { _ -> },
    crossinline onActivityResumed: (
        activity: Activity,
    ) -> Unit = { _ -> },
    crossinline onActivitySaveInstanceState: (
        activity: Activity,
        savedInstanceState: Bundle?
    ) -> Unit = { _, _ -> },
    crossinline onActivityPaused: (
        activity: Activity,
    ) -> Unit = { _ -> },
    crossinline onActivityStopped: (
        activity: Activity,
    ) -> Unit = { _ -> },
    crossinline onActivityDestroyed: (
        activity: Activity,
    ) -> Unit = { _ -> },
): Application.ActivityLifecycleCallbacks {
    val callbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            onActivityCreated.invoke(activity, savedInstanceState)
        }

        override fun onActivityStarted(activity: Activity) {
            onActivityStarted.invoke(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            onActivityResumed.invoke(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            onActivitySaveInstanceState.invoke(activity, outState)
        }

        override fun onActivityPaused(activity: Activity) {
            onActivityPaused.invoke(activity)
        }

        override fun onActivityStopped(activity: Activity) {
            onActivityStopped.invoke(activity)
        }

        override fun onActivityDestroyed(activity: Activity) {
            onActivityDestroyed.invoke(activity)
        }
    }
    registerActivityLifecycleCallbacks(callbacks)
    return callbacks
}
