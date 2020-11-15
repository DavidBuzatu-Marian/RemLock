package com.davidmarian_buzatu.remlock.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.davidmarian_buzatu.remlock.MainActivity;
import com.davidmarian_buzatu.remlock.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;

public class CalendarManager {
    private static final long CURR_TIME = System.currentTimeMillis();
    private static final long ONE_MONTH = CURR_TIME + 2592000000L;
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

    public static List<Event> getFromCalendar(Context context) {
        List<Event> eventsList = new ArrayList<>();
        CalendarProvider calendarProvider = new CalendarProvider(context);
        List<Calendar> calendars = calendarProvider.getCalendars().getList();
        for(Calendar calendar: calendars) {
            List<Event> events = calendarProvider.getEvents(calendar.id).getList();
            for(Event event: events) {
                if(event.dTStart >= CURR_TIME && event.dTStart < ONE_MONTH) {
                    eventsList.add(event);
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            eventsList.sort(new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    if(e1.dTStart < e2.dTStart) {
                        return -1;
                    } else if (e1.dTStart > e2.dTStart) {
                        return 1;
                    }
                    return 0;
                }
            });
        }
        return eventsList;
    }

}
