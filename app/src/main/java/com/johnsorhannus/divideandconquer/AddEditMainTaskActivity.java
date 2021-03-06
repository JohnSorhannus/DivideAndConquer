package com.johnsorhannus.divideandconquer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.johnsorhannus.divideandconquer.viewmodels.AddMainTaskViewModel;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddEditMainTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String MAIN_TASK = "mainTask";

    private AddMainTaskViewModel viewModel;

    //XML components
    private EditText textInputName;
    private LinearLayout buttonColorPicker;
    private TextView circle;
    private LinearLayout buttonDueDatePicker;
    private TextView textViewDueDate;
    private Button buttonAddMainTask;

    //DATA values
    private int chosenColor; //defaults to blue
    private Calendar chosenDate; //defaults to week from current date
    private MainTask mainTask;
    private long dueDate = 0;

    //Intent
    private Intent intent = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_maintask);

        //Display toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(AddMainTaskViewModel.class);

        //Set UI components
        textInputName = findViewById(R.id.add_maintask_name);
        buttonColorPicker = findViewById(R.id.add_maintask_color);
        buttonDueDatePicker = findViewById(R.id.add_maintask_due_date);
        buttonAddMainTask = findViewById(R.id.add_maintask_button);

        //due date
        textViewDueDate = findViewById(R.id.add_maintask_selected_date);

        //circle
        circle = findViewById(R.id.add_maintask_circle);
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());

        intent = getIntent();
        if (intent.hasExtra(MAIN_TASK)) {
            setTitle(R.string.edit_maintask);
            buttonAddMainTask.setText(R.string.edit_maintask);
            mainTask = (MainTask)intent.getSerializableExtra(MAIN_TASK);
            textInputName.setText(mainTask.getName());
            chosenColor = mainTask.getColor();
            chosenDate = mainTask.getDueDate();
        } else {
            //Add title to toolbar
            setTitle(R.string.add_maintask);
            buttonAddMainTask.setText(R.string.add_maintask);
            mainTask = null;
            chosenColor = getColor(R.color.colorPrimary);
            chosenDate = DueDateQueryLiterals.getCurrentDate();
        }

        //Set due date text
        textViewDueDate.setText(DateFormat.getDateInstance().format(chosenDate.getTime()));

        //Set circle color
        drawable.getPaint().setColor(chosenColor);
        circle.setBackground(drawable);

        if (mainTask != null) {
            viewModel.retrieveMaxDueDateForMT(mainTask.getId()).observe(this, new Observer<SubTask>() {
                @Override
                public void onChanged(@Nullable SubTask subTask) {
                    if (subTask != null) {
                        dueDate = subTask.getDueDate().getTimeInMillis();
                    }
                }
            });
        }

        buttonColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        buttonAddMainTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMainTask();
            }
        });

        buttonDueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = DatePickerFragment.newInstance(null, mainTask, dueDate, chosenDate);
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

    }

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
                //Add intent to return to MainActivity;
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addMainTask() {
        //String name = textInputName.getEditText().getText().toString().trim();
        String name = textInputName.getText().toString().trim();

        if (name.length() == 0) {
            name = getString(R.string.no_title);
        }

        if (intent.hasExtra(MAIN_TASK)) {
            mainTask.setName(name);
            mainTask.setDueDate(chosenDate);
            mainTask.setColor(chosenColor);
            MainTask newMainTask = new MainTask(mainTask);
            viewModel.updateMainTask(newMainTask);
            finish();
        } else {
            viewModel.insertMainTask(new MainTask(name, chosenColor, chosenDate));
            finish();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar date = DueDateQueryLiterals.clearTimeValues();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        chosenDate = date;

        textViewDueDate.setText(DateFormat.getDateInstance().format(chosenDate.getTime()));
    }

    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, chosenColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                chosenColor = color;

                ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
                drawable.getPaint().setColor(chosenColor);
                circle.setBackground(drawable);
            }
        });
        colorPicker.show();
    }
}
