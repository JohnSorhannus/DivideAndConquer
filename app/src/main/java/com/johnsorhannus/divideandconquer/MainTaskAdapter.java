package com.johnsorhannus.divideandconquer;


import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static java.util.Calendar.MONTH;


public class MainTaskAdapter extends ListAdapter<MainTask, MainTaskAdapter.ViewHolder> {
    //private List<MainTask> mainTasks = new ArrayList<>();
    private Context context;
    private OnMainTaskListener onMainTaskListener;


    public MainTaskAdapter(Context context, OnMainTaskListener onMainTaskListener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.onMainTaskListener = onMainTaskListener;
    }

    private static final DiffUtil.ItemCallback<MainTask> DIFF_CALLBACK = new DiffUtil.ItemCallback<MainTask>() {
        @Override
        public boolean areItemsTheSame(@NonNull MainTask oldTask, @NonNull MainTask newTask) {
            return oldTask.getId() == newTask.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MainTask oldTask, @NonNull MainTask newTask) {
            return oldTask.getName().equals(newTask.getName()) &&
                    oldTask.getDueDate().equals(newTask.getDueDate()) &&
                    oldTask.getColor() == newTask.getColor() &&
                    oldTask.getPercentCompleted() == newTask.getPercentCompleted();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintask_list_layout, parent, false);
        return new ViewHolder(view, onMainTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder parent, int i) {
        MainTask mainTask = getItem(i);

        //Map<String, Integer> dateDisplayNames = mainTask.getDueDate().getDisplayNames(Calendar.MONTH, Calendar.LONG, Locale.US);
        //dateDisplayNames.
        parent.mainTaskName.setText(mainTask.getName());
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(mainTask.getColor() + 0xFF000000);
        parent.circle.setBackground(drawable);
        parent.percentageComplete.setText(context.getResources().getString(R.string.percent, mainTask.getPercentCompleted()));
        Calendar dueDate = mainTask.getDueDate();
        //String day_str = dueDate.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.getDefault());
        //int day = Integer.parseInt()
        Log.d(TAG, "onBindViewHolder: " + dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        parent.dueDate.setText(context.getResources().getString(R.string.due_date, dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()), dueDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()), dueDate.get(Calendar.DAY_OF_MONTH), dueDate.get(Calendar.YEAR)));

        //Set colors if overdue
        if (mainTask.isOverdue()) {
            parent.card.setCardBackgroundColor(context.getColor(R.color.red));
            parent.mainTaskName.setTextColor(context.getColor(R.color.colorAccent));
            parent.dueDate.setTextColor(context.getColor(R.color.colorAccent));
            parent.percentageComplete.setTextColor(context.getColor(R.color.colorAccent));
            parent.completed.setTextColor(context.getColor(R.color.colorAccent));
        } else {
            parent.card.setCardBackgroundColor(context.getColor(R.color.colorAccent));
            parent.mainTaskName.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
            parent.dueDate.setTextColor(context.getColor(R.color.main_task_text_color));
            parent.percentageComplete.setTextColor(context.getColor(R.color.green));
            parent.completed.setTextColor(context.getColor(R.color.green));
        }
    }

    /*
    @Override
    public int getItemCount() {
        return mainTasks.size();
    }

    public void setMainTasks(List<MainTask> mainTasks) {
        this.mainTasks = mainTasks;

        notifyDataSetChanged();
    }
    */

    public MainTask getMainTaskAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView circle;
        private TextView mainTaskName;
        private TextView dueDate;
        private TextView percentageComplete;
        private TextView completed;
        private CardView card;
        private OnMainTaskListener onMainTaskListener;

        public ViewHolder(@NonNull View itemView, OnMainTaskListener onMainTaskListener) {
            super(itemView);
            circle = itemView.findViewById(R.id.mtFrag_circle);
            mainTaskName = itemView.findViewById(R.id.mtFrag_main_task_name);
            dueDate = itemView.findViewById(R.id.mtFrag_due_date);
            percentageComplete = itemView.findViewById(R.id.mtFrag_percentage);
            completed = itemView.findViewById(R.id.completed);
            card = itemView.findViewById(R.id.parent_layout);
            this.onMainTaskListener = onMainTaskListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (onMainTaskListener != null && position != RecyclerView.NO_POSITION) {
                onMainTaskListener.onMainTaskClick(getItem(position));
            }
        }
    }

    public interface OnMainTaskListener {
        void onMainTaskClick(MainTask mainTask);
    }
}
