package com.davidmarian_buzatu.remlock.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.davidmarian_buzatu.remlock.MainActivity;

import static com.davidmarian_buzatu.remlock.service.ServiceNotification.NOTIFICATION_ID;

public class ScreenshotService extends Service {
    public static final String CHANNEL_ID = "ScreenshotServiceChannel";
    private static NotificationManager gNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        gNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        startMonitorisation();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        gNotificationManager.cancel(NOTIFICATION_ID);
        ScreenshotFileObserver.stopObserver();
        // Tell the user we stopped.
        Log.d("SERVICE", "STOPPED SERVICE");
    }

    private void startMonitorisation() {
        // create channel for notifications for Android version > 26
        ServiceNotification.createNotificationChannel(this);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        Notification notification = ServiceNotification.getNotification(this, pendingIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);
        ScreenshotFileObserver.startObserver(this);
    }

}
