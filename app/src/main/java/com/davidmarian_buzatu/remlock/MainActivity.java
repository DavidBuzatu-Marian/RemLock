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
import com.davidmarian_buzatu.remlock.service.ScreenshotService;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    EditText title;
    EditText location;
    EditText description;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView EventsList = findViewById(R.id.EventsList);
        EventsList.setLayoutManager(new LinearLayoutManager(this));
        String[] events = {"one", "two", "three", "four", "five", "six"};
        EventsList.setAdapter(new EventsList(events, this));
        setButtonListener();
        requestPermission();
    }

    private void requestPermission() {
        while (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, 1);
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