package com.zy.ypro

import android.os.Bundle
import com.zy.ypro.base.BaseActivity
import com.zy.ypro.mvp.ui.TabFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    val fragment: ArrayList<TabFragment> = arrayListOf()
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

        val homeFragment3 = TabFragment()
        val bundle3 = Bundle()
        bundle3.putString("content", "我的")
        homeFragment3.arguments = bundle3
        fragment.add(homeFragment3)

        changeFragment(0)
    }

    private fun changeFragment(currentPosition: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_content, fragment.get(currentPosition))
        transaction.commit()
    }
}
