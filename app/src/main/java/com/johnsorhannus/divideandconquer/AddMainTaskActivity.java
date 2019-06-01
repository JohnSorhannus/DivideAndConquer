package com.johnsorhannus.divideandconquer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.johnsorhannus.divideandconquer.viewmodels.AddMainTaskViewModel;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddMainTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private AddMainTaskViewModel viewModel;
    private TextInputLayout textInputName;
    private LinearLayout buttonColorPicker;
    private TextInputLayout textInputDate;
    private Button buttonAddMainTask;
    private AppCompatSpinner spinner;
    private TextView circle;
    int defaultColor;
    int chosenColor;
    Calendar chosenDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_maintask);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //viewModel = new AddMainTaskViewModel(getApplicationContext());
        chosenDate = null;

        viewModel = ViewModelProviders.of(this).get(AddMainTaskViewModel.class);

        textInputName = findViewById(R.id.add_maintask_name);
        buttonColorPicker = findViewById(R.id.add_maintask_color);
        //textInputDate = findViewById(R.id.add_maintask_date);
        buttonAddMainTask = findViewById(R.id.add_maintask_button);

        //cirlce
        circle = findViewById(R.id.add_maintask_circle);
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.colorPrimary));
        circle.setBackground(drawable);

        //spinner
        spinner = findViewById(R.id.add_maintask_date_picker_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.date_picker, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        chosenDate = DueDateQueryLiterals.getCurrentDate();
                        ((TextView) parent.getChildAt(0)).setText(DateFormat.getDateInstance().format(chosenDate.getTime()));
                        break;
                    case 1:
                        chosenDate = DueDateQueryLiterals.getTomorrow();
                        ((TextView) parent.getChildAt(0)).setText(DateFormat.getDateInstance().format(chosenDate.getTime()));
                        break;
                    case 2:
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.show(getSupportFragmentManager(), "Date Picker");
                        //while (chosenDate == null);
                        //((TextView) parent.getChildAt(0)).setText(DateFormat.getDateInstance().format(chosenDate.getTime()));
                        break;
                    default:
                        chosenDate = DueDateQueryLiterals.getCurrentDate();
                        ((TextView) parent.getChildAt(0)).setText(DateFormat.getDateInstance().format(chosenDate.getTime()));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        defaultColor = ContextCompat.getColor(AddMainTaskActivity.this, R.color.colorPrimary);
        chosenColor = defaultColor;

        setTitle(R.string.add_maintask);

        buttonColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        /*textInputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });*/

        buttonAddMainTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validInput()) {
                    //viewModel.insertMainTask();
                    addMainTask();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_task, menu);
        return true;
    }

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
        String name = textInputName.getEditText().getText().toString().trim();

        viewModel.insertMainTask(new MainTask(name, chosenColor, chosenDate));
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar date = DueDateQueryLiterals.clearTimeValues();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //textInputDate.setHint(DateFormat.getDateInstance().format(date.getTime()));
        //spinner.setPrompt(DateFormat.getDateInstance().format(date.getTime()));
        //(TextView )
        //spinner.getAdapter().getView(0, TextView, ).getParent().
        chosenDate = date;


    }

    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                chosenColor = color;

                ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
                drawable.getPaint().setColor(chosenColor);
                circle.setBackground(drawable);
                /*Drawable circle = getResources().getDrawable(R.drawable.circle);
                circle = DrawableCompat.wrap(circle);
                DrawableCompat.setTint(circle, chosenColor);
                buttonColorPicker.setCompoundDrawables(circle, null, null, null);*/
                //buttonColorPicker.setText(R.string.color_set);
            }
        });
        colorPicker.show();
    }

    private boolean nameIsValid() {
        String name = textInputName.getEditText().getText().toString().trim();

        if (name.isEmpty()) {
            textInputName.setError("Field cannot be empty");
            return false;
        } else {
            textInputName.setError(null);
            textInputName.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validInput() {
        return (nameIsValid());
    }

}
