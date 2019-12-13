package com.zy.ypro.mvp.presenter

import androidx.annotation.NonNull
import com.zy.ypro.http.HttpResponseListener
import com.zy.ypro.model.entity.UserInfo
import com.zy.ypro.mvp.BasePresenter
import com.zy.ypro.mvp.contract.LoginContract
import com.zy.ypro.mvp.model.LoginModelImpl

/**
 * @Description:
 * Created by yong on 2019/12/5 11:32.
 */
class LoginPresenterImpl : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter,
        HttpResponseListener<UserInfo> {

    private var loginModel: LoginModelImpl = LoginModelImpl()

    override fun login(@NonNull user: String, @NonNull passwd: String) {
        loginModel.requestLogin(user, passwd, this)
    }

    override fun onSuccess(tag: Any, t: UserInfo) {
        getView().onSuccess(t)
    }

    override fun onFailed(tag: Any, error: String) {
        getView().onFailed(error)
    }
}