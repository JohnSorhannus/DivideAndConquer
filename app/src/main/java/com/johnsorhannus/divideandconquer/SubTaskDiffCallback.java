package com.johnsorhannus.divideandconquer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;

public class SubTaskDiffCallback {

    private static final String TAG = "SubTaskDiffCallback";

    static public DiffUtil.ItemCallback<SubTask> getSubTaskDiffCallback() {
        return new DiffUtil.ItemCallback<SubTask>() {
            @Override
            public boolean areItemsTheSame(@NonNull SubTask oldTask, @NonNull SubTask newTask) {
                Log.d(TAG, "areItemsTheSame: oldTask name: " + oldTask.getName() + " id: " + oldTask.getId() + " ### newTask name: " + newTask.getName() + " id: " + newTask.getId());
                return oldTask.getId() == newTask.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull SubTask oldTask, @NonNull SubTask newTask) {
                Log.d(TAG, "areContentsTheSame: oldTask name: " + oldTask.getName() + " ### newTask name: " + newTask.getName());
                boolean contentsSame = oldTask.getName().equals(newTask.getName()) &&
                        oldTask.getDueDate().equals(newTask.getDueDate()) &&
                        oldTask.isCompleted() == newTask.isCompleted() &&
                        oldTask.getMainTaskId() == (newTask.getMainTaskId());
                Log.d(TAG, "areContentsTheSame: = " + contentsSame);
                return contentsSame;
            }

            //prevents animation when checking checkbox
            @Nullable
            @Override
            public Object getChangePayload(@NonNull SubTask oldTask, @NonNull SubTask newTask) {
                if (oldTask.isCompleted() != newTask.isCompleted()) {
                    return Boolean.FALSE;
                } else {
                    return null;
                }

                //return Boolean.FALSE;
            }
        };
    }
}
