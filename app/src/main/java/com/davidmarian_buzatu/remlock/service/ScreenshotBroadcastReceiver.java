package com.davidmarian_buzatu.remlock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.davidmarian_buzatu.remlock.MainActivity;

public class ScreenshotBroadcastReceiver extends BroadcastReceiver {
    public static final String SCREENSHOT_PATH = "SCREENSHOT_PATH";
    public static final int SCREENSHOT_ID = 999;
    public static final String STOP_SERVICE = "STOP_SERVICE";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(STOP_SERVICE)) {
            context.stopService(new Intent(context, ScreenshotService.class));
        } else {
            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.getString(SCREENSHOT_PATH) != null) {
                Log.d("SCREENSHOT", bundle.getString(SCREENSHOT_PATH));
            }
        }
    }
}
