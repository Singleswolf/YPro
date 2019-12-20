package com.zy.ypro.module.login.activity

import android.view.View
import com.zy.ypro.R
import com.zy.ypro.base.BaseActivity
import com.zy.ypro.module.login.adapter.ViewPagerFragmentStateAdapter
import com.zy.ypro.module.login.fragment.LoginPageFragment
import com.zy.ypro.widget.TitleView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.title_layout.*


/**
 * @Description:
 * Created by yong on 2019/12/5 11:41.
 */
class LoginActivity : BaseActivity(), TitleView.OnClickListener {
    override fun onClickLeft() {
        onBackPressed()
    }

    override fun onClickRight() {

    }

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initListener() {
        title_view.setListener(this)
        login_register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_register -> {
                if (vp2.currentItem == 0) {
                    vp2.setCurrentItem(LoginPageFragment.REGISTER, true)
                    login_register.text = getString(R.string.login)
                    val left = resources.getDrawable(R.drawable.ic_left_arrow)
                    login_register.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null)
                } else {
                    vp2.setCurrentItem(LoginPageFragment.LOGIN, true)
                    login_register.text = getString(R.string.register)
                    val right = resources.getDrawable(R.drawable.ic_right_arrow)
                    login_register.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null)
                }
            }
        }
    }

    override fun initData() {
        login_register.text = getString(R.string.register)
        val adapter = ViewPagerFragmentStateAdapter(this)
        vp2.adapter = adapter
        vp2.isUserInputEnabled = false
    }
}

