package com.johnsorhannus.divideandconquer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class DatePickerFragment extends DialogFragment {

    private static final String MAIN_TASK_DUE_DATE = "MAIN_TASK";
    private static final String SUB_TASK_DUE_DATE = "SUB_TASK";
    private static final String MAX_SUB_TASK_DUE_DATE = "MAX_DUE_DATE";

    private static final String CHOSEN_DATE = "CHOSEN_DATE";

    //to pass MainTask to fragment in order to set a max date on picker
    public static DatePickerFragment newInstance(SubTask subTask, MainTask mainTask, long maxDueDate, Calendar chosenDate) {
        Bundle args = new Bundle();

        /*
        try {
            args.putLong(MAIN_TASK_DUE_DATE, mainTask.getDueDate().getTimeInMillis());
        } catch (NullPointerException e) {

        }

        try {
            args.putLong(MAX_SUB_TASK_DUE_DATE, maxDueDate);
        } catch (NullPointerException e) {

        }

        try {
            args.putLong(SUB_TASK_DUE_DATE, subTask.getDueDate().getTimeInMillis());
        }*/

        if (subTask != null) {
            args.putLong(SUB_TASK_DUE_DATE, subTask.getDueDate().getTimeInMillis());
        } else {
            args.putLong(SUB_TASK_DUE_DATE, 0);
        }

        if (mainTask != null) {
            args.putLong(MAIN_TASK_DUE_DATE, mainTask.getDueDate().getTimeInMillis());
        } else {
            args.putLong(MAIN_TASK_DUE_DATE, 0);
        }

        if (maxDueDate != 0) {
            args.putLong(MAX_SUB_TASK_DUE_DATE, maxDueDate);
        } else {
            args.putLong(MAX_SUB_TASK_DUE_DATE, 0);
        }

        args.putLong(CHOSEN_DATE, chosenDate.getTimeInMillis());

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //default date
        //new -- today
        //edit -- date of sub task/main task
        long subTaskDueDate = getArguments().getLong(SUB_TASK_DUE_DATE);
        long mainTaskDueDate = getArguments().getLong(MAIN_TASK_DUE_DATE);
        long maxSubTaskDueDate = getArguments().getLong(MAX_SUB_TASK_DUE_DATE);
        long chosenDate = getArguments().getLong(CHOSEN_DATE);

        //DEFAULT DATE
        int year;
        int month;
        int day;

        boolean editSubTask = subTaskDueDate != 0 && mainTaskDueDate != 0 && maxSubTaskDueDate == 0;
        boolean editMainTask = subTaskDueDate == 0 && mainTaskDueDate != 0 && maxSubTaskDueDate != 0;
        boolean newSubTask = subTaskDueDate == 0 && mainTaskDueDate != 0 && maxSubTaskDueDate == 0;


        //SET DEFAULT DATE
        Calendar defaultDate = Calendar.getInstance();
        if (editSubTask && subTaskDueDate == chosenDate) {
            defaultDate.setTimeInMillis(subTaskDueDate);
        } else if (editMainTask && mainTaskDueDate == chosenDate) {
            defaultDate.setTimeInMillis(mainTaskDueDate);
        } else { //NEW SUBTASK OR MAINTASK OR USER CHANGED DATE SELECTED IN CALENDAR DIALOG
            defaultDate.setTimeInMillis(chosenDate);
        }

        //SET DEFAULT DATE VALUES
        year = defaultDate.get(Calendar.YEAR);
        month = defaultDate.get(Calendar.MONTH);
        day = defaultDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);

        //SET MIN DATE
        if (editMainTask) {
            dialog.getDatePicker().setMinDate(maxSubTaskDueDate);
        }

        //SET MAX DATE
        if (newSubTask || editSubTask) {
            dialog.getDatePicker().setMaxDate(mainTaskDueDate);
        }

        return dialog;
    }
}
