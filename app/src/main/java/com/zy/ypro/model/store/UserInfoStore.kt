package com.zy.ypro.model.store

import com.google.gson.Gson
import com.zy.commonlib.http.RetrofitFactory
import com.zy.ypro.model.entity.UserInfo
import com.zy.ypro.core.SPDelegate
import com.zy.ypro.ext.fromJson

/**
 * Created by yong on 2020/8/27 20:55.
 */
object UserInfoStore {
    private const val KEY_USER_INFO = "sp_key_userInfo"
    private var spUserInfo: String by SPDelegate(KEY_USER_INFO, "")
    private val gson by lazy { Gson() }
    fun updateUserInfo(userInfo: UserInfo) {
        spUserInfo = gson.toJson(userInfo)
    }

    fun getUserInfo(): UserInfo? {
        val info = spUserInfo
        return if (info.isNotEmpty()) {
            gson.fromJson(info)
        } else {
            null
        }
    }

    fun clearUserInfo() {
        spUserInfo = ""
    }

    fun isLogin(): Boolean {
        return spUserInfo.isNotEmpty() && RetrofitFactory.hasCookie()
    }
}