package com.zy.ypro.mvp

import java.lang.ref.WeakReference

/**
 * @Description:
 * Created by yong on 2019/12/4 20:56.
 */
open class BasePresenter<V : IView> : IPresenter {

    private var mViewRef: WeakReference<V>? = null


    fun getView(): V {
        if (!isViewAttach()) {
            throw IllegalStateException(this.javaClass.canonicalName + " is not attach view")
        }
        return mViewRef!!.get()!!
    }

    fun attachView(view: V): BasePresenter<V> {
        mViewRef = WeakReference(view)
        return this
    }

    override fun isViewAttach(): Boolean {
        return mViewRef?.get() != null
    }

    override fun detachView() {
        mViewRef?.clear()
        mViewRef = null
    }
}

