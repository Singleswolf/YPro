package com.zy.ypro.model.api


import com.zy.ypro.http.BaseResponse
import com.zy.ypro.model.entity.UserInfo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Description: Created by yong on 2019/3/26 20:24.
 */
interface LoginService {
    //https://api.apiopen.top/loginUser?apikey=f08493bf125244d4f6924d1ec1b6ba4a&name=yong&passwd=123456
    @GET("loginUser")
    fun login(@Query("name") account: String, @Query("passwd") passwd: String): Observable<BaseResponse<UserInfo>>
}
