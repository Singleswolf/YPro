package com.zy.ypro.module.mine.fragment

import android.view.View
import com.zy.commonlib.base.BaseFragment
import com.zy.ypro.R
import com.zy.ypro.event.LiveBus
import com.zy.ypro.event.EVENT_LOGIN_SUCCESS_STICKY
import com.zy.ypro.model.entity.UserInfo
import com.zy.ypro.model.store.UserInfoStore
import com.zy.ypro.module.login.view.LoginActivity
import com.zy.ypro.utils.ActivityHelper
import kotlinx.android.synthetic.main.activity_user.*

/**
 * @Description:
 * Created by yong on 2019/12/6 16:53.
 */
class UserFragment : BaseFragment() {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.icon_head, R.id.tv_login -> ActivityHelper.start(LoginActivity::class.java)
        }
    }

    override val layoutId = R.layout.activity_user

    override fun initView() {
        icon_head.setImageResource(R.mipmap.ic_launcher)
    }

    override fun initListener() {
        icon_head.setOnClickListener(this)
        tv_login.setOnClickListener(this)
    }

    override fun lazyLoadData() {
        if (UserInfoStore.isLogin()) {
            tv_login.text = UserInfoStore.getUserInfo()?.publicName ?: getString(R.string.str_login_register)
        }
    }

    override fun observer() {
        super.observer()
        LiveBus.observeSticky<UserInfo>(this, EVENT_LOGIN_SUCCESS_STICKY, {
            tv_login.text = it.publicName
        })
    }
}