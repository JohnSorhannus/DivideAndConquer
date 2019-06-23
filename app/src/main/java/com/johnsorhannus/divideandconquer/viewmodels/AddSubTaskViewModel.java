package com.johnsorhannus.divideandconquer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.SubTask;
import com.johnsorhannus.divideandconquer.room.AppRepository;

import java.util.List;

public class AddSubTaskViewModel extends AndroidViewModel {
    private AppRepository repository;

    public AddSubTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void insertSubTask(SubTask subTask) {
        repository.insertSubTask(subTask);
    }

    public LiveData<List<MainTask>> retrieveActiveMainTasks() {
        /* PROBLEM

        repository.retrieveActiveMainTasks() returns an empty list of main tasks!!!! Check SQL statement!
         */

        return repository.retrieveActiveMainTasks();

        //return repository.retrieveAllMainTasks();
    }
}
