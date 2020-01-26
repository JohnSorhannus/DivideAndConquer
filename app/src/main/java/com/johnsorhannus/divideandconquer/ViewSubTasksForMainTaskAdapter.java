package com.johnsorhannus.divideandconquer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/*public class ViewSubTasksForMainTaskAdapter extends ListAdapter<SubTask, ViewSubTasksForMainTaskAdapter.ViewHolder> {

    public ViewSubTasksForMainTaskAdapter(Context context) {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<SubTask> DIFF_CALLBACK = SubTaskDiffCallback.getSubTaskDiffCallback();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subTaskName;
        TextView date;
        CheckBox checkBox;
        CardView card;
        //ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subTaskName = itemView.findViewById(R.id.vmtCard_sub_task_name);
            date = itemView.findViewById(R.id.vmtCard_date);
            checkBox = itemView.findViewById(R.id.stFrag_checkBox);
            card = itemView.findViewById(R.id.parent_layout);
        }
    }
}*/
