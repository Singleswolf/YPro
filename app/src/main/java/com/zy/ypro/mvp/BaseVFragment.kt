package com.zy.ypro.mvp

import com.zy.ypro.base.BaseFragment

/**
 * @Description:
 * Created by yong on 2019/12/4 20:45.
 */
abstract class BaseVFragment<P : BasePresenter<*>> : BaseFragment(), IView {

    private var mPresenter: P? = null

    fun getPresenter(): P {
        if (mPresenter == null) {
            mPresenter = onBindPresenter()
        }
        return mPresenter!!
    }

    protected abstract fun onBindPresenter(): P


    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.detachView()
            mPresenter = null
        }
    }
}