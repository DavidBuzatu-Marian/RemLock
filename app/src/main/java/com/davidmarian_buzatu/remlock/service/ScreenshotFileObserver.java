package com.davidmarian_buzatu.remlock.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;

import static com.davidmarian_buzatu.remlock.service.ScreenshotBroadcastReceiver.SCREENSHOT_PATH;

public class ScreenshotFileObserver {
    private static FileObserver fileObserver;

    public static void startObserver(final Context context) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Screenshots" + File.separator;
                final File f = new File(path);
                if (f.isDirectory()) {
                    fileObserver = new FileObserver(path, FileObserver.CLOSE_WRITE) {
                        @Override
                        public void onEvent(int event, @Nullable String eventPath) {
                            // .lock files are generated after screenshot
                            if(eventPath != null && !eventPath.endsWith(".lock")) {
                                context.sendBroadcast(new Intent(context, ScreenshotBroadcastReceiver.class).setAction("Screenshot").putExtra(SCREENSHOT_PATH, path + eventPath));
                            }
                        }
                    };
                    fileObserver.startWatching();
                }
            }
        });
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    public static void stopObserver() {
        fileObserver.stopWatching();
    }
}
