package com.johnsorhannus.divideandconquer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.SubTask;
import com.johnsorhannus.divideandconquer.room.AppRepository;

import java.util.List;

public class MainTaskViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<MainTask>> mainTasks;
    //private LiveData<List<SubTask>> subTasks;

    public MainTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        mainTasks = repository.retrieveAllMainTasks();
        //subTasks = repository.retrieveSubTasksForSTFragment();
    }

    public LiveData<List<MainTask>> getMainTasks() {
        return mainTasks;
    }

    //public LiveData<List<SubTask>> getSubTasks() { return subTasks; }

    public LiveData<List<MainTask>> retrieveActiveMainTasks() {
        return repository.retrieveActiveMainTasks();
    }

    public void deleteMainTask(MainTask mainTask) { repository.deleteMainTask(mainTask); }
}
