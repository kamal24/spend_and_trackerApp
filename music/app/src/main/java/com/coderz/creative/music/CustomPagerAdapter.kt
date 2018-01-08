package com.coderz.creative.music

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

/**
 * Created by kamal on 20/12/17.
 */
class CustomPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm){
    var mFm = fm
    var mFragmentItems = ArrayList<Fragment>()
    var mFragmentTitles = ArrayList<String>()

    override fun getCount(): Int {
        return mFragmentItems.size
    }

    override fun getItem(position: Int): Fragment {
         return  mFragmentItems[position]
    }

    override fun getItemPosition(obj: Any?): Int {
   /*     if(obj is CatagoryListFragment)
            return PagerAdapter.POSITION_NONE
        return super.getItemPosition(obj)*/
        return PagerAdapter.POSITION_NONE
    }

    fun addFragment(fm :Fragment, title: String){
        mFragmentItems.add(fm)
        mFragmentTitles.add(title)
    }


    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitles[position]
    }

}