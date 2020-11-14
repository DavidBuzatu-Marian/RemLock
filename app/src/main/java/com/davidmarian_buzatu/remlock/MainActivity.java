package com.davidmarian_buzatu.remlock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.davidmarian_buzatu.remlock.service.ScreenshotBroadcastReceiver;
import com.davidmarian_buzatu.remlock.service.ScreenshotFileObserver;
import com.davidmarian_buzatu.remlock.service.ServiceNotification;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.davidmarian_buzatu.remlock.service.ServiceNotification.NOTIFICATION_ID;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtonListener();
        requestPermission();
        ScreenshotFileObserver.startObserver(this);

    }

    private void requestPermission() {
        while(ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[] {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenshotFileObserver.stopObserver();
    }

    private void setButtonListener() {
        Button startServiceBTN = findViewById(R.id.activity_main_BTN_start);
        startServiceBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });
    }


    private void startService() {
        // create channel for notifications for Android version > 26
        ServiceNotification.createNotificationChannel(this);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        Notification notification = ServiceNotification.getNotification(this, pendingIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);
    }
}