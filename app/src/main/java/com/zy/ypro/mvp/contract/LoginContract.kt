package com.zy.ypro.mvp.contract

import com.zy.ypro.http.HttpResponseListener
import com.zy.ypro.model.entity.UserInfo
import com.zy.ypro.mvp.IPresenter
import com.zy.ypro.mvp.IView

/**
 * @Description:
 * Created by yong on 2019/12/5 11:28.
 */
interface LoginContract {
    interface LoginView : IView {
        fun onSuccess(userInfo: UserInfo)
        fun onFailed(msg: String)
    }

    interface LoginPresenter : IPresenter {
        fun login(user: String, passwd: String)
    }

    interface LoginModel {
        fun requestLogin(user: String, pwd: String, callback: HttpResponseListener<UserInfo>)
    }
}