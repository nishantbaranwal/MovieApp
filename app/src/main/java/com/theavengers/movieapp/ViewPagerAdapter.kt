package com.theavengers.movieapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(context: Context, supportFragmentManager: FragmentManager):FragmentPagerAdapter(supportFragmentManager) {
    var ctx:Context = context
    override fun getItem(position: Int): Fragment? {
        var fragment:Fragment = Fragment()
        if (position == 0)
            fragment = TopMoviesFragment(ctx)
        else
            fragment = TopMoviesFragment(ctx)
        return fragment
    }

    override fun getCount(): Int {
        return 2
    }

}
