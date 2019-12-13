package com.zy.ypro.http

/**
 * @Description: Created by yong on 2019/3/22 11:38.
 */
interface HttpResponseListener<T> {

    fun onSuccess(tag: Any, t: T)

    fun onFailed(tag: Any, error: String)
}
