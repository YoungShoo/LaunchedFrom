package com.shoo.launchedfrom;

import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * Created by Shoo on 16-9-22.
 */
public class HandlerCallbackHook implements Handler.Callback {

    public static final int LAUNCH_ACTIVITY = 100;

    private Handler.Callback mOriginCallback;

    public HandlerCallbackHook(Handler.Callback originCallback) {
        mOriginCallback = originCallback;
    }

    @Override
    public boolean handleMessage(Message msg) {
        beforeCallback(msg);
        return mOriginCallback != null && mOriginCallback.handleMessage(msg);
    }

    private void beforeCallback(Message msg) {
        switch (msg.what) {
            case LAUNCH_ACTIVITY:
                try {
                    Object activityClientRecord = msg.obj;
                    Class<?> acrClass = activityClientRecord.getClass();
                    Field referrer = acrClass.getDeclaredField("referrer");
                    referrer.setAccessible(true);
                    Object launchedFromPackage = referrer.get(activityClientRecord);
                    LaunchedFromManager.getInstance().setLaunchedFromPkgName((String) launchedFromPackage);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

}