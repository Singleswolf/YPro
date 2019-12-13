package com.zy.ypro.http.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object RxSchedulers {
    /**
     * UI线程.
     */
    val MAIN = AndroidSchedulers.mainThread()

    /**
     * 用于执行网络请求.
     */
    val NETWORKING = Schedulers.from(
        newFixedThreadPoolExecutor(4)
    )

    /**
     * 用于执行异步任务, 例如读写缓存, 读写pref, 额外的API请求等.
     */
    val ASYNC = Schedulers.from(
        ThreadPoolExecutor(
            0, Integer.MAX_VALUE, 60L,
            TimeUnit.SECONDS, SynchronousQueue()
        )
    )

    fun newFixedThreadPoolExecutor(size: Int): ThreadPoolExecutor {
        val executor = ThreadPoolExecutor(
            size, size, 1, TimeUnit.MINUTES,
            LinkedBlockingQueue()
        )
        executor.allowCoreThreadTimeOut(true)
        return executor
    }

    fun <T> compose(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { }
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}