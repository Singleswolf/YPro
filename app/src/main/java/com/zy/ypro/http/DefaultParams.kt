package com.zy.ypro.http

import java.util.*

/**
 * @Description: add common params
 * Created by yong on 2019/3/26 15:15.
 */
class DefaultParams : RetrofitConfig.Params {
    override val headers: Map<String, String>
        get() = HashMap()

    override val urlParams: MutableMap<String, String>
        get() = HashMap()

    override val bodyParams: MutableMap<String, String>
        get() = HashMap()
}
