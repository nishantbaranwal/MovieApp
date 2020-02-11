package com.theavengers.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class ViewMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)

        val viewPager:ViewPager = findViewById(R.id.viewPager)
        val tabLayout:TabLayout = findViewById(R.id.tabLayout)
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)

        viewPager.setAdapter(viewPagerAdapter)
        tabLayout.setupWithViewPager(viewPager)

    }
}
