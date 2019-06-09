/*
 * Copyright (c) 2018. welcomeworld All rights reserved
 */

package com.github.welcomeworld.android_kit;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.github.welcomeworld.android_kit.service.ActivityInfoService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button traceButton=findViewById(R.id.main_trace_button);
        traceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ActivityInfoService.class);
                startService(intent);
                int accessibilityEnabled = 0;
                try {
                    accessibilityEnabled= Settings.Secure.getInt(getContentResolver(),Settings.Secure.ACCESSIBILITY_ENABLED);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                if (accessibilityEnabled == 1) {
                    String services = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                    if (services != null) {
                        if(services.toLowerCase().contains(getPackageName().toLowerCase())){
                            return;
                        }
                    }
                }
                Intent settingIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                settingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(settingIntent);
            }
        });
    }
}
