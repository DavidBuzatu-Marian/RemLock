package com.davidmarian_buzatu.remlock.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;

import static com.davidmarian_buzatu.remlock.service.ScreenshotBroadcastReceiver.SCREENSHOT_PATH;

public class ScreenshotFileObserver {
    private static FileObserver fileObserver;

    public static void startObserver(final Activity activity) {
        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Screenshots" + File.separator;
        File f = new File(path);
        if (f.isDirectory()) {
            fileObserver = new FileObserver(path, FileObserver.CREATE) {
                @Override
                public void onEvent(int event, @Nullable String eventPath) {
                    activity.sendBroadcast(new Intent(activity, ScreenshotBroadcastReceiver.class).setAction("Screenshot").putExtra(SCREENSHOT_PATH, path + eventPath));
                }
            };
            fileObserver.startWatching();
        }

    }

    public static void stopObserver() {
        fileObserver.stopWatching();
    }
}
