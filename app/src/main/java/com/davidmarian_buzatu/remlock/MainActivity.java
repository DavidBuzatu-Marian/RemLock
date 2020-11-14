package com.davidmarian_buzatu.remlock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView EventsList = findViewById(R.id.EventsList);
        EventsList.setLayoutManager(new LinearLayoutManager(this));
        String[] events = {"one", "two", "three", "four", "five", "six"};
        EventsList.setAdapter(new EventsList(events));
    }
}