package com.zy.ypro.http

import com.zy.ypro.model.api.ApiService

/**
 * @Description: Created by yong on 2019/3/26 19:31.
 */
data class BaseResponse<T>(val errorCode: Int, val errorMsg: String, val result: T, val data: T) {

    val isSuccess: Boolean
        get() = errorCode == ApiService.Code.SUCCESS
}
