package com.zy.ypro.module.login.model

import com.zy.commonlib.http.RetrofitFactory
import com.zy.ypro.model.api.ApiService

/**
 * Created by yong on 2020/8/27 11:07.
 */

/**
 * 纯粹的M层, 只有数据获取的代码
 */
class LoginModel {
    suspend fun login(user: String, pwd: String) = RetrofitFactory.create(ApiService::class.java).login(user, pwd).apiData()

    suspend fun registerUser(user: String, pwd: String, rePwd: String) =
        RetrofitFactory.create(ApiService::class.java).registerUser(user, pwd, rePwd).apiData()
}