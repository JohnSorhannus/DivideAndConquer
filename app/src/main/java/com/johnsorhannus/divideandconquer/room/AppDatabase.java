package com.johnsorhannus.divideandconquer.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.SubTask;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

@Database(entities = {SubTask.class, MainTask.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "task_db";

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME
            ).fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }

        Log.d(TAG, "getInstance: INSTANCE CREATED");

        return instance;
    }

    public abstract SubTaskDao getSubTaskDao();

    public abstract MainTaskDao getMainTaskDao();

    //TESTING TO POPULATE DATABASE
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "onCreate: ROOM DATABASE CALLBACK");
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MainTaskDao mainTaskDao;
        private SubTaskDao subTaskDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            mainTaskDao = db.getMainTaskDao();
            subTaskDao = db.getSubTaskDao();

            Log.d(TAG, "PopulateDbAsyncTask: CALLED");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Calendar date = Calendar.getInstance();
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
            date.set(2019, 2, 25);
            MainTask mt1 = new MainTask("School Assignments", 0xFF0000, date);
            mainTaskDao.insert(mt1);

            Calendar date2 = Calendar.getInstance();
            date2.set(Calendar.HOUR_OF_DAY, 0);
            date2.set(Calendar.MINUTE, 0);
            date2.set(Calendar.SECOND, 0);
            date2.set(Calendar.MILLISECOND, 0);
            date2.set(2019, 2, 23);
            SubTask st1 = new SubTask("Read database book", date2, 1);
            subTaskDao.insert(st1);
            mt1.addSubTask(st1);



            Calendar date3 = Calendar.getInstance();
            date3.set(Calendar.HOUR_OF_DAY, 0);
            date3.set(Calendar.MINUTE, 0);
            date3.set(Calendar.SECOND, 0);
            date3.set(Calendar.MILLISECOND, 0);
            date3.set(2019, 2, 20);
            SubTask st2 = new SubTask("Read OS book", date3, 1);
            subTaskDao.insert(st2);
            mt1.addSubTask(st1);

            Calendar date4 = Calendar.getInstance();
            date4.set(Calendar.HOUR_OF_DAY, 0);
            date4.set(Calendar.MINUTE, 0);
            date4.set(Calendar.SECOND, 0);
            date4.set(Calendar.MILLISECOND, 0);
            date4.set(2019, 2, 17);
            SubTask st3 = new SubTask("Wash car", date4, 1);
            subTaskDao.insert(st3);
            mt1.addSubTask(st3);

            Log.d(TAG, "doInBackground: " + mt1.numTasks());


            Log.d(TAG, "doInBackground: DATABASE POPULATED");

            return null;
        }
    }
}
