package com.johnsorhannus.divideandconquer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnsorhannus.divideandconquer.viewmodels.SubTaskViewModel;

import java.util.List;

public class SubTaskFragment extends Fragment {
    //MainTask m;
    private RecyclerView recyclerView;

    //private ArrayList<SubTask> subTasks = new ArrayList<>();
    private SubTaskAdapter adapter;
    //private AppRepository repository;
    private SubTaskViewModel viewModel;

    private static final String TAG = "SubTaskFragment";
    public static final int EDIT_SUBTASK_REQUEST = 1;

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
        viewModel.getSubTasks().observe(getViewLifecycleOwner(), new Observer<List<SubTask>>() {
            @Override
            public void onChanged(@Nullable List<SubTask> subTasks) {
                //Update RecyclerView
                Log.d(TAG, "onChanged: subTasks length = " + subTasks.size());
                adapter.submitList(subTasks);
                //Log.d(TAG, "onChanged: " + subTasks.size());
                //Toast.makeText(getContext(), "hiuhih", Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "onChanged: CALLED");
            }
        });


        viewModel.getMainTasks().observe(getViewLifecycleOwner(), new Observer<List<MainTask>>() {
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

        /* DELETE FUNCTIONALITY */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                SubTask deletedSubTask = adapter.getSubTaskAt(viewHolder.getAdapterPosition());
                viewModel.deleteSubTask(deletedSubTask);
                Snackbar.make(getView(), getResources().getString(R.string.delete_subtask_snackbar) + deletedSubTask.getName() + getResources().getString(R.string.delete_snackbar), Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewModel.insertSubTask(deletedSubTask);
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new SubTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SubTask subTask) {
                Intent intent = new Intent(getActivity(), AddEditSubTaskActivity.class);
                /*
                intent.putExtra("id", subTask.getId());
                intent.putExtra("name", subTask.getName());
                intent.put
                */
                intent.putExtra("subTask", subTask);
                startActivityForResult(intent, EDIT_SUBTASK_REQUEST);
            }
        });

        return rootView;
    }

    //TESTING!!!

    //PROBLEMS WITH THE APP
    //1. I'M using the main thread for the database -- this needs to be changed I guess with asynctask -- fixed ,prop
    //2. NullPointerExceptions because data that does not exist is being retrieved. I sort of fixed by using a query to return num of records but there may be a better way of doing this -- fixed, prop
    //3. Create mock values for the database to test it out
    //4. Dao functions that return a single sub/main task should probably return a livedata version of it -- this may be the source of my problems -- seems to be with counting the num of entries now -- mitch doesnt have something like that -fixed, prop, see what getValue does -- calling in background thread does not guerantee that latest value set will be receieved

}
