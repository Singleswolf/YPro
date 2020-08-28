package com.zy.commonlib.http

import com.blankj.utilcode.util.Utils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zy.http.interceptor.HttpLoggingInterceptor
import com.zy.http.interceptor.TimeoutInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Description: Created by yong on 2019/3/26 10:02.
 */
object RetrofitFactory {
    private const val BASE_URL = "https://www.wanandroid.com"
    private const val TIME_OUT = 10
    private val cookiePersistor = SharedPrefsCookiePersistor(Utils.getApp())
    private val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersistor)
    private val sslParams = HttpsUtils.sslSocketFactory//https

    private val client = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        .sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
        .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .cookieJar(cookieJar)
        .addInterceptor(TimeoutInterceptor())
        .build()

    fun <T> create(clazz: Class<T>): T {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clazz)
    }

    fun clearCookie() = cookieJar.clear()

    fun hasCookie() = cookiePersistor.loadAll().isNotEmpty()
}
