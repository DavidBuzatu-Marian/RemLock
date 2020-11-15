package com.davidmarian_buzatu.remlock.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Parser {


    public static boolean isValidTime(String time) {
        return time.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
    }

    // consider formats like Friday, 13 November 2020
    public static boolean isValidDate(String date) {
        int res = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setLenient(false);
        res ^= parseData(sdf, date);
        return res == 1;
    }

    private static int parseData(SimpleDateFormat sdf, String date) {
        try {
            sdf.parse(date);
            return 1;
        } catch (ParseException pe) {
            return 0;
        }
    }
}
