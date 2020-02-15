package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.johnsorhannus.divideandconquer.viewmodels.ViewSubTasksForMainTaskVMFactory;
import com.johnsorhannus.divideandconquer.viewmodels.ViewSubTasksForMainTaskViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class ViewSubTasksForMainTaskActivity extends AppCompatActivity {
    //Backend components
    private RecyclerView recyclerView;
    private ViewSubTasksForMainTaskViewModel viewModel;
    private ViewSubTasksForMainTaskAdapter adapter;
    //private MainTask mainTask;

    public static final int EDIT_SUBTASK_REQUEST = 1;

    //XML Components
    TextView textViewMainTaskName;
    TextView textViewDueDate;
    TextView circle;
    TextView percentageCompleted;

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
        adapter = new ViewSubTasksForMainTaskAdapter(this);
        recyclerView.setAdapter(adapter);

        //Get XML Components
        textViewMainTaskName = findViewById(R.id.vmt_main_task_name);
        textViewDueDate = findViewById(R.id.vmt_due_date);
        circle = findViewById(R.id.vmt_circle);
        percentageCompleted = findViewById(R.id.vmt_percentage);

        //Set XML Components
        textViewMainTaskName.setText(mainTask.getName());

        Calendar dueDate = mainTask.getDueDate();
        Log.d(TAG, "onBindViewHolder: " + dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        textViewDueDate.setText(getResources().getString(R.string.due_date, dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()), dueDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()), dueDate.get(Calendar.DAY_OF_MONTH), dueDate.get(Calendar.YEAR)));

        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(mainTask.getColor() + 0xFF000000);
        circle.setBackground(drawable);

        percentageCompleted.setText(getResources().getString(R.string.percent, mainTask.getPercentCompleted()));

        viewModel = ViewModelProviders.of(this, new ViewSubTasksForMainTaskVMFactory(this.getApplication(), mainTask.getId())).get(ViewSubTasksForMainTaskViewModel.class);
        //Observe LiveData
        viewModel.getSubTasks().observe(this, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(@Nullable List<SubTask> subTasks) {
                //adapter.submitList(null);
                //adapter.submitList(null);

                //adapter.submitList(null);
                //viewModel.getMainTask().getValue().size();
                adapter.submitList(subTasks);

                //adapter.notifyDataSetChanged();
            }
        });

        viewModel.getMainTask().observe(this, new Observer<MainTask>() {
            @Override
            public void onChanged(@Nullable MainTask mainTask) {
                adapter.setMainTask(mainTask);
                percentageCompleted.setText(getResources().getString(R.string.percent, mainTask.getPercentCompleted()));
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                SubTask deletedSubTask = adapter.getSubTaskAt(viewHolder.getAdapterPosition());
                viewModel.deleteSubTask(deletedSubTask);
                Snackbar.make(findViewById(R.id.view_maintask_linlayout), getResources().getString(R.string.delete_subtask_snackbar) + deletedSubTask.getName() + getResources().getString(R.string.delete_snackbar), Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewModel.insertSubTask(deletedSubTask);
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ViewSubTasksForMainTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SubTask subTask) {
                Intent intent = new Intent(ViewSubTasksForMainTaskActivity.this, AddEditSubTaskActivity.class);
                intent.putExtra("subTask", subTask);
                startActivityForResult(intent, EDIT_SUBTASK_REQUEST);
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
}
