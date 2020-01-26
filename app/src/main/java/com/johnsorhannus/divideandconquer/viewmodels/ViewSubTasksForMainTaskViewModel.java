package com.johnsorhannus.divideandconquer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.SubTask;
import com.johnsorhannus.divideandconquer.room.AppRepository;

import java.util.List;

public class ViewSubTasksForMainTaskViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<SubTask>> subTasks;
    private LiveData<MainTask> mainTask;

    public static final String TAG = "ViewSubTasksMainTaskVM";

    public ViewSubTasksForMainTaskViewModel(@NonNull Application application, final int mainTaskId) {
        super(application);
        repository = new AppRepository(application);
        subTasks = repository.retrieveSubTasksForMainTask(mainTaskId);
        mainTask = repository.retrieveMainTask(mainTaskId);
    }

    //For when deleting sub task is undone
    public void insertSubTask(SubTask subTask) {
        repository.insertSubTask(subTask);
    }

    public void updateSubTask(SubTask subTask) {
        repository.updateSubTask(subTask);
        Log.d(TAG, "updateSubTask: CALLED for " + subTask.getName() + " isCompleted = " + subTask.isCompleted() + " isOverdue = " + subTask.isOverdue());
    }

    public void updateMainTask(MainTask mainTask) {
        repository.updateMainTask(mainTask);
    }

    public void deleteSubTask(SubTask subTask) {
        repository.deleteSubTask(subTask);
    }

    //Possibly if I add a delete button in activity
    public void deleteMainTask(MainTask mainTask) {
        repository.deleteMainTask(mainTask);
    }

    public LiveData<List<SubTask>> getSubTasks() {
        return subTasks;
    }

    public LiveData<MainTask> getMainTask() {
        return mainTask;
    }
}
