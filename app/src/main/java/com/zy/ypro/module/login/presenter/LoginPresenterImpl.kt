package com.zy.ypro.module.login.presenter

import androidx.annotation.NonNull
import com.zy.ypro.base.BasePresenter
import com.zy.ypro.http.HttpResponseListener
import com.zy.ypro.model.entity.LoginBean
import com.zy.ypro.module.login.contract.LoginContract
import com.zy.ypro.module.login.model.LoginModelImpl

/**
 * @Description:
 * Created by yong on 2019/12/5 11:32.
 */
class LoginPresenterImpl : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter,
    HttpResponseListener<LoginBean> {
    override fun registUser(user: String, passwd: String, repasswd: String) {
        loginModel.requestRegistUser(user, passwd, repasswd, this)
    }

    private val loginModel: LoginModelImpl by lazy { LoginModelImpl() }

    override fun login(@NonNull user: String, @NonNull passwd: String) {
        loginModel.requestLogin(user, passwd, this)
    }

    override fun onSuccess(tag: Any, t: LoginBean) {
        getView().onSuccess(t)
    }

    override fun onFailed(tag: Any, error: String) {
        getView().onFailed(error)
    }
}