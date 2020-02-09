package com.johnsorhannus.divideandconquer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.room.AppRepository;

public class AddMainTaskViewModel extends AndroidViewModel {
    private AppRepository repository;

    public AddMainTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public long retrieveMaxDueDateForMT(final int mainTaskId) {
        return repository.retrieveMaxDueDateForMT(mainTaskId);
    }

    public void insertMainTask(MainTask mainTask) {
        repository.insertMainTask(mainTask);
    }
}
