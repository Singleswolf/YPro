package com.zy.ypro.module.mine

import com.zy.commonlib.base.BaseFragment
import com.zy.ypro.R
import kotlinx.android.synthetic.main.fragment_tab.*


/**
 * @Description:
 * Created by yong on 2019/12/6 11:07.
 */
class TabFragment : BaseFragment() {
    val CONTENT = "content"
    override val layoutId: Int
        get() = R.layout.fragment_tab

    override fun initData() {
        val content = arguments!!.getString(CONTENT)
        tv_text.text = content
    }
}