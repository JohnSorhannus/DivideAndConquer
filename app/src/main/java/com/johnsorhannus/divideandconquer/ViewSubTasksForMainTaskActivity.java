package com.johnsorhannus.divideandconquer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class ViewSubTasksForMainTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getExtras().getString("Name");
        setContentView(R.layout.view_maintask);

        //Display toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set title of toolbar
        setTitle(R.string.view_maintask);

        //Toast.makeText(this, name, Toast.LENGTH_LONG).show();
    }
}
