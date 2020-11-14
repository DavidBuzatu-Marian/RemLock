package com.davidmarian_buzatu.remlock.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.davidmarian_buzatu.remlock.R;

import static com.davidmarian_buzatu.remlock.service.ScreenshotService.CHANNEL_ID;

public class ServiceNotification {
    public static final int NOTIFICATION_ID = 1234;


    public static Notification getNotification(Activity activity, PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setContentTitle(activity.getText(R.string.notification_title))
                .setContentText(activity.getText(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build();
    }

    public static void createNotificationChannel (Activity activity) {
        // necessary for Android SDK > 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = activity.getString(R.string.channel_name);
            String description = activity.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
