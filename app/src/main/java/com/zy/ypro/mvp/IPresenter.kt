package com.zy.ypro.mvp

/**
 * @Description:
 * Created by yong on 2019/12/4 20:48.
 */
interface IPresenter {
    fun isViewAttach(): Boolean
    fun detachView()
}