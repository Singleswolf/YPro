package com.zy.ypro.mvp.contract

import com.zy.ypro.mvp.IPresenter
import com.zy.ypro.mvp.IView

/**
 * @Description:
 * Created by yong on 2019/12/6 16:54.
 */
interface UserContract {
    interface UserView : IView
    interface UserPresenter : IPresenter
}