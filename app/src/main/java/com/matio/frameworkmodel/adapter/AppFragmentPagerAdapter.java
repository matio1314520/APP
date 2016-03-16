package com.matio.frameworkmodel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Angel on 2016/3/14.
 */
public class AppFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;


    public AppFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public AppFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        this(fm);
        this.mFragmentList = fragmentList;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList != null ? mFragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }
}
