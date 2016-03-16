package com.matio.frameworkmodel.activity;

import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.matio.frameworkmodel.R;
import com.matio.frameworkmodel.base.BaseActivity;
import com.matio.frameworkmodel.fragment.HomeFragment;
import com.matio.frameworkmodel.fragment.MeFragment;
import com.matio.frameworkmodel.fragment.SecondFragment;
import com.matio.frameworkmodel.fragment.ThirdFragment;
import com.matio.frameworkmodel.utils.FragmentUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements FragmentUtils.OnRgsExtraCheckedChangedListener {

    @ViewInject(R.id.radiogroup_activity_main)
    private RadioGroup mMainRag;   //radiogroup

    @Override
    public void onOperate() {

        ArrayList<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(SecondFragment.newInstance());
        fragmentList.add(ThirdFragment.newInstance());
        fragmentList.add(MeFragment.newInstance());

        new FragmentUtils(getSupportFragmentManager(), fragmentList, R.id.container_activity_main, mMainRag, this);
    }

    @Override
    public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {

    }
}
