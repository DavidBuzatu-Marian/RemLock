package com.davidmarian_buzatu.remlock.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.davidmarian_buzatu.remlock.MainActivity;
import com.davidmarian_buzatu.remlock.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;

public class CalendarManager {
    private static final long CURR_TIME = System.currentTimeMillis();
    private static final long ONE_MONTH = CURR_TIME + 2592000000L;
    public static void addToCalendar(Context context, String title, String location, String description, long dtStart) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intent.putExtra(CalendarContract.Events.DTSTART, dtStart);
        intent.putExtra(CalendarContract.Events.ALL_DAY, true);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dtStart);

        // Open app if form is filled and has permission
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void addToCalendarWithoutIntent(Context context,String title, String location, String description, long dtStart) {
        final ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.CALENDAR_ID, 394);

        event.put(CalendarContract.Events.TITLE, title);
        event.put(CalendarContract.Events.DESCRIPTION, description);
        event.put(CalendarContract.Events.EVENT_LOCATION, location);

        event.put(CalendarContract.Events.DTSTART, dtStart);
        event.put(CalendarContract.Events.DTEND, dtStart);
        event.put(CalendarContract.Events.ALL_DAY, 1);

        String timeZone = TimeZone.getDefault().getID();
        event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

        Uri baseUri;
        if (Build.VERSION.SDK_INT >= 8) {
            baseUri = Uri.parse("content://com.android.calendar/events");
        } else {
            baseUri = Uri.parse("content://calendar/events");
        }
        context.getContentResolver().insert(baseUri, event);
    }

    public static void openCalendar(Context context, Event event) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri.Builder uri = CalendarContract.Events.CONTENT_URI.buildUpon();
        uri.appendPath(Long.toString(event.id));
        intent.setData(uri.build());
        context.startActivity(intent);
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
