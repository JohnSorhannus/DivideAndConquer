package com.johnsorhannus.divideandconquer.room;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.SubTask;

import java.util.Calendar;
import java.util.List;

import static com.johnsorhannus.divideandconquer.DueDateQueryLiterals.getCurrentDate;
import static com.johnsorhannus.divideandconquer.DueDateQueryLiterals.getYesterday;

public class AppRepository {

    private AppDatabase database;
    private SubTaskDao subTaskDao;
    private MainTaskDao mainTaskDao;
    private static final String TAG = "AppRepository";

    public AppRepository(Context context) {
        database = AppDatabase.getInstance(context);
        subTaskDao = database.getSubTaskDao();
        mainTaskDao = database.getMainTaskDao();
    }

    public void insertSubTask(SubTask subTask) {
        new InsertSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    public void insertMainTask(MainTask mainTask) {
        new InsertMainTaskAsyncTask(mainTaskDao).execute(mainTask);
    }

    public void updateSubTask(SubTask subTask) {
        new UpdateSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    public void updateMainTask(MainTask mainTask) {
        new UpdateMainTaskAsyncTask(mainTaskDao).execute(mainTask);
    }

    public void deleteSubTask(SubTask subTask) {
        new DeleteSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    public void deleteMainTask(MainTask mainTask) {
        new DeleteMainTaskAsyncTask(mainTaskDao).execute(mainTask);
    }

    public LiveData<List<SubTask>> retrieveSubTasksForSTFragment() {
        //LiveData<MainTask> latestMainTask = database.getMainTaskDao().getLatestDueDate();
        /*LiveData<Long> latestDueDate = Transformations.map(latestMainTask, mainTask -> {
            return mainTask.getDueDate().getTimeInMillis();
        });*/

        /*if (latestMainTask == null) { //TESTING ONLY!!!!
            return database.getSubTaskDao().getSubTasksForSTFragment(0, 0, 0);
        } else {
            return database.getSubTaskDao().getSubTasksForSTFragment(latestMainTask.getValue().getDueDate().getTimeInMillis(), getCurrentDate(), getYesterday());
        }*/

        return database.getSubTaskDao().getSubTasksForSTFragment(getCurrentDate().getTimeInMillis(), getYesterday().getTimeInMillis());

        /*try {
            Log.d(TAG, "retrieveSubTasksForSTFragment: 1ST CALLED");
            return database.getSubTaskDao().getSubTasksForSTFragment(latestMainTask.getValue().getDueDate().getTimeInMillis(), getCurrentDate(), getYesterday());
            //Log.d(TAG, "retrieveSubTasksForSTFragment: ");
        } catch (NullPointerException e) {
            Log.d(TAG, "retrieveSubTasksForSTFragment: 2ND CALLED");
            return database.getSubTaskDao().getSubTasksForSTFragment(0, 0, 0);
        }*/


    }

    /*public LiveData<MainTask> retrieveMainTask(final int subTaskId) {
        return database.getSubTaskMainTaskJunctionDao().getMainTask(subTaskId);
    }*/

    public LiveData<List<MainTask>> retrieveOverdueMainTasks() {
        return database.getMainTaskDao().getOverdueMainTasks(getCurrentDate().getTimeInMillis());
    }

    public LiveData<List<MainTask>> retrieveActiveMainTasks() {
        return database.getMainTaskDao().getActiveMainTasks(getCurrentDate().getTimeInMillis());
    }

    /*public LiveData<Integer> retrieveNumActiveMainTasks() {
        return database.getMainTaskDao().getNumActiveMainTasks(getCurrentDate());
    }*/

    public LiveData<List<MainTask>> retrieveCompletedMainTasks() {
        return database.getMainTaskDao().getCompletedMainTasks();
    }

    public LiveData<List<MainTask>> retrieveAllMainTasks() {
        return database.getMainTaskDao().getAllMainTasks();
    }

    //testing -- problem -- can't be int, has to be LiveData
    public int retrieveNumSubTasks() {
        int num = database.getSubTaskDao().getNumberOfSubTasks();

        return num;
    }

    /******** INSERT **********/
    private static class InsertSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void> {
        private SubTaskDao subTaskDao;

        private InsertSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.insert(subTasks[0]);
            return null;
        }
    }

    private static class InsertMainTaskAsyncTask extends AsyncTask<MainTask, Void, Void> {
        private MainTaskDao mainTaskDao;

        private InsertMainTaskAsyncTask(MainTaskDao mainTaskDao) {
            this.mainTaskDao = mainTaskDao;
        }

        @Override
        protected Void doInBackground(MainTask... mainTasks) {
            mainTaskDao.insert(mainTasks[0]);
            return null;
        }
    }

    /********** UPDATE ***********/
    private static class UpdateSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void> {
        private SubTaskDao subTaskDao;

        private UpdateSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.update(subTasks[0]);
            return null;
        }
    }

    private static class UpdateMainTaskAsyncTask extends AsyncTask<MainTask, Void, Void> {
        private MainTaskDao mainTaskDao;

        private UpdateMainTaskAsyncTask(MainTaskDao mainTaskDao) {
            this.mainTaskDao = mainTaskDao;
        }

        @Override
        protected Void doInBackground(MainTask... mainTasks) {
            mainTaskDao.update(mainTasks[0]);
            return null;
        }
    }

    /********** DELETE ***********/
    private static class DeleteSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void> {
        private SubTaskDao subTaskDao;

        private DeleteSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.delete(subTasks[0]);
            return null;
        }
    }

    private static class DeleteMainTaskAsyncTask extends AsyncTask<MainTask, Void, Void> {
        private MainTaskDao mainTaskDao;

        private DeleteMainTaskAsyncTask(MainTaskDao mainTaskDao) {
            this.mainTaskDao = mainTaskDao;
        }

        @Override
        protected Void doInBackground(MainTask... mainTasks) {
            mainTaskDao.delete(mainTasks[0]);
            return null;
        }
    }

    static private class DueDateQueryLiterals {
        public long getCurrentDate() {
            Calendar currentDate = clearTimeValues();
            return currentDate.getTimeInMillis();
        }

        public long getYesterday() {
            Calendar yesterday = clearTimeValues();
            yesterday.add(Calendar.DAY_OF_YEAR, -1);
            return yesterday.getTimeInMillis();
        }

        private Calendar clearTimeValues() {
            Calendar date = Calendar.getInstance();
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);

            return date;
        }
    }

}
