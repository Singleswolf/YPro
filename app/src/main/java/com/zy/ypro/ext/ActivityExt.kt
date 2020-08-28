package com.zy.ypro.ext

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.view.View
import android.view.animation.OvershootInterpolator

/**
 * Created by yong on 2020/8/28 16:23.
 */
fun Activity.flipAnimatorYViewShow(rootView: View, flag: Boolean, time: Long, call: (Boolean) -> Unit) {
    lateinit var animator2: Animator
    lateinit var animator1: Animator
    if (flag) {
        animator1 = ObjectAnimator.ofFloat(rootView, "rotationY", 0f, -90f)
        animator2 = ObjectAnimator.ofFloat(rootView, "rotationY", 90f, 0f)
    } else {
        animator1 = ObjectAnimator.ofFloat(rootView, "rotationY", 0f, 90f)
        animator2 = ObjectAnimator.ofFloat(rootView, "rotationY", -90f, 0f)
    }

    animator2.interpolator = OvershootInterpolator(2.0f)
    animator1.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            animator2.setDuration(time).start()
            call(flag)
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    animator1.setDuration(time).start()
}