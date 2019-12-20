package com.zy.ypro.module.login.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zy.ypro.module.login.fragment.LoginPageFragment

/**
 * @Description:
 * Created by yong on 2019/12/20 14:41.
 */
class ViewPagerFragmentStateAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    val fragments: ArrayList<LoginPageFragment> by lazy {
        arrayListOf(LoginPageFragment.newInstance(true), LoginPageFragment.newInstance(false))
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): LoginPageFragment {
        return fragments.get(position)
    }
}