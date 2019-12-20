package com.zy.ypro.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Description: Created by yong on 2019/3/26 10:02.
 */
object RetrofitFactory {

    fun <T> create(clazz: Class<T>): T {
        val config = DefaultRetrofitConfig()
        return Retrofit.Builder().baseUrl(config.buildBaseUrl())
            .client(config.buildClient())
            .addConverterFactory(GsonConverterFactory.create(config.buildGson()))
            .build()
            .create(clazz)
    }
}
