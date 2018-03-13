package com.youtuberapp.irakli.youtuberapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View

class HomeActivity : AppCompatActivity() {
    var Tag:String = "HomeACtivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

SectionsPageAdapter(supportFragmentManager)
        var mViewPager = findViewById<ViewPager>(R.id.container)
        setupViewPager(mViewPager)

        var tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)


    }


    private fun setupViewPager(viewPager:ViewPager) {
        var adapter = SectionsPageAdapter(supportFragmentManager)
    adapter.addFragment(videosFragment(),"TestTab")
        adapter.addFragment(HomeFragment(),"TestTab2")
        viewPager.adapter = adapter

    }

}
