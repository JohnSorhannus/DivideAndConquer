package com.johnsorhannus.divideandconquer;


import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
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


public class MainTaskAdapter extends RecyclerView.Adapter<MainTaskAdapter.ViewHolder> {
    private List<MainTask> mainTasks = new ArrayList<>();
    private Context context;

    public MainTaskAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintask_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder parent, int i) {


        MainTask mainTask = mainTasks.get(i);

        //Map<String, Integer> dateDisplayNames = mainTask.getDueDate().getDisplayNames(Calendar.MONTH, Calendar.LONG, Locale.US);
        //dateDisplayNames.
        parent.mainTaskName.setText(mainTask.getName());
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(mainTask.getColor() + 0xFF000000);
        parent.circle.setBackground(drawable);
        parent.percentageComplete.setText(context.getResources().getString(R.string.percent, mainTask.percentCompleted()));
        Calendar dueDate = mainTask.getDueDate();
        //String day_str = dueDate.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.getDefault());
        //int day = Integer.parseInt()
        Log.d(TAG, "onBindViewHolder: " + dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        parent.dueDate.setText(context.getResources().getString(R.string.due_date, dueDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()), dueDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()), dueDate.get(Calendar.DAY_OF_MONTH), dueDate.get(Calendar.YEAR)));
    }

    @Override
    public int getItemCount() {
        return mainTasks.size();
    }

    public void setMainTasks(List<MainTask> mainTasks) {
        this.mainTasks = mainTasks;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView circle;
        private TextView mainTaskName;
        private TextView dueDate;
        private TextView percentageComplete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circle = itemView.findViewById(R.id.mtFrag_circle);
            mainTaskName = itemView.findViewById(R.id.mtFrag_main_task_name);
            dueDate = itemView.findViewById(R.id.mtFrag_due_date);
            percentageComplete = itemView.findViewById(R.id.mtFrag_percentage);
        }
    }
}
