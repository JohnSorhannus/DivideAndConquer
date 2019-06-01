package com.johnsorhannus.divideandconquer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "subTask",
        foreignKeys = @ForeignKey(
                entity = MainTask.class,
                parentColumns = "id",
                childColumns = "mainTaskId",
                onDelete = CASCADE),
        indices = {@Index("mainTaskId")})
public class SubTask extends Task {

    private static final String TAG = "SubTask";
    @NonNull
    @ColumnInfo(name = "completed")
    @TypeConverters(BooleanConverter.class)
    private boolean completed;

    @ColumnInfo(name = "mainTaskId")
    //@TypeConverters(MainTaskConverter.class)
    private int mainTaskId;
    //private MainTask mainTask;

    public SubTask(String name, Calendar dueDate, int mainTaskId) {
        super(name, dueDate);
        completed = false;
        this.mainTaskId = mainTaskId;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    public int getMainTaskId() {
        return mainTaskId;
    }

    //required by compiler
    public void setCompleted(boolean completed) {
        this.completed = completed;
        Log.d(TAG, "setCompleted: " + super.getName() + ": complete = " + String.valueOf(completed));
    }

    @Override
    public String toString() {
        return "SubTask[" + super.toString() + ", completed = " + completed + "]";
    }
}
