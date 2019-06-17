package com.johnsorhannus.divideandconquer;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MainTaskSpinnerAdapter extends ArrayAdapter<MainTask> {
    private List<MainTask> mainTasks;

    public MainTaskSpinnerAdapter(Context context, List<MainTask> mainTasks) {
        super(context, 0, mainTasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    public void getMainTasks(List<MainTask> mainTasks) {
        this.mainTasks = mainTasks;

        notifyDataSetChanged();
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) { //if the view that we want to recycle does not exist, inflate the convertView layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.maintask_spinner_layout, parent, false);
        }

        TextView circle = convertView.findViewById(R.id.maintask_color);
        TextView mainTaskName = convertView.findViewById(R.id.maintask_name);

        MainTask currentItem = getItem(position);

        //circle = findViewById(R.id.maintask_color);
        //circle

        if (currentItem != null) {
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            drawable.getPaint().setColor(currentItem.getColor() + 0xFF000000);
            circle.setBackground(drawable);
        }

        mainTaskName.setText(currentItem.getName());

        return convertView;
    }
}
