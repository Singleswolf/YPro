package com.zy.ypro.module.mine.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.zy.ypro.R
import com.zy.ypro.base.BaseVFragment
import com.zy.ypro.event.EventTag
import com.zy.ypro.model.entity.LoginBean
import com.zy.ypro.module.login.activity.LoginActivity
import com.zy.ypro.module.mine.contract.UserContract
import com.zy.ypro.module.mine.presenter.UserPresenterImpl
import com.zy.ypro.utils.Logger
import kotlinx.android.synthetic.main.activity_user.*

/**
 * @Description:
 * Created by yong on 2019/12/6 16:53.
 */
class UserFragment : BaseVFragment<UserPresenterImpl>(), UserContract.UserView {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.icon_head, R.id.tv_login -> startActivity(Intent(mContext,
                LoginActivity::class.java))
        }
    }

    override fun onBindPresenter() = UserPresenterImpl().attachView(this) as UserPresenterImpl

    override val layoutId: Int
        get() = R.layout.activity_user

    override fun initView() {
        icon_head.setImageResource(R.drawable.ic_launcher)
        tv_login.setText(R.string.login)
    }

    override fun initListener() {
        icon_head.setOnClickListener(this)
        tv_login.setOnClickListener(this)
    }

    override fun lazyLoadData() {
        Logger.v("lazyLoadData")
        LiveEventBus.get(EventTag.KEY_LOGIN_SUCCESS_STICKY, LoginBean::class.java)
            .observeSticky(this, Observer {
                Logger.v("login status ${it.publicName}")
            })
    }
}