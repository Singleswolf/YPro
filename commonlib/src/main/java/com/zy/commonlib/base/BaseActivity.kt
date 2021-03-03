package com.zy.commonlib.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils

/**
 * @Description:
 * Created by yong on 2019/12/6 10:31.
 */
@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {
    private val mDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }
    protected lateinit var mContext: Context
    protected abstract val layoutId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mContext = this
        BarUtils.setStatusBarColor(this, Color.parseColor("#3790F0")/*ColorUtils.getColor(R.color.common_bg)*/, true)
        setContentView(layoutId)
        observer()
        initView()
        initListener()
        initData()
    }

    protected open fun observer() {}
    protected open fun initView() {}
    protected open fun initData() {}
    protected open fun initListener() {}

    protected fun showLoading(msg: String = "") {
        if (!TextUtils.isEmpty(msg)) {
            mDialog.setMessage(msg)
        }
        mDialog.show()
    }

    protected fun hideLoading() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {

    }
}