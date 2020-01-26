package com.johnsorhannus.divideandconquer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSubTasksForMainTaskActivity extends AppCompatActivity {

    //XML Components
    TextView textViewMainTaskName;
    TextView textViewDueDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainTask mainTask = (MainTask)getIntent().getSerializableExtra("mainTask");
        setContentView(R.layout.view_maintask);

        //Display toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set title of toolbar
        setTitle(R.string.view_maintask);

        //Get XML Components
        textViewMainTaskName = findViewById(R.id.vmtFrag_main_task_name);
        textViewDueDate = findViewById(R.id.vmtFrag_due_date);

        //Set XML Components
        textViewMainTaskName.setText(mainTask.getName());

    }
}
