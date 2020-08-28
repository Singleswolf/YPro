package com.zy.ypro.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * Created by yong on 2020/8/27 18:26.
 */
object LiveBus {
    /**
     * 发布消息
     * @param tag 发送消息tag
     * @param value 发送数据
     */
    inline fun <reified T> post(tag: String, value: T) {
        LiveEventBus.get(tag, T::class.java).post(value)
    }

    /**
     * 订阅消息
     */
    inline fun <reified T> observe(owner: LifecycleOwner, tag: String, observer: Observer<T>) {
        LiveEventBus.get(tag, T::class.java).observe(owner, observer)
    }

    /**
     * 订阅粘性消息
     */
    inline fun <reified T> observeSticky(owner: LifecycleOwner, tag: String, observer: Observer<T>) {
        LiveEventBus.get(tag, T::class.java).observeSticky(owner, observer)
    }

    /**
     * 订阅进程消息
     */
    inline fun <reified T> observeForever(tag: String, observer: Observer<T>) {
        LiveEventBus.get(tag, T::class.java).observeForever(observer)
    }

    /**
     * 订阅进程粘性消息
     */
    inline fun <reified T> observeStickyForever(tag: String, observer: Observer<T>) {
        LiveEventBus.get(tag, T::class.java).observeStickyForever(observer)
    }
}