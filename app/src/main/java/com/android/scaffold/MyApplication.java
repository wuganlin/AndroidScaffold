package com.android.scaffold;

import android.app.Application;
import android.content.Context;


/**
 * Created by wuganlin on 2017/4/1.
 */

public class MyApplication extends Application {
    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Context getContext() {
        return mApplication.getApplicationContext();
    }
}
