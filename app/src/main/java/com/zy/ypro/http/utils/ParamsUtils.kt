package com.zy.ypro.http.utils

import android.util.Pair
import com.zy.ypro.http.RetrofitConfig

object ParamsUtils {

    fun obtainParams(
        config: RetrofitConfig.Params, params: Map<String, String>?,
        get: Boolean
    ): Pair<MutableMap<String, String>, MutableMap<String, String>> {
        val urlParams = config.urlParams
        val bodyParams = config.bodyParams

        if (params != null) {
            if (get) {
                urlParams.putAll(params)
            } else {
                bodyParams.putAll(params)
            }
        }
        escapeParams(urlParams, bodyParams)
        // 如果是Get请求, 则把所有post参数全部放到urlParams里去
        if (get) {
            urlParams.putAll(bodyParams)
            bodyParams.clear()
        }

        return Pair(urlParams, bodyParams)
    }

    // 对一些参数做修正, 目前仅处理了空参数
    private fun escapeParams(
        urlParams: MutableMap<String, String>?,
        bodyParams: MutableMap<String, String>?
    ) {
        if (urlParams != null) {
            for (key in urlParams.keys) {
                var value: String? = urlParams[key]
                if (value == null) {
                    value = ""
                    urlParams[key] = value
                }
            }
        }

        if (bodyParams != null) {
            for (key in bodyParams.keys) {
                var value: String? = bodyParams[key]
                if (value == null) {
                    value = ""
                    bodyParams[key] = value
                }
            }
        }
    }
}
