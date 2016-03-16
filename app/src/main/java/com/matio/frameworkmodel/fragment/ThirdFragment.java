package com.matio.frameworkmodel.fragment;

import android.os.Bundle;

import com.matio.frameworkmodel.R;
import com.matio.frameworkmodel.base.BaseFragment;

import org.xutils.view.annotation.ContentView;

/**
 * Created by Angel on 2016/2/19.
 */
@ContentView(R.layout.fragment_third)
public class ThirdFragment extends BaseFragment {


    public static SecondFragment newInstance() {

        Bundle args = new Bundle();

        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onOperate() {

    }
}
