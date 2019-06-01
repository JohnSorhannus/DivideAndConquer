package com.johnsorhannus.divideandconquer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.room.AppRepository;

import java.util.List;

public class MainTaskViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<MainTask>> mainTasks;

    public MainTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        mainTasks = repository.retrieveAllMainTasks();
    }

    public LiveData<List<MainTask>> getMainTasks() {
        return mainTasks;
    }


}
