package com.johnsorhannus.divideandconquer;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class CalendarConverter {

    @TypeConverter
    public static Calendar toCalendar(long milli) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milli);
        return cal;
    }

    @TypeConverter
    public static long toLong(Calendar date) {
        return date.getTimeInMillis();
    }
}
