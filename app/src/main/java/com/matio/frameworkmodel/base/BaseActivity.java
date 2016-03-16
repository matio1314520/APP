package com.matio.frameworkmodel.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * Created by Angel on 2016/3/14.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);


        //用户操作
        onOperate();
    }

    /**
     * 用户操作
     */
    public abstract void onOperate();
}
