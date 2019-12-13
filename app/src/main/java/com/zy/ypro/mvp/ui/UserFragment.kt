package com.zy.ypro.mvp.ui

import android.content.Intent
import android.view.View
import com.zy.ypro.R
import com.zy.ypro.mvp.BaseVFragment
import com.zy.ypro.mvp.contract.UserContract
import com.zy.ypro.mvp.presenter.UserPresenterImpl
import kotlinx.android.synthetic.main.activity_user.*

/**
 * @Description:
 * Created by yong on 2019/12/6 16:53.
 */
class UserFragment : BaseVFragment<UserPresenterImpl>(), UserContract.UserView {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.icon_head, R.id.tv_login ->
                startActivity(Intent(mContext, LoginActivity::class.java))
        }
    }

    override fun onBindPresenter() = UserPresenterImpl().attachView(this) as UserPresenterImpl

    override val layoutId: Int
        get() = R.layout.activity_user

    override fun initListener() {
        icon_head.setOnClickListener(this)
        tv_login.setOnClickListener(this)
    }
}