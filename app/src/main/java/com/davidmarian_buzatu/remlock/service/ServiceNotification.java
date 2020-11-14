package com.davidmarian_buzatu.remlock.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.davidmarian_buzatu.remlock.R;

import static com.davidmarian_buzatu.remlock.service.ScreenshotBroadcastReceiver.SCREENSHOT_ID;
import static com.davidmarian_buzatu.remlock.service.ScreenshotBroadcastReceiver.STOP_SERVICE;
import static com.davidmarian_buzatu.remlock.service.ScreenshotService.CHANNEL_ID;

public class ServiceNotification {
    public static final int NOTIFICATION_ID = 1234;


    public static Notification getNotification(Context context, PendingIntent pendingIntent) {
        Intent closeIntent = new Intent(context, ScreenshotBroadcastReceiver.class);
        closeIntent.setAction(STOP_SERVICE);
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getText(R.string.notification_title))
                .setContentText(context.getText(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_close,"Stop monitoring", PendingIntent.getBroadcast(context, SCREENSHOT_ID, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
    }

    public static void createNotificationChannel (Context context) {
        // necessary for Android SDK > 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
