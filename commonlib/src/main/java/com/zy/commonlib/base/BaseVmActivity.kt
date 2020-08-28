package com.zy.commonlib.base

import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * Created by yong on 2020/8/27 17:15.
 */
abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {
    protected open val mViewModel: VM by lazy {
        ViewModelProvider(this).get(getViewModelClz())
    }

    private fun getViewModelClz(): Class<VM> {
        val type = this.javaClass.genericSuperclass
        val param = (type as ParameterizedType?)!!.actualTypeArguments
        return param[0] as Class<VM>
    }

}