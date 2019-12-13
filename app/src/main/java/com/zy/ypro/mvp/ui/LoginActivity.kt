package com.zy.ypro.mvp.ui

import com.zy.ypro.R
import com.zy.ypro.model.entity.UserInfo
import com.zy.ypro.mvp.BaseVActivity
import com.zy.ypro.mvp.contract.LoginContract
import com.zy.ypro.mvp.presenter.LoginPresenterImpl
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @Description:
 * Created by yong on 2019/12/5 11:41.
 */
class LoginActivity : BaseVActivity<LoginPresenterImpl>(),
    LoginContract.LoginView {

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initListener() {
        login.setOnClickListener {
            showLoading("登录中...")
            getPresenter().login(user.text.toString().trim(), pwd.text.toString().trim())
        }
    }

    override fun onSuccess(userInfo: UserInfo) {
        hideLoading()
        showToast("登录成功")
    }

    override fun onFailed(msg: String) {
        hideLoading()
        showToast(msg)
    }

    override fun onBindPresenter() =
        LoginPresenterImpl().attachView(this) as LoginPresenterImpl
}

