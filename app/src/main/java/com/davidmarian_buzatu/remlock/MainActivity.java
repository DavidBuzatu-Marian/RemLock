package com.davidmarian_buzatu.remlock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    EditText title;
    EditText location;
    EditText description;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.eventTitle);
        location = findViewById(R.id.eventLocation);
        description = findViewById(R.id.eventDescription);
        addEvent = findViewById(R.id.btnAdd);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if event form is filled
                // Currently all of the fields must be filled for the
                // program to open Calendar, this can be changed later
                if (!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty()
                        && !description.getText().toString().isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    intent.putExtra(Intent.EXTRA_EMAIL, "test@yahoo.com, test2@yahoo.com");

                    // Open app if form is filled and has permission
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "There is no app that can support this action",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "enter details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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