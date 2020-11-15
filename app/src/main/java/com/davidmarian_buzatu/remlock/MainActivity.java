package com.davidmarian_buzatu.remlock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.pm.PackageManager;

import com.davidmarian_buzatu.remlock.calendar.CalendarManager;
import com.davidmarian_buzatu.remlock.service.ScreenshotService;

import java.util.List;

import me.everything.providers.android.calendar.Event;

import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_CALENDAR;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        requestPermission();
        RecyclerView EventsList = findViewById(R.id.EventsList);
        EventsList.setLayoutManager(new LinearLayoutManager(this));
        List<Event> events = CalendarManager.getFromCalendar(this);
        EventsList.setAdapter(new EventsList(events, this));
        setButtonListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ContextCompat.checkSelfPermission(this, READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            CalendarManager.getFromCalendar(this);
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_CALENDAR, READ_CALENDAR}, 1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void setButtonListener() {
        Button startServiceBTN = findViewById(R.id.act_main_BTN_start);
        startServiceBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, ScreenshotService.class));
            }
        });
    }


}