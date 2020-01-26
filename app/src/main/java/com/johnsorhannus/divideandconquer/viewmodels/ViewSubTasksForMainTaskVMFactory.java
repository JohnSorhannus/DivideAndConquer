package com.johnsorhannus.divideandconquer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

//In order to pass in mainTaskId when calling ViewModelProviders.of
public class ViewSubTasksForMainTaskVMFactory implements ViewModelProvider.Factory {
    private Application application;
    private int mainTaskId;

    public ViewSubTasksForMainTaskVMFactory(Application application, int mainTaskId) {
        this.application = application;
        this.mainTaskId = mainTaskId;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ViewSubTasksForMainTaskViewModel(application, mainTaskId);
    }
}
