package com.johnsorhannus.divideandconquer;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.johnsorhannus.divideandconquer.viewmodels.ViewSubTasksForMainTaskViewModel;

import java.util.Calendar;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class ViewSubTasksForMainTaskActivity extends AppCompatActivity {
    //Backend components
    private RecyclerView recyclerView;
    private ViewSubTasksForMainTaskViewModel viewModel;
    //add adapter


    //XML Components
    TextView textViewMainTaskName;
    TextView textViewDueDate;
    TextView circle;

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

        //Set Recycler View
        recyclerView = findViewById(R.id.vmt_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Set adapter

        //Get XML Components
        textViewMainTaskName = findViewById(R.id.vmt_main_task_name);
        textViewDueDate = findViewById(R.id.vmt_due_date);
        circle = findViewById(R.id.vmt_circle);

        //Set XML Components
        textViewMainTaskName.setText(mainTask.getName());

        Calendar dueDate = mainTask.getDueDate();
        Log.d(TAG, "onBindViewHolder: " + dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        textViewDueDate.setText(getResources().getString(R.string.due_date, dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()), dueDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()), dueDate.get(Calendar.DAY_OF_MONTH), dueDate.get(Calendar.YEAR)));

        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(mainTask.getColor() + 0xFF000000);
        circle.setBackground(drawable);

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
}
