package com.zy.ypro.module.login.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.jeremyliao.liveeventbus.LiveEventBus
import com.zy.ypro.R
import com.zy.ypro.base.BaseVFragment
import com.zy.ypro.event.EventTag
import com.zy.ypro.model.entity.LoginBean
import com.zy.ypro.module.login.contract.LoginContract
import com.zy.ypro.module.login.presenter.LoginPresenterImpl
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @Description:
 * Created by yong on 2019/12/20 14:28.
 */
class LoginPageFragment : BaseVFragment<LoginPresenterImpl>(), LoginContract.LoginView {
    var isLoginPage = true
    var user: String = ""
    var pwd: String = ""
    var pwd2: String = ""
    override fun onSuccess(loginBean: LoginBean) {
        hideLoading()
        LiveEventBus.get(EventTag.KEY_LOGIN_SUCCESS_STICKY).post(loginBean)
        activity?.finish()
    }

    override fun onFailed(msg: String) {
        hideLoading()
        showToast(msg)
    }

    override fun onBindPresenter(): LoginPresenterImpl {
        return LoginPresenterImpl().attachView(this) as LoginPresenterImpl
    }

    override val layoutId: Int
        get() = R.layout.fragment_login

    companion object {
        val TYPE = "isLoginPage"
        val LOGIN = 0
        val REGISTER = 1
        fun newInstance(type: Boolean): LoginPageFragment {
            val fragment = LoginPageFragment()
            val args = Bundle()
            args.putBoolean(TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit -> {
                showLoading("")
                if (isLoginPage) {
                    getPresenter().login(user, pwd)
                } else {
                    getPresenter().registUser(user, pwd, pwd2)
                }
            }
        }
    }

    override fun initView() {
        submit.setOnClickListener(this)
        if (arguments?.get(TYPE) as Boolean) {
            isLoginPage = true
            submit.text = getString(R.string.login)
        } else {
            isLoginPage = false
            input_pwd2.visibility = View.VISIBLE
            submit.text = getString(R.string.register)
        }
    }

    override fun initListener() {
        input_user.doAfterTextChanged { text ->
            this.user = text.toString()
            checkSubmitEnable()
        }
        input_pwd.doAfterTextChanged { text ->
            this.pwd = text.toString()
            checkSubmitEnable()
        }
        input_pwd2.doAfterTextChanged { text ->
            this.pwd2 = text.toString()
            checkSubmitEnable()
        }
    }

    fun checkSubmitEnable() {
        if (isLoginPage) {
            submit.isEnabled = (user.isNotEmpty() && pwd.isNotEmpty())
        } else {
            submit.isEnabled = (user.isNotEmpty() && pwd.isNotEmpty() && pwd2.isNotEmpty())
        }
    }
}