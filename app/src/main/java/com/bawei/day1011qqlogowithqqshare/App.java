package com.bawei.day1011qqlogowithqqshare;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by 张祺钒
 * on2017/10/11.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setQQZone("1106036236", "mjFCi0oxXZKZEWJs");
    }   
}
