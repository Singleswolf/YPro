package com.zy.commonlib.http


/**
 * @Description: Created by yong on 2019/3/26 19:31.
 */
data class ApiResponse<T>(val errorCode: Int, val errorMsg: String, val data: T) {
    fun apiData(): T {
        if (errorCode == 0) {
            return data
        } else {
            throw ApiException(errorCode, errorMsg)
        }
    }
}
