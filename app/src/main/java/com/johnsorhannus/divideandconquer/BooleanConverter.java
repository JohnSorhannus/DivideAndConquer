package com.johnsorhannus.divideandconquer;

import android.arch.persistence.room.TypeConverter;

public class BooleanConverter {

    @TypeConverter
    public static boolean toBoolean(int value) {
        return value == 1;
    }

    @TypeConverter
    public static int toInt(boolean value) {
        return value ? 1 : 0;
    }
}
