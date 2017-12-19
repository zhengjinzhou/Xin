package com.zhou.xin.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by zhou on 2017/12/19.
 */

public class DemoApplication extends Application {

    public static Context applicationContext;
    private static DemoApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";
    //nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
    public static String currentUserNick = "";


    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        instance = this;

    }
}
