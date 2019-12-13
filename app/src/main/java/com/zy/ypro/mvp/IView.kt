package com.zy.ypro.mvp

/**
 * @Description:
 * Created by yong on 2019/12/4 20:46.
 */
interface IView {
    fun showLoading(msg: String)
    fun hideLoading()
    fun showToast(msg: String)
}