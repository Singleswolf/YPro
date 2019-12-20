package com.zy.ypro.http.interceptor

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 用于处理读取单独 api 的超时时间的配置
 */
class TimeoutInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        val builder = request.headers().newBuilder()
        val readTimeout = request.header(READ_TIMEOUT_NAME)
        if (!TextUtils.isEmpty(readTimeout)) {
            try {
                chain.withReadTimeout(Integer.valueOf(readTimeout!!.trim { it <= ' ' }), TimeUnit.MILLISECONDS)
            } catch (ignored: Exception) {
            }

            builder.removeAll(LONG_READ_TIMEOUT)
        }

        val writeTimeout = request.header(WRITE_TIMEOUT_NAME)
        if (!TextUtils.isEmpty(writeTimeout)) {
            try {
                chain.withWriteTimeout(Integer.valueOf(writeTimeout!!.trim { it <= ' ' }), TimeUnit.MILLISECONDS)
            } catch (ignored: Exception) {
            }

            builder.removeAll(LONG_WRITE_TIMEOUT)
        }

        val connectionTimeout = request.header(CONNECTION_TIMEOUT_NAME)
        if (!TextUtils.isEmpty(connectionTimeout)) {
            try {
                chain.withConnectTimeout(Integer.valueOf(connectionTimeout!!.trim { it <= ' ' }), TimeUnit.MILLISECONDS)
            } catch (ignored: Exception) {
            }

            builder.removeAll(LONG_CONNECTION_TIMEOUT)
        }

        request = request.newBuilder().headers(builder.build()).build()
        return chain.proceed(request)
    }

    companion object {

        val READ_TIMEOUT_NAME = "readTimeout"
        val WRITE_TIMEOUT_NAME = "writeTimeout"
        val CONNECTION_TIMEOUT_NAME = "connectionTimeout"
        val LONG_TIMEOUT = 30000

        val LONG_READ_TIMEOUT = "$READ_TIMEOUT_NAME:$LONG_TIMEOUT"
        val LONG_WRITE_TIMEOUT = "$WRITE_TIMEOUT_NAME:$LONG_TIMEOUT"
        val LONG_CONNECTION_TIMEOUT = "$CONNECTION_TIMEOUT_NAME:$LONG_TIMEOUT"
    }
}
