package com.davidmarian_buzatu.remlock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ScreenshotBroadcastReceiver extends BroadcastReceiver {
    public static final String SCREENSHOT_PATH = "SCREENSHOT_PATH";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            Log.d("SCREENSHOT", bundle.getString(SCREENSHOT_PATH));
        }
    }
}
