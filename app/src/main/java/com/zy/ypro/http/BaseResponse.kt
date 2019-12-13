package com.zy.ypro.http

/**
 * @Description: Created by yong on 2019/3/26 19:31.
 */
data class BaseResponse<T>(val code: Int, val message: String, val result: T, val data: T) {

    val isSuccess: Boolean
        get() = code == 200
}
