package com.zy.ypro

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zy.commonlib.base.BaseActivity
import com.zy.ypro.module.mine.TabFragment
import com.zy.ypro.module.mine.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    override val layoutId = R.layout.activity_main
    private lateinit var fragments: Map<Int, Fragment>


    override fun initListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            changeFragment(item.itemId)
            true
        }
    }

    override fun initData() {
        val homeFragment = TabFragment()
        val bundle = Bundle()
        bundle.putString("content", "首页")
        homeFragment.arguments = bundle

        val homeFragment2 = TabFragment()
        val bundle2 = Bundle()
        bundle2.putString("content", "体系")
        homeFragment2.arguments = bundle2

        val homeFragment3 = TabFragment()
        val bundle3 = Bundle()
        bundle3.putString("content", "导航")
        homeFragment3.arguments = bundle3

        val homeFragment4 = TabFragment()
        val bundle4 = Bundle()
        bundle4.putString("content", "项目")
        homeFragment4.arguments = bundle4

        val userFragment = UserFragment()

        fragments = mapOf(
            R.id.item_home to homeFragment,
            R.id.item_system to homeFragment2,
            R.id.item_navigation to homeFragment3,
            R.id.item_project to homeFragment4,
            R.id.item_mine to userFragment
        )
        bottomNavigationView.selectedItemId = R.id.item_home
    }

    private fun changeFragment(targetIndex: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments[targetIndex]
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.frame_content, it)
            }
        }.commit()
    }
}
