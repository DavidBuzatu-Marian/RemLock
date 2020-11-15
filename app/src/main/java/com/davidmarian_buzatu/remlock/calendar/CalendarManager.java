package com.davidmarian_buzatu.remlock.calendar;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.widget.Toast;

import com.davidmarian_buzatu.remlock.MainActivity;
import com.davidmarian_buzatu.remlock.R;

public class CalendarManager {

    public static void addToCalendar(Context context, String title, String location, String description) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intent.putExtra(CalendarContract.Events.ALL_DAY, true);

        // Open app if form is filled and has permission
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

}
