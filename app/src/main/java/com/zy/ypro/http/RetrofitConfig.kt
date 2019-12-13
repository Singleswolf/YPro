package com.zy.ypro.http

import com.google.gson.Gson

import io.reactivex.Scheduler
import okhttp3.OkHttpClient

interface RetrofitConfig {

    val executeScheduler: Scheduler

    fun buildGson(): Gson

    fun buildParams(): Params

    fun buildBaseUrl(): String

    fun buildClient(): OkHttpClient

    interface Params {

        val headers: Map<String, String>

        val urlParams: MutableMap<String, String>

        val bodyParams: MutableMap<String, String>
    }
}
