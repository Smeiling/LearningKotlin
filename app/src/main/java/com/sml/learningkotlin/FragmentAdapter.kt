package com.sml.learningkotlin

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Smeiling on 2017/11/8.
 */

class FragmentAdapter(fm: FragmentManager, private val mFragments: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(arg0: Int): Fragment {
        return mFragments[arg0]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

}