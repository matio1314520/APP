package com.matio.frameworkmodel.activity;

import android.content.Intent;
import android.view.WindowManager;

import com.matio.frameworkmodel.R;
import com.matio.frameworkmodel.base.BaseActivity;

import org.xutils.view.annotation.ContentView;


@ContentView(R.layout.activity_guide)
public class GuideActivity extends BaseActivity {


    @Override
    public void onOperate() {

        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //页面跳转
        startActivity(new Intent(this, MainActivity.class));
    }
}
