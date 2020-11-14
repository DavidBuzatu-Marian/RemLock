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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.davidmarian_buzatu.remlock.service.ScreenshotBroadcastReceiver;
import com.davidmarian_buzatu.remlock.service.ScreenshotFileObserver;
import com.davidmarian_buzatu.remlock.service.ScreenshotService;
import com.davidmarian_buzatu.remlock.service.ServiceNotification;

import java.io.File;

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
    }

    private void requestPermission() {
        while(ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[] {READ_EXTERNAL_STORAGE},1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void setButtonListener() {
        Button startServiceBTN = findViewById(R.id.activity_main_BTN_start);
        startServiceBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, ScreenshotService.class));
            }
        });
    }


}