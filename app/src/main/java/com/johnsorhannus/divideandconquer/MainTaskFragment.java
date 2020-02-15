package com.johnsorhannus.divideandconquer;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnsorhannus.divideandconquer.viewmodels.MainTaskViewModel;

import java.util.List;

public class MainTaskFragment extends Fragment implements MainTaskAdapter.OnMainTaskListener {
    private RecyclerView recyclerView;
    private MainTaskAdapter adapter;
    private MainTaskViewModel viewModel;

    public static final int EDIT_MAINTASK_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_task, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set adapter
        adapter = new MainTaskAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainTaskViewModel.class);
        viewModel.getMainTasks().observe(this, new Observer<List<MainTask>>() {
            @Override
            public void onChanged(@Nullable List<MainTask> mainTasks) {
                adapter.submitList(mainTasks);
            }
        });

        //Observes any changes in sub task to update percentage completed
        /*
        viewModel.getSubTasks().observe(this, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(@Nullable List<SubTask> subTasks) {
                adapter.notifyDataSetChanged();
            }
        });
        */

        /* DELETE FUNCTIONALITY */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                MainTask deletedMainTask = adapter.getMainTaskAt(viewHolder.getAdapterPosition());

                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.delete_maintask_title))
                        .setMessage(getResources().getString(R.string.delete_maintask_message1) + deletedMainTask.getName() + getResources().getString(R.string.delete_maintask_message2))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            //DELETE when clicking "yes"
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.deleteMainTask(deletedMainTask);
                                //show snackbar
                                Snackbar.make(getView(), getResources().getString(R.string.delete_maintask_snackbar) + deletedMainTask.getName() + getResources().getString(R.string.delete_snackbar), Snackbar.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            //Restore when hitting "no"
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        })
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnMainTaskLongClickListener(new MainTaskAdapter.OnMainTaskLongClickListener() {
            @Override
            public void onMainTaskLongClick(MainTask mainTask) {
                Intent intent = new Intent(getActivity(), AddEditMainTaskActivity.class);
                /*
                intent.putExtra("id", subTask.getId());
                intent.putExtra("name", subTask.getName());
                intent.put
                */
                intent.putExtra("mainTask", mainTask);
                startActivityForResult(intent, EDIT_MAINTASK_REQUEST);
            }
        });

        return rootView;
    }

    @Override
    public void onMainTaskClick(MainTask mainTask) {
        //Toast.makeText(getContext(), Integer.toString(adapter.getMainTaskAt(position).percentCompleted()), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), ViewSubTasksForMainTaskActivity.class);
        //intent.putExtra("mainTask", mainTask);
        intent.putExtra("mainTask", mainTask);
        startActivity(intent);
    }
}
