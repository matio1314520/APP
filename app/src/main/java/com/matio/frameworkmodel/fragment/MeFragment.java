package com.matio.frameworkmodel.fragment;

import com.matio.frameworkmodel.R;
import com.matio.frameworkmodel.base.BaseFragment;

import org.xutils.view.annotation.ContentView;

/**
 * Created by Angel on 2016/3/14.
 */
@ContentView(R.layout.fragment_me)
public class MeFragment extends BaseFragment {

    public static MeFragment newInstance() {

        MeFragment fragment = new MeFragment();

        return fragment;
    }

    @Override
    public void onOperate() {

    }
}
