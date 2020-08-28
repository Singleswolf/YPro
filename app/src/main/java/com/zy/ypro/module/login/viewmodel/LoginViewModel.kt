package com.zy.ypro.module.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zy.commonlib.base.BaseViewModel
import com.zy.ypro.event.LiveBus
import com.zy.ypro.event.EVENT_LOGIN_SUCCESS_STICKY
import com.zy.ypro.model.store.UserInfoStore
import com.zy.ypro.module.login.model.LoginModel

class LoginViewModel : BaseViewModel() {
    private val loginModel: LoginModel by lazy {
        LoginModel()
    }
    val loading = MutableLiveData<Boolean>()
    val result = MutableLiveData<Boolean>()

    fun doLogin(user: String, pwd: String) {
        loading.postValue(true)
        launch(
            block = {
                val response = loginModel.login(user, pwd)
                UserInfoStore.updateUserInfo(response)
                LiveBus.post(EVENT_LOGIN_SUCCESS_STICKY, response)
                loading.postValue(false)
                result.postValue(true)
            },
            error = {
                loading.postValue(false)
                result.postValue(false)
            })
    }

    fun doRegister(user: String, pwd: String, rePwd: String) {
        launch(
            block = {
                val response = loginModel.registerUser(user, pwd, rePwd)
                UserInfoStore.updateUserInfo(response)
                LiveBus.post(EVENT_LOGIN_SUCCESS_STICKY, response)
                result.postValue(true)
                loading.postValue(false)
            },
            error = {
                loading.postValue(false)
                result.postValue(false)
            })
    }

}