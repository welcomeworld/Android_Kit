/*
 * Copyright (c) 2018. welcomeworld All rights reserved
 */

package com.github.welcomeworld.android_kit.manager;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.github.welcomeworld.android_kit.view.FloatNormalView;

public class MyWindowManager {

    private FloatNormalView normalView;

    private static MyWindowManager instance;

    private MyWindowManager() {
    }

    public static MyWindowManager getInstance() {
        if (instance == null)
            instance = new MyWindowManager();
        return instance;
    }


    /**
     * 创建小型悬浮窗
     */
    public void createNormalView(Context context) {
        if (normalView == null)
            normalView = new FloatNormalView(context);
        Log.e("ActivityInfoService","normal view have been created");
    }

    public FloatNormalView getNormalView(){
        return normalView;
    }

    /**
     * 移除悬浮窗
     *
     * @param context
     */
    public void removeNormalView(Context context) {
        if (normalView != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.removeView(normalView);
            normalView = null;
        }
    }


}
