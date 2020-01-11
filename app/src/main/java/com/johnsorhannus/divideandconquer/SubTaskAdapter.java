package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.johnsorhannus.divideandconquer.room.AppRepository;
import com.johnsorhannus.divideandconquer.viewmodels.SubTaskViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {
    private static final String TAG = "SubTaskAdapter";

    private List<SubTask> subTasks = new ArrayList<>();
    private List<MainTask> mainTasks = new ArrayList<>();
    private Context context;
    private SubTaskViewModel subTaskViewModel;
    //is this smart?
    //private AppRepository repository;
    //should there be an array list for main tasks here?

    /*public SubTaskAdapter(ArrayList<SubTask> subTasks, AppRepository repository) {
        this.subTasks = subTasks;
        this.repository = repository;
    }*/

    public SubTaskAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        //testing --- if block = new code
        /*try {
            MainTask mainTask = repository.retrieveMainTask(subTasks.get(i).getId()).getValue();
            viewHolder.subTaskName.setText(subTasks.get(i).getName());
            viewHolder.mainTaskName.setText(mainTask.getName());
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            drawable.getPaint().setColor(mainTask.getColor() + 0xFF000000); //FIRST TWO DIGITS MUST BE FF for alpha (fully opaque)
            viewHolder.circle.setBackground(drawable);
        } catch (NullPointerException e) {
            Log.d(TAG, "onBindViewHolder: Null pointer " + e.getMessage());
        }*/

        SubTask subTask = subTasks.get(i);
        MainTask mainTask = findMainTask(subTask);
        Log.d(TAG, "onBindViewHolder: " + mainTask.getName());
        Log.d(TAG, "onBindViewHolder: " + subTask.getName());
        viewHolder.subTaskName.setText(subTask.getName());
        viewHolder.mainTaskName.setText(mainTask.getName());
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(mainTask.getColor() + 0xFF000000);
        viewHolder.circle.setBackground(drawable);

        if (subTask.isOverdue()) {
            viewHolder.card.setCardBackgroundColor(context.getResources().getColor(R.color.red));
            viewHolder.subTaskName.setTextColor(context.getResources().getColor(R.color.colorAccent));
            viewHolder.mainTaskName.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }

        //check of sub task if completed
        if (subTask.isCompleted()) {
            viewHolder.checkBox.setChecked(true);
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subTaskViewModel = ViewModelProviders.of((FragmentActivity) context).get(SubTaskViewModel.class);
                if (isChecked) {
                    subTask.setCompleted(true);
                    subTaskViewModel.updateSubTask(subTask);
                    notifyDataSetChanged(); //if an overdue sub task has been checked, this function remove it from screen if called
                } else {
                    subTask.setCompleted(false);
                    subTaskViewModel.updateSubTask(subTask);
                }
                Log.d(TAG, "onCheckedChanged: " + subTask.getName() + " IsCompleted: " + subTask.isCompleted());
            }
        });

        /* if (viewHolder.checkBox.isChecked()) {
            subTask.setCompleted(true);
        } else {
            subTask.setCompleted(false);
        }*/

        /*Iterator<MainTask> iterator = mainTasks.iterator();
        while (iterator.hasNext()) {
            MainTask mainTask = iterator.next();
            if (mainTask.getId() == subTasks.get(i).getMainTaskId()) {
                viewHolder.mainTaskName.setText(mainTask.getName());
            }
        }*/
        

    }



    @Override
    public int getItemCount() {
        return subTasks.size();
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;

        //change later
        notifyDataSetChanged();
    }

    public void setMainTasks(List<MainTask> mainTasks) {
        this.mainTasks = mainTasks;

        //change later
        notifyDataSetChanged();
    }

    private MainTask findMainTask(SubTask subTask) {
        Iterator<MainTask> iterator = mainTasks.iterator();
        while (iterator.hasNext()) {
            MainTask mainTask = iterator.next();
            if (mainTask.getId() == subTask.getMainTaskId()) {
                return mainTask;
            }
        }

        return null;
    }

    public SubTask getSubTaskAt(int position) {
        return subTasks.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subTaskName;
        TextView mainTaskName;
        TextView circle; //
        CheckBox checkBox;
        CardView card;
        //ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subTaskName = itemView.findViewById(R.id.stFrag_sub_task_name);
            mainTaskName = itemView.findViewById(R.id.stFrag_main_task_name);
            circle = itemView.findViewById(R.id.stFrag_circle); //
            checkBox = itemView.findViewById(R.id.stFrag_checkBox);
            card = itemView.findViewById(R.id.parent_layout);
            //parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView date;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
        }
    }

}
