/*
 * Copyright (c) 2018. welcomeworld All rights reserved
 */

package com.github.welcomeworld.android_kit.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.welcomeworld.android_kit.R;
import com.github.welcomeworld.android_kit.manager.MyWindowManager;

public class FloatNormalView extends LinearLayout {

    private Context context = null;
    private View view = null;
    private WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    private static WindowManager windowManager;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private boolean initViewPlace = false;
    private MyWindowManager myWindowManager;

    public FloatNormalView(final Context context) {
        super(context);
        this.context = context;
        myWindowManager = MyWindowManager.getInstance();
        LayoutInflater.from(context).inflate(R.layout.float_normal_view, this);
        view = findViewById(R.id.ll_float_normal);
        Button cancelButton=findViewById(R.id.float_cancel_button);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWindowManager.getInstance().removeNormalView(context);
            }
        });
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initLayoutParams();
        initEvent();
    }


    /**
     * 初始化参数
     */
    private void initLayoutParams() {
        //屏幕宽高
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        //总是出现在应用程序窗口之上。
        if(Build.VERSION.SDK_INT>=26){
            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按,不设置这个flag的话，home页的划屏会有问题
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //悬浮窗默认显示的位置
        lp.gravity = Gravity.START | Gravity.TOP;
        //指定位置
        lp.x = screenWidth - view.getLayoutParams().width * 2;
        lp.y = screenHeight / 2 + view.getLayoutParams().height * 2;
        //悬浮窗的宽高
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.format = PixelFormat.TRANSPARENT;
        windowManager.addView(this, lp);
    }

    /**
     * 设置悬浮窗监听事件
     */
    private void initEvent() {
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!initViewPlace) {
                            initViewPlace = true;
                            //获取初始位置
                            mTouchStartX += (event.getRawX() - lp.x);
                            mTouchStartY += (event.getRawY() - lp.y);
                        } else {
                            //根据上次手指离开的位置与此次点击的位置进行初始位置微调
                            mTouchStartX += (event.getRawX() - x);
                            mTouchStartY += (event.getRawY() - y);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取相对屏幕的坐标，以屏幕左上角为原点
                        x = event.getRawX();
                        y = event.getRawY();
                        updateViewPosition();
                        break;

                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 更新浮动窗口位置
     */
    private void updateViewPosition() {
        lp.x = (int) (x - mTouchStartX);
        lp.y = (int) (y - mTouchStartY);
        windowManager.updateViewLayout(this, lp);
    }
}