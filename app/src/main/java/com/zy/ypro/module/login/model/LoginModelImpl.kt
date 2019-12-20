package com.zy.ypro.module.login.model

import com.zy.ypro.http.HttpResponseListener
import com.zy.ypro.http.RetrofitFactory
import com.zy.ypro.http.utils.HttpThrowables
import com.zy.ypro.model.api.ApiService
import com.zy.ypro.model.entity.LoginBean
import com.zy.ypro.module.login.contract.LoginContract
import com.zy.ypro.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @Description: Created by yong on 2019/3/22 11:55.
 */
class LoginModelImpl : LoginContract.LoginModel {
    override fun requestLogin(user: String, pwd: String, callback: HttpResponseListener<LoginBean>) {
        val apiService = RetrofitFactory.create(ApiService::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.login(user, pwd)
                Logger.v(response.toString())
                if (response.isSuccess) {
                    callback.onSuccess(ApiService.TAG_API_LOGIN, response.data)
                } else {
                    callback.onFailed(ApiService.TAG_API_LOGIN, response.errorMsg)
                }
            } catch (ex: Exception) {
                callback.onFailed(ApiService.TAG_API_LOGIN, HttpThrowables.getMessage(ex))
            }
        }
    }

    override fun requestRegistUser(user: String, pwd: String, repwd: String, callback: HttpResponseListener<LoginBean>) {
        val apiService = RetrofitFactory.create(ApiService::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.registerUser(user, pwd, repwd)
                Logger.v(response.toString())
                if (response.isSuccess) {
                    callback.onSuccess(ApiService.TAG_API_REGISTER, response.data)
                } else {
                    callback.onFailed(ApiService.TAG_API_REGISTER, response.errorMsg)
                }
            } catch (ex: Exception) {
                callback.onFailed(ApiService.TAG_API_REGISTER, HttpThrowables.getMessage(ex))
            }
        }
    }
}
