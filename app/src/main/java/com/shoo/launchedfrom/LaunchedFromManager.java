package com.shoo.launchedfrom;

import android.text.TextUtils;

/**
 * Created by Shoo on 16-9-22.
 */
public class LaunchedFromManager {

    private static class Holder {
        public static LaunchedFromManager INSTANCE = new LaunchedFromManager();
    }

    private static LaunchedFromManager sInstance;

    private String mLaunchedFromPkgName;

    public static LaunchedFromManager getInstance() {
        if (sInstance == null) {
            sInstance = Holder.INSTANCE;
        }
        return sInstance;
    }

    private LaunchedFromManager() {

    }

    public void setLaunchedFromPkgName(String packageName) {
        mLaunchedFromPkgName = getSafePackageName(packageName);
    }

    public String getLaunchedFromPkgName() {
        return mLaunchedFromPkgName;
    }

    private String getSafePackageName(String packageName) {
        return !TextUtils.isEmpty(packageName) ? packageName : getDefaultPackageName();
    }

    private String getDefaultPackageName() {
        return LaunchedApplication.getAppContext().getPackageName();
    }

}
