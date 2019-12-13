package com.zy.ypro.mvp.model

import android.annotation.SuppressLint
import com.zy.ypro.http.DefaultRetrofitConfig
import com.zy.ypro.http.HttpResponseListener
import com.zy.ypro.http.RetrofitFactory
import com.zy.ypro.http.RxCallback
import com.zy.ypro.http.utils.DelayRetryWhenError
import com.zy.ypro.http.utils.RxSchedulers
import com.zy.ypro.model.api.LoginService
import com.zy.ypro.model.entity.UserInfo
import com.zy.ypro.mvp.contract.LoginContract
import com.zy.ypro.utils.Logger

/**
 * @Description: Created by yong on 2019/3/22 11:55.
 */
class LoginModelImpl : LoginContract.LoginModel {
    @SuppressLint("CheckResult")
    override fun requestLogin(user: String, pwd: String, callback: HttpResponseListener<UserInfo>) {
        val service =
            RetrofitFactory.newBuilder(DefaultRetrofitConfig(RxSchedulers.NETWORKING)).build()
                .create(LoginService::class.java)
        val observable = service.login(user, pwd)
        observable.compose(RxSchedulers.compose())
            .retryWhen(DelayRetryWhenError())
            .subscribe(object : RxCallback<UserInfo>() {
                override fun onFailed(code: Int, msg: String) {
                    Logger.e("LoginModelImpl", "code $code, msg $msg")
                    callback.onFailed("", msg)
                }

                override fun onSuccess(data: UserInfo) {
                    Logger.i("LoginModelImpl", "name = %s", data.name)
                    callback.onSuccess("", data)
                }
            })
    }
}
