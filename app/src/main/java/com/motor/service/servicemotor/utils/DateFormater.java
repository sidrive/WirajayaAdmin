package com.motor.service.servicemotor.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ikun on 02/01/18.
 */

public class DateFormater {

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
