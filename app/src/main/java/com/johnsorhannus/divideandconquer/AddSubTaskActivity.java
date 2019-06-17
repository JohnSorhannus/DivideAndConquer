package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.johnsorhannus.divideandconquer.viewmodels.AddSubTaskViewModel;

import java.util.Calendar;
import java.util.List;

public class AddSubTaskActivity extends AppCompatActivity {
    private AddSubTaskViewModel viewModel;

    //XML components
    private EditText textInputName;
    //private LinearLayout buttonMainTaskPicker;
    //private TextView circle;
    private LinearLayout buttonDueDatePicker;
    private TextView textViewDueDate;
    private Button buttonAddSubTask;
    private Spinner mainTaskSpinner;

    //XML components
    //int circleColor;
    Calendar chosenDate;
    MainTask chosenMainTask;


    private MainTaskSpinnerAdapter spinnerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subtask);

        viewModel = ViewModelProviders.of(this).get(AddSubTaskViewModel.class);

        //Display toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set title of toolbar
        setTitle(R.string.add_subtask);

        //Set Values
        //circleColor = getResources().getColor(R.color.colorPrimary);

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

        viewModel.retrieveActiveMainTasks().observe(this, new Observer<List<MainTask>>() {
            @Override
            public void onChanged(@Nullable List<MainTask> mainTasks) {
                //spinnerAdapter.getMainTasks(mainTasks);
                spinnerAdapter = new MainTaskSpinnerAdapter(getApplicationContext(), mainTasks);
                mainTaskSpinner.setAdapter(spinnerAdapter);
            }
        });

        mainTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenMainTask = (MainTask) parent.getItemAtPosition(position);
                Toast.makeText(AddSubTaskActivity.this, chosenMainTask.getName() + " selected", Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonAddSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add sub task
            }
        });
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
}
