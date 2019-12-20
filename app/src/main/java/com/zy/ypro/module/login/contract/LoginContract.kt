package com.zy.ypro.module.login.contract

import com.zy.ypro.base.IPresenter
import com.zy.ypro.base.IView
import com.zy.ypro.http.HttpResponseListener
import com.zy.ypro.model.entity.LoginBean

/**
 * @Description:
 * Created by yong on 2019/12/5 11:28.
 */
interface LoginContract {
    interface LoginView : IView {
        fun onSuccess(loginBean: LoginBean)
        fun onFailed(msg: String)
    }

    interface LoginPresenter : IPresenter {
        fun login(user: String, passwd: String)
        fun registUser(user: String, passwd: String, repasswd: String)
    }

    interface LoginModel {
        fun requestLogin(user: String, pwd: String, callback: HttpResponseListener<LoginBean>)
        fun requestRegistUser(user: String, pwd: String, repwd: String, callback: HttpResponseListener<LoginBean>)
    }
}