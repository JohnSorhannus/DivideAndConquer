package com.johnsorhannus.divideandconquer;

import com.johnsorhannus.divideandconquer.MainTask;

import java.util.Calendar;

public class DueDateQueryLiterals {
    static public Calendar getCurrentDate() {
        return clearTimeValues();
        //return currentDate.getTimeInMillis();
    }

    static public Calendar getYesterday() {
        Calendar yesterday = clearTimeValues();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        return yesterday;
        //return yesterday.getTimeInMillis();
    }

    static public Calendar getTomorrow() {
        Calendar tomorrow = clearTimeValues();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        return tomorrow;
    }

    static public Calendar clearTimeValues() {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return date;
    }
}
