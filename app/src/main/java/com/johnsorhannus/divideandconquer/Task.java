package com.johnsorhannus.divideandconquer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.util.Calendar;

public abstract class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "dueDate")
    @TypeConverters(CalendarConverter.class)
    private Calendar dueDate;

    public Task(int id, @NonNull  String paramName, @NonNull Calendar paramDueDate) {
        /*if (paramName == null || paramDueDate == null) {
            throw new NullPointerException();
        }*/

        this.id = id;
        this.name = paramName;
        this.dueDate = paramDueDate;
    }

    @Ignore
    public Task(@NonNull String paramName, @NonNull Calendar paramDueDate) {
        this.name = paramName;
        this.dueDate = paramDueDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String newName) {
        name = newName;
    }

    public Calendar getDueDate() {
        return (Calendar) dueDate.clone();
    }

    public void setDueDate(Calendar newDueDate) {
        dueDate = newDueDate;
    }

    public abstract boolean isCompleted();

    public boolean isOverdue() {
        Calendar currentDate = Calendar.getInstance();

        boolean dueDatePassed = ((currentDate.get(Calendar.YEAR) == dueDate.get(Calendar.YEAR)) && (currentDate.get(Calendar.DAY_OF_YEAR) > dueDate.get(Calendar.DAY_OF_YEAR))) || (currentDate.get(Calendar.YEAR) > dueDate.get(Calendar.YEAR));

        return (!isCompleted() && dueDatePassed);
    }

    public String toString() {
        return "name = " + name + ", dueDate = " + DateFormat.getDateInstance().format(dueDate.getTime()) + ", overdue = " + isOverdue();
    }

}
