package com.johnsorhannus.divideandconquer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.SubTask;
import com.johnsorhannus.divideandconquer.room.AppRepository;

import java.util.List;

public class ViewSubTasksForMainTaskViewModel extends AndroidViewModel {
    AppRepository repository;
    private LiveData<List<SubTask>> subTasks;

    public ViewSubTasksForMainTaskViewModel(@NonNull Application application, final int mainTaskId) {
        super(application);
        repository = new AppRepository(application);
        subTasks = repository.retrieveSubTasksForMainTask(mainTaskId);
    }
}
