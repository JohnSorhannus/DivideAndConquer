package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        return rootView;
    }
}
