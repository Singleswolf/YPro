package com.zy.ypro

import android.os.Bundle
import com.zy.ypro.base.BaseActivity
import com.zy.ypro.base.BaseFragment
import com.zy.ypro.module.mine.TabFragment
import com.zy.ypro.module.mine.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    val fragment: ArrayList<BaseFragment> = arrayListOf()
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initListener() {
        bottom_bar.setOnItemSelectedListener { item, previousPosition, currentPosition ->
            changeFragment(currentPosition);
        }
    }

    override fun initData() {
        val homeFragment = TabFragment()
        val bundle = Bundle()
        bundle.putString("content", "首页")
        homeFragment.arguments = bundle
        fragment.add(homeFragment)

        val homeFragment2 = TabFragment()
        val bundle2 = Bundle()
        bundle2.putString("content", "视频")
        homeFragment2.arguments = bundle2
        fragment.add(homeFragment2)

        val userFragment = UserFragment()
        fragment.add(userFragment)

        changeFragment(0)
    }

    private fun changeFragment(currentPosition: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_content, fragment.get(currentPosition))
        transaction.commit()
    }
}
