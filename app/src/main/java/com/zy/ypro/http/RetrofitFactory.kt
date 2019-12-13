package com.zy.ypro.http

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Description: Created by yong on 2019/3/26 10:02.
 */
object RetrofitFactory {

    fun newBuilder(config: RetrofitConfig): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(config.buildBaseUrl())
            .client(config.buildClient())
            .addConverterFactory(GsonConverterFactory.create(config.buildGson()))
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory
                    .createWithScheduler(config.executeScheduler)
            )
    }
}
