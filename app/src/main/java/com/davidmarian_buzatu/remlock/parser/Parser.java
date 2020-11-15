package com.davidmarian_buzatu.remlock.parser;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Parser {
    private static SimpleDateFormat validSDF;

    public static boolean isValidTime(String time) {
        return time.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
    }

    // consider formats like Friday, 13 November 2020
    public static boolean isValidDate(String date) {
        int res = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/dd/mm", Locale.getDefault());
        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/mm/dd", Locale.getDefault());
        SimpleDateFormat sdf5 = new SimpleDateFormat("EEEEE, MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf6 = new SimpleDateFormat("EEEEE, MM yyyy", Locale.getDefault());
        SimpleDateFormat sdf7 = new SimpleDateFormat("EEEEE MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf8 = new SimpleDateFormat("EEEEE MM yyyy", Locale.getDefault());
        SimpleDateFormat sdf9 = new SimpleDateFormat("EEE, MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf10 = new SimpleDateFormat("EEE, MM yyyy", Locale.getDefault());
        SimpleDateFormat sdf11 = new SimpleDateFormat("EEE MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf12 = new SimpleDateFormat("EEE MM yyyy", Locale.getDefault());
        SimpleDateFormat sdf13 = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        SimpleDateFormat sdf14 = new SimpleDateFormat("dd MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf15 = new SimpleDateFormat("EEEEE, d MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf16 = new SimpleDateFormat("EEEEE, d MM yyyy", Locale.getDefault());
        SimpleDateFormat sdf17 = new SimpleDateFormat("EEEEE d MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf18 = new SimpleDateFormat("EEEEE d MM yyyy", Locale.getDefault());
        SimpleDateFormat sdf19 = new SimpleDateFormat("EEE, d MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf20 = new SimpleDateFormat("EEE, d MM yyyy", Locale.getDefault());
        SimpleDateFormat sdf21 = new SimpleDateFormat("EEE d MMMMM yyyy", Locale.getDefault());
        SimpleDateFormat sdf22 = new SimpleDateFormat("EEE d MM yyyy", Locale.getDefault());
        sdf.setLenient(false);
        res += parseData(sdf, date);
        res += parseData(sdf2, date);
        res += parseData(sdf3, date);
        res += parseData(sdf4, date);
        res += parseData(sdf5, date);
        res += parseData(sdf6, date);
        res += parseData(sdf7, date);
        res += parseData(sdf8, date);
        res += parseData(sdf9, date);
        res += parseData(sdf10, date);
        res += parseData(sdf11, date);
        res += parseData(sdf12, date);
        res += parseData(sdf13, date);
        res += parseData(sdf14, date);
        res += parseData(sdf15, date);
        res += parseData(sdf16, date);
        res += parseData(sdf17, date);
        res += parseData(sdf18, date);
        res += parseData(sdf19, date);
        res += parseData(sdf20, date);
        res += parseData(sdf21, date);
        res += parseData(sdf22, date);

        return res > 0;
    }

    private static int parseData(SimpleDateFormat sdf, String date) {
        try {
            sdf.parse(date);
            validSDF = (SimpleDateFormat) sdf.clone();
            return 1;
        } catch (ParseException pe) {
            return 0;
        }
    }

    public static long getTimeInMillis(String time) {
        Date dateF = null;
        if(time == null) {
            return 0;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            dateF = sdf.parse(time);
            Log.d("TEST", dateF.getTime() + "");
            return dateF.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDateInMillis(String date) {
        Date dateF = null;
        if(date == null) {
            return 0;
        }
        try {
            dateF = validSDF.parse(date);
            return dateF.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
