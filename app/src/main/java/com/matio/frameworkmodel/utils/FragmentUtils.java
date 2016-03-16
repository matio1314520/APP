package com.matio.frameworkmodel.utils;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by Angel on 2016/3/14.
 */
public class FragmentUtils implements RadioGroup.OnCheckedChangeListener {

    private List<Fragment> mFragmentList; // 一个tab页面对应一个Fragment

    private RadioGroup mRag; // 用于切换tab

    private FragmentManager mFragmentManager; // Fragment所属的Activity

    private int mFragmentContentId; // Activity中所要被替换的区域的id

    private int currentTab; // 当前Tab页面索引

    private OnRgsExtraCheckedChangedListener mOnRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能

    public FragmentUtils(FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId, RadioGroup rgs, OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {

        this.mFragmentList = fragments;

        this.mRag = rgs;

        this.mFragmentManager = fragmentManager;

        this.mFragmentContentId = fragmentContentId;

        this.mOnRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;

        rgs.setOnCheckedChangeListener(this);

        ((RadioButton) rgs.getChildAt(0)).setChecked(true);
    }

    public FragmentUtils(FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId, RadioGroup rgs) {

        this(fragmentManager, fragments, fragmentContentId, rgs, null);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        for (int i = 0; i < mRag.getChildCount(); i++) {

            RadioButton button = (RadioButton) mRag.getChildAt(i);

            if (button.getId() == checkedId) {

                button.setTextColor(Color.RED);

                Fragment fragment = mFragmentList.get(i);

                FragmentTransaction ft = obtainFragmentTransaction(i);

                getCurrentFragment().onStop();

                if (fragment.isAdded()) {

                    fragment.onStart();

                } else {

                    ft.add(mFragmentContentId, fragment);

                    ft.commit();
                }
                showTab(i);

                if (null != mOnRgsExtraCheckedChangedListener) {

                    mOnRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }

            } else {

                button.setTextColor(Color.GRAY);

            }
        }

    }

    private void showTab(int idx) {

        for (int i = 0; i < mFragmentList.size(); i++) {

            Fragment fragment = mFragmentList.get(i);

            FragmentTransaction ft = obtainFragmentTransaction(idx);

            if (idx == i) {

                ft.show(fragment);

            } else {

                ft.hide(fragment);

            }

            ft.commit();

        }
        currentTab = idx;
    }


    private FragmentTransaction obtainFragmentTransaction(int index) {

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        return ft;
    }

    public int getCurrentTab() {

        return currentTab;

    }

    public Fragment getCurrentFragment() {

        return mFragmentList.get(currentTab);

    }

    public OnRgsExtraCheckedChangedListener getmOnRgsExtraCheckedChangedListener() {

        return mOnRgsExtraCheckedChangedListener;

    }

    public void setmOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener mOnRgsExtraCheckedChangedListener) {

        this.mOnRgsExtraCheckedChangedListener = mOnRgsExtraCheckedChangedListener;

    }

    public static interface OnRgsExtraCheckedChangedListener {

        public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index);

    }

}