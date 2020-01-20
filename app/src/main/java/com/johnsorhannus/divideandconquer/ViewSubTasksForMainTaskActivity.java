package com.johnsorhannus.divideandconquer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ViewSubTasksForMainTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getExtras().getString("Name");
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
    }
}
