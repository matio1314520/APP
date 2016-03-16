package com.matio.frameworkmodel.utils;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Angel on 2016/3/14.
 */
public class ViewPagerUtils {

    /**
     * viewpager adapter
     *
     * @param viewPager
     * @param viewList
     */
    public static void setAdapter(ViewPager viewPager, final List<View> viewList) {

        if (viewPager != null) {
            viewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return viewList != null ? viewList.size() : 0;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == (View) object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    View view = viewList.get(position);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(viewList.get(position));
                }
            });
        }
    }
}
