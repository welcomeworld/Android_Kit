/*
 * Copyright (c) 2018. welcomeworld All rights reserved
 */

package com.github.welcomeworld.android_kit.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;

import com.github.welcomeworld.android_kit.R;
import com.github.welcomeworld.android_kit.manager.MyWindowManager;
import com.github.welcomeworld.android_kit.view.FloatNormalView;

public class ActivityInfoService extends AccessibilityService {

    String TAG="ActivityInfoService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyWindowManager.getInstance().createNormalView(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName=event.getPackageName().toString();
        String className=event.getClassName().toString();
        switch (event.getEventType()){
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.e(TAG,"WINDOW_CHANGE~packageName:"+packageName+"className"+className);
                FloatNormalView floatNormalView=MyWindowManager.getInstance().getNormalView();
                if(floatNormalView!=null){
                    TextView packageView=floatNormalView.findViewById(R.id.float_packageName);
                    TextView classView=floatNormalView.findViewById(R.id.float_className);
                    packageView.setText("PackageName:"+packageName);
                    classView.setText("ClassName:"+className);
                }else{
                    Log.e(TAG,"floatNormal is null");
                }
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.e(TAG,"VIEW_CLICK~packageName:"+packageName+"className"+className);
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }
}
