package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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

import com.johnsorhannus.divideandconquer.room.AppRepository;
import com.johnsorhannus.divideandconquer.viewmodels.SubTaskViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SubTaskAdapter extends ListAdapter<SubTask, SubTaskAdapter.ViewHolder> {
    private static final String TAG = "SubTaskAdapter";

    //private List<SubTask> subTasks = new ArrayList<>();
    private List<MainTask> mainTasks = new ArrayList<>();
    private Context context;
    private SubTaskViewModel subTaskViewModel;

    public SubTaskAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<SubTask> DIFF_CALLBACK = new DiffUtil.ItemCallback<SubTask>() {
        @Override
        public boolean areItemsTheSame(@NonNull SubTask oldTask, @NonNull SubTask newTask) {
            Log.d(TAG, "areItemsTheSame: oldTask name: " + oldTask.getName() + " id: " + oldTask.getId() + " ### newTask name: " + newTask.getName() + " id: " + newTask.getId());
            return oldTask.getId() == newTask.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SubTask oldTask, @NonNull SubTask newTask) {
            Log.d(TAG, "areContentsTheSame: oldTask name: " + oldTask.getName() + " ### newTask name: " + newTask.getName());
            boolean contentsSame = oldTask.getName().equals(newTask.getName()) &&
                    oldTask.getDueDate().equals(newTask.getDueDate()) &&
                    oldTask.isCompleted() == newTask.isCompleted() &&
                    oldTask.getMainTaskId() == (newTask.getMainTaskId());
            Log.d(TAG, "areContentsTheSame: = " + contentsSame);
            return contentsSame;
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull SubTask oldTask, @NonNull SubTask newTask) {
            if (oldTask.isCompleted() != newTask.isCompleted()) {
                return Boolean.FALSE;
            } else {
                return null;
            }

            //return Boolean.FALSE;
        }
    };
    //is this smart?
    //private AppRepository repository;
    //should there be an array list for main tasks here?

    /*public SubTaskAdapter(ArrayList<SubTask> subTasks, AppRepository repository) {
        this.subTasks = subTasks;
        this.repository = repository;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_list_layout, parent, false);
        return new ViewHolder(view);
    }

    /*
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            onBindViewHolder(holder, position);
        }
    }
    */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //Log.d(TAG, "onBindViewHolder: called. " + i);

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

        SubTask subTask = getItem(i);
        MainTask mainTask = findMainTask(subTask);
        Log.d(TAG, "onBindViewHolder: " + mainTask.getName() + " " + mainTask.isOverdue());
        Log.d(TAG, "onBindViewHolder: pos: " + i + " name: " + subTask.getName() + " Is overdue: " + subTask.isOverdue());
        viewHolder.subTaskName.setText(subTask.getName());
        viewHolder.mainTaskName.setText(mainTask.getName());
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(mainTask.getColor() + 0xFF000000);
        viewHolder.circle.setBackground(drawable);

        Log.d(TAG, "onBindViewHolder: " + subTask.getName() + " isOverdue = " + subTask.isOverdue());
        if (subTask.isOverdue()) {
            viewHolder.card.setCardBackgroundColor(context.getColor(R.color.red));
            viewHolder.subTaskName.setTextColor(context.getColor(R.color.colorAccent));
            viewHolder.mainTaskName.setTextColor(context.getColor(R.color.colorAccent));
        } else {
            viewHolder.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            viewHolder.subTaskName.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
            viewHolder.mainTaskName.setTextColor(context.getColor(R.color.main_task_text_color));
        }

        /* SOLVED ISSUE WITH OTHER CHECKBOXES BEING CHECKED -- PROBLEM WAS CIRCULAR CALLS. https://stackoverflow.com/questions/27070220/android-recyclerview-notifydatasetchanged-illegalstateexception/37305564#37305564  */
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        //check of sub task if completed
        if (subTask.isCompleted()) {
            viewHolder.checkBox.setChecked(true);
            //notifyItemChanged(i);
            Log.d(TAG, "onBindViewHolder: " + subTask.getName() + " set to checked");
        } else {
            viewHolder.checkBox.setChecked(false);
            //notifyItemChanged(i);
            Log.d(TAG, "onBindViewHolder: " + subTask.getName() + " set to unchecked");
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: called for subTask " + subTask.getName() + " isChecked = " + isChecked);
                subTaskViewModel = ViewModelProviders.of((FragmentActivity) context).get(SubTaskViewModel.class);
                if (isChecked) {
                    subTask.setCompleted(true);
                    subTaskViewModel.updateSubTask(subTask);
                    if (subTask.isOverdue()) {
                        //notifyItemRemoved(i);
                    } else {
                        //notifyItemChanged(viewHolder.getAdapterPosition(), false);
                    }
                    //notifyItemChanged(i);
                    //notifyDataSetChanged(); //if an overdue sub task has been checked, this function remove it from screen if called -- TEST THIS. COMMENTING OUT THIS FUNCTION ANIMATES BOX WHILE BEING CHECKED.
                } else {
                    subTask.setCompleted(false);
                    subTaskViewModel.updateSubTask(subTask);
                    //notifyItemChanged(viewHolder.getAdapterPosition(), false);
                }
                //notifyItemChanged(i);
                //notifyItemChanged();
                //Log.d(TAG, "onCheckedChanged: " + subTask.getName() + " IsCompleted: " + subTask.isCompleted());
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

    /*
    @Override
    public int getItemCount() {
        return subTasks.size();
    }
    */

    /*
    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;

        //change later
        notifyDataSetChanged();
    }
    */

    //WHEN REMOVING MAIN TASK OBSERVE
    public void setMainTasks(List<MainTask> mainTasks) {
        this.mainTasks = mainTasks;

        //change later
        //notifyDataSetChanged();
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
        return getItem(position);
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
