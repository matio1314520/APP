package com.matio.frameworkmodel;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Angel on 2016/3/14.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
