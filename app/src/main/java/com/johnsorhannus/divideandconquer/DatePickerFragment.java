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

    //to pass MainTask to fragment in order to set a max date on picker
    public static DatePickerFragment newInstance(MainTask mainTask) {
        
        Bundle args = new Bundle();
        args.putLong(MAIN_TASK_DUE_DATE, mainTask.getDueDate().getTimeInMillis());
        
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar defaultDate = Calendar.getInstance();
        int year = defaultDate.get(Calendar.YEAR);
        int month = defaultDate.get(Calendar.MONTH);
        int day = defaultDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        try {
            dialog.getDatePicker().setMaxDate(getArguments().getLong(MAIN_TASK_DUE_DATE));
        } catch (NullPointerException e) {
            Log.d(TAG, "onCreateDialog: No main task");
        }

        //dialog.getDatePicker().setMaxDate(getArguments().getLong(MAIN_TASK_DUE_DATE));

        return dialog;
    }
}
