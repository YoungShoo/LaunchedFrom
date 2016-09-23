package com.shoo.launchedfrom;

import android.os.Handler;

import java.lang.reflect.Field;

/**
 * Created by Shoo on 16-9-22.
 */
public class ActivityThreadHooks {

    public static void handlerCallbackHook() {
        try {
            // ActivityThread ActivityThread.sCurrentActivityThread
            Class<?> atClass = Class.forName("android.app.ActivityThread");
            Field atField = atClass.getDeclaredField("sCurrentActivityThread");
            atField.setAccessible(true);
            Object sCurrentActivityThread = atField.get(null);

            // Handler sCurrentActivityThread.mH
            Field handlerField = atClass.getDeclaredField("mH");
            handlerField.setAccessible(true);
            Object mH = handlerField.get(sCurrentActivityThread);

            // Callback mH.mCallback
            Field callbackField = Handler.class.getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            Handler.Callback mCallback = (Handler.Callback) callbackField.get(mH);

            // hook Callback
            callbackField.set(mH, new HandlerCallbackHook(mCallback));
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}