package com.zy.ypro.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import com.zy.ypro.App
import com.zy.ypro.http.interceptor.ConfigParamsInterceptor
import com.zy.ypro.http.interceptor.HttpLoggingInterceptor
import com.zy.ypro.http.interceptor.TimeoutInterceptor
import com.zy.ypro.http.utils.Gsons
import com.zy.ypro.http.utils.HttpsUtils
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @Description: Created by yong on 2019/3/26 14:56.
 */
class DefaultRetrofitConfig(override val executeScheduler: Scheduler) : RetrofitConfig {
    private var mOkHttpClient: OkHttpClient? = null
    private var mUploadOkHttpClient: OkHttpClient? = null
    private val isUpload = false
    private val logging = true
    private val isHttps = true
    private val isCookie = true

    override fun buildGson(): Gson {
        return Gsons.GSON
    }

    override fun buildParams(): RetrofitConfig.Params {
        return DefaultParams()
    }

    override fun buildBaseUrl(): String {
        return "https://api.apiopen.top/"
    }

    override fun buildClient(): OkHttpClient {
        if (isUpload) {
            if (mUploadOkHttpClient == null) {
                mUploadOkHttpClient = getHttpClient(DEFAULT_UPLOAD_TIME_OUT_SECOND)
            }
            return mUploadOkHttpClient!!
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = getHttpClient(DEFAULT_TIME_OUT_SECOND)
        }
        return mOkHttpClient!!
    }


    private fun getHttpClient(timeOut: Int): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)

        if (logging) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }

        if (isHttps) {
            val sslParams = HttpsUtils.sslSocketFactory//https
            builder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
            builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
        }
        if (isCookie) {
            val cookieJar =
                PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.getContext()))
            builder.cookieJar(cookieJar)
        }
        builder.addInterceptor(TimeoutInterceptor())
        builder.addInterceptor(ConfigParamsInterceptor(buildParams()))
        return builder.build()
    }

    companion object {
        private val DEFAULT_TIME_OUT_SECOND = 15
        private val DEFAULT_UPLOAD_TIME_OUT_SECOND = 60
    }

}
