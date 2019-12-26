package com.johnsorhannus.divideandconquer;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnsorhannus.divideandconquer.viewmodels.MainTaskViewModel;

import java.util.List;

public class MainTaskFragment extends Fragment {
    private RecyclerView recyclerView;
    private MainTaskAdapter adapter;
    private MainTaskViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_task, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set adapter
        adapter = new MainTaskAdapter(getContext());
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainTaskViewModel.class);
        viewModel.getMainTasks().observe(this, new Observer<List<MainTask>>() {
            @Override
            public void onChanged(@Nullable List<MainTask> mainTasks) {
                adapter.setMainTasks(mainTasks);
            }
        });

        /* DELETE FUNCTIONALITY */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                MainTask deletedMainTask = adapter.getMainTaskAt(viewHolder.getAdapterPosition());
                StringBuilder builder = new StringBuilder();


                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.delete_maintask_title))
                        .setMessage(builder.append(getResources().getString(R.string.delete_maintask_message1)).append(deletedMainTask.getName()).append(getResources().getString(R.string.delete_maintask_message2)))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            //DELETE when clicking "yes"
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.deleteMainTask(deletedMainTask);
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

        return rootView;
    }
}
