package com.zy.ypro.http

import com.zy.ypro.utils.Logger
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * @Description: Created by yong on 2019/3/27 20:45.
 */
abstract class RxCallback<T> : DisposableObserver<BaseResponse<T>>() {

    override fun onNext(tBaseResponse: BaseResponse<T>) {
        if (tBaseResponse.isSuccess) {
            if (tBaseResponse.result != null) {
                onSuccess(tBaseResponse.result)
            } else if (tBaseResponse.data != null) {
                onSuccess(tBaseResponse.data)
            }
        } else {
            onFailed(tBaseResponse.errorCode, tBaseResponse.errorMsg)
        }
    }

    protected abstract fun onSuccess(data: T)

    protected abstract fun onFailed(code: Int, msg: String)

    override fun onError(e: Throwable) {
        try {
            if (e is SocketTimeoutException) {//请求超时
            } else if (e is ConnectException) {//网络连接超时
                onFailed(-1, "网络连接超时")
            } else if (e is SSLHandshakeException) {//安全证书异常
                onFailed(-1, "安全证书异常")
            } else if (e is HttpException) {//请求的地址不存在
                val code = e.code()
                if (code == 504) {
                    onFailed(-1, "网络异常，请检查您的网络状态")
                } else if (code == 404) {
                    onFailed(-1, "请求的地址不存在")
                } else {
                    onFailed(-1, "请求失败")
                }
            } else if (e is UnknownHostException) {//域名解析失败
                onFailed(-1, "域名解析失败")
            } else {
                onFailed(-1, "error:" + e.message)
            }
        } catch (e2: Exception) {
            e2.printStackTrace()
        } finally {
            Logger.e("OnSuccessAndFaultSub", "error:", e)
            onFailed(-1, "finally error:" + e.message)
        }
    }

    override fun onComplete() {}
}
