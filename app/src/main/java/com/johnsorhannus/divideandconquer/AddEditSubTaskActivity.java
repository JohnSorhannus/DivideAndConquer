package com.johnsorhannus.divideandconquer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.johnsorhannus.divideandconquer.viewmodels.AddSubTaskViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class AddEditSubTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddEditSubTaskActivity";
    private static final String SUB_TASK = "subTask";

    private AddSubTaskViewModel viewModel;

    //XML components
    private EditText textInputName;
    //private LinearLayout buttonMainTaskPicker;
    //private TextView circle;
    private LinearLayout buttonDueDatePicker;
    private TextView textViewDueDate;
    private Button buttonAddSubTask;
    private Spinner mainTaskSpinner;

    //Data
    private Calendar chosenDate;
    private MainTask chosenMainTask;
    private int mainTaskId;
    private SubTask subTask;

    //Intent
    private Intent intent = null;

    private MainTaskSpinnerAdapter spinnerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subtask);

        viewModel = ViewModelProviders.of(this).get(AddSubTaskViewModel.class);

        //Display toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set Values

        //Set UI components
        textInputName = findViewById(R.id.add_subtask_name);
        //buttonMainTaskPicker = findViewById(R.id.select_maintask);
        buttonDueDatePicker = findViewById(R.id.add_subtask_due_date);
        textViewDueDate = findViewById(R.id.add_subtask_selected_date);
        buttonAddSubTask = findViewById(R.id.add_subtask_button);
        mainTaskSpinner = findViewById(R.id.maintask_spinner);
        mainTaskSpinner.setAdapter(spinnerAdapter);




        //set adapter
        //spinnerAdapter = new MainTaskSpinnerAdapter(this);
        //mainTaskSpinner.setAdapter(spinnerAdapter);

        //circle
        /*
        circle = findViewById(R.id.maintask_color);
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(circleColor);
        circle.setBackground(drawable);
        */

        /*buttonMainTaskPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open up a dialog box a spinner menu
            }
        });*/

        //If Edit, get intent and fill in fields
        intent = getIntent();
        if (intent.hasExtra(SUB_TASK)) {
            setTitle(R.string.edit_subtask);

            subTask = (SubTask)intent.getSerializableExtra(SUB_TASK);
            textInputName.setText(subTask.getName());
            mainTaskId = subTask.getMainTaskId();
            chosenDate = subTask.getDueDate();
            textViewDueDate.setText(DateFormat.getDateInstance().format(chosenDate.getTime()));
            /*
            int i = 1;
            MainTask currMT = spinnerAdapter.getItem(0);
            while (currMT.getId() != mainTaskId) {
                currMT = spinnerAdapter.getItem(i);
                i++;
            }
            mainTaskSpinner.setSelection(i);

            List<MainTask> list = viewModel.retrieveActiveMainTasks(mainTaskId).getValue();
            //search for position in list where main task ids match
            /*
            int pos;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == mainTaskId) {

                }
            }
            int i = 0;
            if (list != null) {
                while (list.get(i).getId() != mainTaskId) {
                    i++;
                }
            }
            mainTaskSpinner.setSelection(i);*/


            //HANDLE WHAT HAPPENS IF OVERDUE MAINTASK IS SELECTED. WHAT WILL BE IN SPINNER?
            //MAY DECIDE TO HAVE NO SPINNER IF EDIT AND OVERDUE
        } else {
            chosenDate = null;
            mainTaskId = -1;
            subTask = null;
            setTitle(R.string.add_subtask);
        }

        viewModel.retrieveActiveMainTasks(mainTaskId).observe(this, new Observer<List<MainTask>>() {
            @Override
            public void onChanged(@Nullable List<MainTask> mainTasks) {
                //spinnerAdapter.getMainTasks(mainTasks);
                spinnerAdapter = new MainTaskSpinnerAdapter(getApplicationContext(), mainTasks);
                Log.d(TAG, "onChanged: Loaded main task list of size " + mainTasks.size());
                mainTaskSpinner.setAdapter(spinnerAdapter);

                if (intent.hasExtra(SUB_TASK)) {
                    MainTask currMT;
                    int i;
                    for (i = 0; i < spinnerAdapter.getCount(); i++) {
                        currMT = spinnerAdapter.getItem(i);
                        if (currMT.getId() == mainTaskId) {
                            break;
                        }
                    }

                    /*
                    int i = 0;
                    MainTask currMT = spinnerAdapter.getItem(0);
                    while (currMT.getId() != mainTaskId) {
                        currMT = spinnerAdapter.getItem(i);
                        i++;
                    }*/
                    mainTaskSpinner.setSelection(i);
                }
            }
        });



        mainTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenMainTask = (MainTask) parent.getItemAtPosition(position);

                //if the current date chosen is greater than the due date of the main task, set the chosenDate to null
                if (chosenDate != null && (chosenDate.getTimeInMillis() > chosenMainTask.getDueDate().getTimeInMillis())) {
                    Log.d(TAG, "onItemSelected: DATE INVALID. SET TO NULL");
                    chosenDate = null;
                    textViewDueDate.setText(getString(R.string.due_date_add_task));
                }

                Log.d(TAG, "onItemSelected: position = " + position);
                //set chosenDate to null here?
                Toast.makeText(AddEditSubTaskActivity.this, chosenMainTask.getName() + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonAddSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add sub task
                submitSubTask();
            }
        });

        buttonDueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DialogFragment datePicker = new DatePickerFragment(); //need to create a date picker fragment for AddSubTask
                DatePickerFragment datePicker = DatePickerFragment.newInstance(subTask, chosenMainTask, 0); //pass main task to picker to set max date
                datePicker.show(getSupportFragmentManager(), "Date Picker");
                //datePicker.
                //((DatePickerFragment) datePicker).setMaxDate(chosenMainTask);
                //datePicker.getDatePicker
                //datePicker.setMin
            }
        });
    }

    private void submitSubTask() {
        String name = textInputName.getText().toString().trim();

        //if user does not enter in a name
        if (name.length() == 0) {
            name = getString(R.string.no_title);
        }

        //check date
        if (chosenDate == null) {
            new AlertDialog.Builder(AddEditSubTaskActivity.this)
                    .setTitle(getString(R.string.no_due_date_title))
                    .setMessage(getString(R.string.no_due_date_message))
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        } else if (intent.hasExtra("subTask")) {
            //edit sub task
            //Change subtask
            subTask.setName(name);
            subTask.setDueDate(chosenDate);
            subTask.setMainTaskId(chosenMainTask.getId());
            //Copy
            SubTask newSubTask = new SubTask(subTask);
            viewModel.updateSubTask(newSubTask);
            finish();
        } else {
            //add sub task
            viewModel.insertSubTask(new SubTask(name, chosenDate, chosenMainTask.getId()));
            finish();
        }

    }

    //These two functions are the same in both AddSubTask and AddMainTask. Any way to reduce repetitive code?
    //Creates 'X' on toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_task, menu);
        return true;
    }

    //Action when 'X' is hit
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel_action:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //view.set
        Calendar date = DueDateQueryLiterals.clearTimeValues();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        chosenDate = date;

        textViewDueDate.setText(DateFormat.getDateInstance().format(chosenDate.getTime()));
    }


}
