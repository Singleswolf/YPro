package com.zy.ypro.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

/**
 * @Description:
 * Created by yong on 2019/12/6 10:31.
 */
abstract class BaseFragment : Fragment(), IView, View.OnClickListener {
    private var isFirst = true;
    private var mDialog: ProgressDialog? = null
    protected var mContext: Context? = null
    protected abstract val layoutId: Int
    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.mContext = activity
        initView()
        initListener()
        initData()
        onVisible()
    }

    protected open fun initView() {}
    protected open fun initData() {}
    protected open fun initListener() {}

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    /**
     * 懒加载
     */
    protected open fun lazyLoadData() {}

    override fun showLoading(msg: String) {
        if (mDialog == null) {
            mDialog = ProgressDialog(activity)
        }
        if (!TextUtils.isEmpty(msg)) {
            mDialog!!.setMessage(msg)
        }
        mDialog!!.show()
    }

    override fun hideLoading() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {

    }
}