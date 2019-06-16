package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.johnsorhannus.divideandconquer.viewmodels.AddSubTaskViewModel;

public class AddSubTaskActivity extends AppCompatActivity {
    private AddSubTaskViewModel viewModel;

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
