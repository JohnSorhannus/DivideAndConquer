package com.johnsorhannus.divideandconquer;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.johnsorhannus.divideandconquer.room.AppRepository;
import com.johnsorhannus.divideandconquer.viewmodels.SubTaskViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SubTaskFragment extends Fragment {
    //MainTask m;
    private RecyclerView recyclerView;

    //private ArrayList<SubTask> subTasks = new ArrayList<>();
    private SubTaskAdapter adapter;
    //private AppRepository repository;
    private SubTaskViewModel viewModel;

    private static final String TAG = "SubTaskFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and setup recycler view
        View rootView = inflater.inflate(R.layout.fragment_sub_task, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.sub_task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set adapter
        adapter = new SubTaskAdapter(getContext());
        recyclerView.setAdapter(adapter);

        //we will see if this works
        //repository = new AppRepository(getContext());
        viewModel = ViewModelProviders.of(this).get(SubTaskViewModel.class);
        //viewModel.init(getContext());
        viewModel.getSubTasks().observe(this, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(@Nullable List<SubTask> subTasks) {
                //Update RecyclerView
                adapter.setSubTasks(subTasks);
                Log.d(TAG, "onChanged: " + subTasks.size());
                Toast.makeText(getContext(), "hiuhih", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onChanged: CALLED");
            }
        });

        viewModel.getMainTasks().observe(this, new Observer<List<MainTask>>() {
            @Override
            public void onChanged(@Nullable List<MainTask> mainTasks) {
                //Update RecyclerView
                //Log.d(TAG, "onChanged mainTasks: " + mainTasks.size());
                //Log.d(TAG, "onChanged mainTaskName: " + mainTasks.get(0).getName());
                adapter.setMainTasks(mainTasks);
            }
        });

        //viewModel.updateSubTask(SubTask subTask);


        //initMainTask();
        //retrieveSubTasks();
        /*Calendar d1 = Calendar.getInstance();
        d1.clear();
        d1.set(2019, 0, 29);
        subTasks.add(new SubTask("test", d1));*/

        //init recyclerView



        return rootView;
    }

    //TESTING!!!

    //PROBLEMS WITH THE APP
    //1. I'M using the main thread for the database -- this needs to be changed I guess with asynctask -- fixed ,prop
    //2. NullPointerExceptions because data that does not exist is being retrieved. I sort of fixed by using a query to return num of records but there may be a better way of doing this -- fixed, prop
    //3. Create mock values for the database to test it out
    //4. Dao functions that return a single sub/main task should probably return a livedata version of it -- this may be the source of my problems -- seems to be with counting the num of entries now -- mitch doesnt have something like that -fixed, prop, see what getValue does -- calling in background thread does not guerantee that latest value set will be receieved

}
