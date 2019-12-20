package com.zy.ypro.http.utils

import org.jetbrains.annotations.NotNull
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * @Description:
 * Created by yong on 2020/1/17 16:12.
 */
object HttpThrowables {
    fun getMessage(@NotNull ex: Exception): String {
        if (ex is SocketTimeoutException) {//请求超时
            return "网络连接超时"
        } else if (ex is ConnectException) {//网络连接超时
            return "网络连接超时"
        } else if (ex is SSLHandshakeException) {//安全证书异常
            return "安全证书异常"
        } else if (ex is HttpException) {//请求的地址不存在
            val code = ex.code()
            if (code == 504) {
                return "网络异常，请检查您的网络状态"
            } else if (code == 404) {
                return "请求的地址不存在"
            } else {
                return "请求失败"
            }
        } else if (ex is UnknownHostException) {//域名解析失败
            return "域名解析失败"
        } else {
            return "error:" + ex.message
        }
    }
}