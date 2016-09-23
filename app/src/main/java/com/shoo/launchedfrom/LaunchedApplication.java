package com.shoo.launchedfrom;

import android.app.Application;
import android.content.Context;

/**
 * Created by Shoo on 16-9-22.
 */
public class LaunchedApplication extends Application {

    private static Context sAppContext;

    public static Context getAppContext() {
        return sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ActivityThreadHooks.handlerCallbackHook();
    }
}
