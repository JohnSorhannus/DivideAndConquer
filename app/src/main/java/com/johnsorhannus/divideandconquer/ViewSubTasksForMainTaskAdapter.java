package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.support.v4.app.FragmentActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.johnsorhannus.divideandconquer.viewmodels.SubTaskViewModel;
import com.johnsorhannus.divideandconquer.viewmodels.ViewSubTasksForMainTaskViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViewSubTasksForMainTaskAdapter extends ListAdapter<SubTask, ViewSubTasksForMainTaskAdapter.ViewHolder> {

    private Context context;
    private ViewSubTasksForMainTaskViewModel viewModel;
    private MainTask mainTask;

    public ViewSubTasksForMainTaskAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final String TAG = "ViewSTForMTAdapter";

    private static final DiffUtil.ItemCallback<SubTask> DIFF_CALLBACK = SubTaskDiffCallback.getSubTaskDiffCallback();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_maintask_st_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SubTask subTask = getItem(i);
        //MainTask mainTask = findMainTask(subTask);
        //Log.d(TAG, "onBindViewHolder: " + mainTask.getName() + " " + mainTask.isOverdue());
        Log.d(TAG, "onBindViewHolder: CALLED");
        //Log.d(TAG, "onBindViewHolder TESTINGTESTING: pos: " + i + " name: " + subTask.getName() + " Is overdue: " + subTask.isOverdue());
        //viewHolder.subTaskName.setText(subTask.getName());
        viewHolder.subTaskName.setText(subTask.getName());

        Calendar dueDate = subTask.getDueDate();
        //Log.d(Constraints.TAG, "onBindViewHolder: " + dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        viewHolder.dueDate.setText(context.getResources().getString(R.string.due_date, dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()), dueDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()), dueDate.get(Calendar.DAY_OF_MONTH), dueDate.get(Calendar.YEAR)));

        //Log.d(TAG, "onBindViewHolder: " + subTask.getName() + " isOverdue = " + subTask.isOverdue());

        /* SOLVED ISSUE WITH OTHER CHECKBOXES BEING CHECKED -- PROBLEM WAS CIRCULAR CALLS. https://stackoverflow.com/questions/27070220/android-recyclerview-notifydatasetchanged-illegalstateexception/37305564#37305564  */
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        //check of sub task if completed
        if (subTask.isCompleted()) {
            viewHolder.checkBox.setChecked(true);
            //notifyItemChanged(i);
            //Log.d(TAG, "onBindViewHolder: " + subTask.getName() + " set to checked");
        } else {
            viewHolder.checkBox.setChecked(false);
            //notifyItemChanged(i);
            //Log.d(TAG, "onBindViewHolder: " + subTask.getName() + " set to unchecked");
        }

        if (subTask.isOverdue()) {
            viewHolder.card.setCardBackgroundColor(context.getColor(R.color.red));
            viewHolder.subTaskName.setTextColor(context.getColor(R.color.colorAccent));
            viewHolder.dueDate.setTextColor(context.getColor(R.color.colorAccent));
        } else {
            viewHolder.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            viewHolder.subTaskName.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
            viewHolder.dueDate.setTextColor(context.getColor(R.color.main_task_text_color));
        }

        /*
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: called for subTask " + subTask.getName() + " isChecked = " + isChecked);
                viewModel = ViewModelProviders.of((FragmentActivity) context).get(ViewSubTasksForMainTaskViewModel.class);
                if (isChecked) {
                    //if overdue, change card to white

                    if (subTask.isOverdue()) {
                        viewHolder.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                        viewHolder.subTaskName.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                        viewHolder.dueDate.setTextColor(context.getColor(R.color.main_task_text_color));
                    }

                    subTask.setCompleted(true);
                    viewModel.updateSubTask(subTask);
                } else {
                    subTask.setCompleted(false);
                    //if overdue, set color back to red

                    if (subTask.isOverdue()) {
                        viewHolder.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                        viewHolder.subTaskName.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                        viewHolder.dueDate.setTextColor(context.getColor(R.color.main_task_text_color));
                    }

                    viewModel.updateSubTask(subTask);
                }
            }
        });*/

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubTask newSubTask = new SubTask(subTask);
                viewModel = ViewModelProviders.of((FragmentActivity) context).get(ViewSubTasksForMainTaskViewModel.class);
                if (((CompoundButton) v).isChecked()) {
                    newSubTask.setCompleted(true);
                } else {
                    newSubTask.setCompleted(false);
                }

                viewModel.updateSubTask(newSubTask);
            }
        });

    }

    public void setMainTask(MainTask mainTask) {
        this.mainTask = mainTask;
    }

    public SubTask getSubTaskAt(int position) {
        return getItem(position);
    }

    /*
    @Override
    public void submitList(@Nullable List<SubTask> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }
    */

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subTaskName;
        TextView dueDate;
        CheckBox checkBox;
        CardView card;
        //ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subTaskName = itemView.findViewById(R.id.vmtCard_sub_task_name);
            dueDate = itemView.findViewById(R.id.vmtCard_date);
            checkBox = itemView.findViewById(R.id.vmtCard_checkBox);
            card = itemView.findViewById(R.id.parent_layout);
        }
    }
}
