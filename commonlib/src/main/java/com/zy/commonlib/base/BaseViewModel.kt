package com.zy.commonlib.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParseException
import com.zy.commonlib.http.ApiException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import javax.net.ssl.SSLHandshakeException

/**
 * Created by yong on 2020/8/27 15:41.
 */
typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

open class BaseViewModel : ViewModel() {
    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @param cancel 取消时只需
     * @return Job
     */
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     */
    private fun onError(e: Exception) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效，清除用户信息、清除cookie/token
//                        UserInfoStore.clearUserInfo()
//                        RetrofitClient.clearCookie()
                    }
                    // 其他api错误
                    -1 -> ToastUtils.showShort(e.message)
                    // 其他错误
                    else -> ToastUtils.showShort(e.message)
                }
            }
            // 网络请求失败
            is ConnectException, is SocketTimeoutException -> ToastUtils.showShort("网络连接超时")
            is UnknownHostException -> ToastUtils.showShort("域名解析失败")
            is SSLHandshakeException -> ToastUtils.showShort("安全证书异常")
            is HttpException -> ToastUtils.showShort("网络请求失败")
            // 数据解析错误
            is JsonParseException -> ToastUtils.showShort("数据解析错误")
            // 其他错误
            else -> ToastUtils.showShort(e.message ?: return)
        }
    }
}