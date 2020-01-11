package com.johnsorhannus.divideandconquer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static android.support.constraint.Constraints.TAG;

@Entity(tableName = "mainTask")
public class MainTask extends Task {

    @NonNull
    @ColumnInfo(name = "color")
    private int color;

    @ColumnInfo(name = "percentCompleted")
    private int percentCompleted;

    @Ignore
    private ArrayList<SubTask> subTasks;

    public MainTask(String name, int color, Calendar dueDate) {
        super(name, dueDate);
        this.color = color;
        subTasks = new ArrayList<>();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int newColor) {
        color = newColor;
    }

    //Required by room
    public void setPercentCompleted(int percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public ArrayList<SubTask> sortSubTasks() {
        ArrayList<SubTask> sortedSubTasks = subTasks;
        sortedSubTasks.ensureCapacity(subTasks.size());

        //sortedSubTasks.sort(new TaskComparator());
        Collections.sort(sortedSubTasks, new TaskComparator());
        return sortedSubTasks;
    }

    public boolean addSubTask(SubTask task) {
        return subTasks.add(task);
    }

    public boolean removeSubTask(SubTask task) {
        return subTasks.remove(task);
    }

    public int numTasks() {
        Log.d(TAG, "numTasks: = " + subTasks.size());
        return subTasks.size();
    }

    public int numCompletedTasks() {
        int numCompTasks = 0;

        for (SubTask t: subTasks) {
            if (t.isCompleted()) {
                numCompTasks++;
            }
        }

        return numCompTasks;
    }

    public int percentCompleted() {
        //will throw exception if numTasks is zero
        //Double percent = (numCompletedTasks() / (double) numTasks()) * 100;
        //return percent.intValue();
        return percentCompleted;
    }

    @Override
    public boolean isCompleted() {
        return (percentCompleted() == 100);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("MainTask[");
        builder.append(super.toString());
        builder.append(", color = " + color);
        builder.append(", percent completed = " + percentCompleted() + "%]");

        if (numTasks() != 0) {
            builder.append('\n');
            ArrayList<SubTask> subs = sortSubTasks();
            for (SubTask t : subs) {
                builder.append('\t' + t.toString());
                builder.append('\n');
            }
        }

        String completedStr = builder.toString();
        return completedStr;
    }
}
