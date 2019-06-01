package com.johnsorhannus.divideandconquer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.SubTask;
import com.johnsorhannus.divideandconquer.room.AppRepository;

import java.util.List;

public class SubTaskViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<SubTask>> subTasks;
    private LiveData<List<MainTask>> mainTasks;

    public SubTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        subTasks = repository.retrieveSubTasksForSTFragment();
        mainTasks = repository.retrieveAllMainTasks();
    }
    //private MutableLiveData<>

    public void insertSubTask(SubTask subTask) {
        repository.insertSubTask(subTask);
    }

    public void insertMainTask(MainTask mainTask) {
        repository.insertMainTask(mainTask);
    }

    public void updateSubTask(SubTask subTask) {
        repository.updateSubTask(subTask);
    }

    public void updateMainTask(MainTask mainTask) {
        repository.updateMainTask(mainTask);
    }

    public void deleteSubTask(SubTask subTask) {
        repository.deleteSubTask(subTask);
    }

    public void deleteMainTask(MainTask mainTask) {
        repository.deleteMainTask(mainTask);
    }

    public LiveData<List<SubTask>> getSubTasks() {
        return subTasks;
    }

    public LiveData<List<MainTask>> getMainTasks() {
        return mainTasks;
    }

}
