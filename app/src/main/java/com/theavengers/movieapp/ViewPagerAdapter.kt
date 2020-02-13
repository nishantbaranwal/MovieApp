package com.theavengers.movieapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(context: Context, supportFragmentManager: FragmentManager):FragmentPagerAdapter(supportFragmentManager) {
    var ctx:Context = context
    override fun getItem(position: Int): Fragment {
        var fragment:Fragment = Fragment()
        if (position == 0)
            fragment = TopMoviesFragment(ctx)
        else
            if (position == 1)
                fragment = TopKidMoviesFragment(ctx)
        else
                if(position == 2)
                fragment = YearBestMoviesFragment(ctx)
        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(position == 0)
            return "Top Movies"
        else
            if (position == 1)
            return "Top Kid's Movies"
        else
                return "2010 Best Dramas"
    }
}
