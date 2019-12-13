package com.zy.ypro.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zy.ypro.mvp.IView

/**
 * @Description:
 * Created by yong on 2019/12/6 10:31.
 */
abstract class BaseActivity : AppCompatActivity(), IView, View.OnClickListener {
    private var mDialog: ProgressDialog? = null
    protected var mContext: Context? = null
    protected abstract val layoutId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mContext = this
        setContentView(layoutId)
        initView()
        initListener()
        initData()
    }

    protected open fun initView() {}
    protected open fun initData() {}
    protected open fun initListener() {}

    override fun showLoading(msg: String) {
        if (mDialog == null) {
            mDialog = ProgressDialog(this)
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {

    }
}