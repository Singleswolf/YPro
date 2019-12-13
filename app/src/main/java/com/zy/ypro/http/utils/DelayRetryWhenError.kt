package com.zy.ypro.http.utils


import com.zy.ypro.utils.Logger
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * @Description: Created by yong on 2019/3/28 14:56.
 */
class DelayRetryWhenError : Function<Observable<out Throwable>, Observable<*>> {

    private var mMaxRetryCount = 3//retry次数
    private var mDelay: Long = 1000//延迟
    private var mIncreaseDelay: Long = 1000//叠加延迟

    constructor() {}

    constructor(maxRetryCount: Int) {
        mMaxRetryCount = maxRetryCount
    }

    constructor(maxRetryCount: Int, delay: Long) {
        mMaxRetryCount = maxRetryCount
        mDelay = delay
    }

    constructor(maxRetryCount: Int, delay: Long, increaseDelay: Long) {
        mMaxRetryCount = maxRetryCount
        mDelay = delay
        mIncreaseDelay = increaseDelay
    }

    @Throws(Exception::class)
    override fun apply(observable: Observable<out Throwable>): Observable<*> {
        return observable
            .zipWith(
                Observable.range(1, mMaxRetryCount + 1),
                object : BiFunction<Throwable, Int, ThrowableWrapper> {
                    override fun apply(throwable: Throwable, curRetryCount: Int): ThrowableWrapper {
                        return ThrowableWrapper(throwable, curRetryCount)
                    }
                }).flatMap(Function<ThrowableWrapper, Observable<*>> { wrapper ->
                //遭遇了IOException等时才重试网络请求，IllegalStateException，NullPointerException或者当你使用gson来解析json时还可能出现的JsonParseException等非I/O异常均不在重试的范围内。
                if (wrapper.throwable is ConnectException
                    || wrapper.throwable is SocketTimeoutException
                    || wrapper.throwable is TimeoutException
                    || wrapper.throwable is HttpException
                ) {
                    //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                    if (wrapper.curRetryCount <= mMaxRetryCount) {
                        Logger.e("DelayRetryWhenError", "网络错误，重试次数:" + wrapper.curRetryCount)
                        val delayTime =
                            mDelay + (wrapper.curRetryCount - 1) * mIncreaseDelay    //使用二进制指数退避算法，每次都比上次长时间
                        return@Function Observable.timer(
                            delayTime,
                            TimeUnit.MILLISECONDS,
                            Schedulers.trampoline()
                        )
                    }
                }
                Observable.error<Throwable>(wrapper.throwable)
            })
    }

    private inner class ThrowableWrapper(
        val throwable: Throwable//抛出的异常
        , val curRetryCount: Int//当前重试次数
    )
}
