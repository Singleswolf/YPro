package com.zy.ypro.model.api


import com.zy.commonlib.http.ApiResponse
import com.zy.ypro.model.entity.UserInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @Description: Created by yong on 2019/3/26 20:24.
 */
interface ApiService {
    companion object {
        const val TAG_API_LOGIN = "TAG_API_LOGIN"
        const val TAG_API_REGISTER = "TAG_API_REGISTER"
        const val TAG_API_LOGOUT = "TAG_API_LOGOUT"
    }

    /**
     * 登录
     * 方法： POST
     * 参数：username，password
     * 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证。
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): ApiResponse<UserInfo>

    /**
     * 注册
     * 方法： POST
     * 参数：username，password, repassword
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResponse<UserInfo>

    /**
     * 退出
     * 方法： GET
     * 访问了 logout 后，服务端会让客户端清除 Cookie（即cookie max-Age=0），
     * 如果客户端 Cookie 实现合理，可以实现自动清理，如果本地做了用户账号密码和保存，及时清理。
     */
    @GET("user/logout/json")
    suspend fun logout(): ApiResponse<Any>
}
