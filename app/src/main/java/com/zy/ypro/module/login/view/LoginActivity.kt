package com.zy.ypro.module.login.view

import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.zy.commonlib.base.BaseVmActivity
import com.zy.ypro.R
import com.zy.ypro.ext.flipAnimatorYViewShow
import com.zy.ypro.module.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


/**
 * @Description:
 * Created by yong on 2019/12/5 11:41.
 */
class LoginActivity : BaseVmActivity<LoginViewModel>() {
    var user: String = ""
    var pwd: String = ""
    var pwd2: String = ""

    override val layoutId = R.layout.activity_login

    override fun initListener() {
        toolbar.apply {
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
        register_user.setOnClickListener(this)
        submit.setOnClickListener(this)
        input_user.doAfterTextChanged { text ->
            this.user = text.toString()
        }
        input_pwd.doAfterTextChanged { text ->
            this.pwd = text.toString()
        }
        input_pwd2.doAfterTextChanged { text ->
            this.pwd2 = text.toString()
        }
    }

    override fun initData() {
        toolbar.title = getString(R.string.str_login)
    }

    private fun isLoginLayout() = wrap_input_pwd2.visibility == View.GONE

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.register_user -> {
                flipAnimatorYViewShow(root_content, isLoginLayout(), 1000) { flag ->
                    user = ""
                    pwd = ""
                    pwd2 = ""
                    wrap_input_user.error = ""
                    wrap_input_pwd.error = ""
                    wrap_input_pwd2.error = ""
                    if (flag) {
                        wrap_input_pwd2.visibility = View.VISIBLE
                        register_user.text = getString(R.string.str_login)
                        submit.text = getString(R.string.str_register)
                        toolbar.title = getString(R.string.str_register)
                    } else {
                        wrap_input_pwd2.visibility = View.GONE
                        register_user.text = getString(R.string.str_register)
                        submit.text = getString(R.string.str_login)
                        toolbar.title = getString(R.string.str_login)
                    }
                }
            }
            R.id.submit -> {
                wrap_input_user.error = ""
                wrap_input_pwd.error = ""
                wrap_input_pwd2.error = ""
                if (isLoginLayout()) {
                    when {
                        user.isEmpty() -> wrap_input_user.error = getString(R.string.str_tips_user_empty)
                        pwd.isEmpty() -> wrap_input_pwd.error = getString(R.string.str_tips_password_empty)
                        else -> mViewModel.doLogin(user, pwd)
                    }
                } else {
                    when {
                        user.isEmpty() -> wrap_input_user.error = getString(R.string.str_tips_user_empty)
                        user.length < 3 -> wrap_input_user.error = getString(R.string.str_tips_user_less_3)
                        pwd.isEmpty() -> wrap_input_pwd.error = getString(R.string.str_tips_password_empty)
                        pwd.length < 6 -> wrap_input_pwd.error = getString(R.string.str_tips_password_less_6)
                        pwd2.isEmpty() -> wrap_input_pwd2.error = getString(R.string.str_tips_confirm_password_empty)
                        pwd != pwd2 -> wrap_input_pwd2.error = getString(R.string.str_tips_confirm_password_error)
                        else -> mViewModel.doRegister(user, pwd, pwd2)
                    }
                }
            }
        }
    }

    override fun observer() {
        mViewModel.run {
            loading.observe(this@LoginActivity, {
                if (it) showLoading("") else hideLoading()
            })
            result.observe(this@LoginActivity, {
                if (it) finish()
            })
        }
    }
}

