package com.johnsorhannus.divideandconquer.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import com.johnsorhannus.divideandconquer.MainTask;
import com.johnsorhannus.divideandconquer.SubTask;

import java.util.Calendar;
import java.util.List;

@Dao
public interface SubTaskDao {

    @Insert
    void insert(SubTask subTask);

    //@Query("SELECT * FROM subTask WHERE (dueDate <= :latestDueDateMillis AND dueDate >= :currentDateMillis) OR (dueDate <= :yesterdayMillis AND completed = 0) ORDER BY dueDate ASC;")
    @Query("SELECT subTask.id, subTask.name, subTask.dueDate, subTask.completed, subTask.mainTaskId, MAX(mainTask.dueDate) AS latestDate FROM subTask, mainTask WHERE (subTask.dueDate >= :currentDateMillis) OR (subTask.dueDate <= :yesterdayMillis AND subTask.completed = 0) GROUP BY subTask.id HAVING subTask.dueDate <= latestDate ORDER BY subTask.dueDate ASC")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<List<SubTask>> getSubTasksForSTFragment(final long currentDateMillis, final long yesterdayMillis);

    @Query("SELECT subTask.id, subTask.name, subTask.dueDate, subTask.completed, subTask.mainTaskId FROM subTask WHERE subTask.mainTaskId = :mainTaskId ORDER BY subTask.dueDate ASC")
    LiveData<List<SubTask>> getSubTasksForMainTask(final int mainTaskId);

    //testing
    @Query("SELECT count(*) FROM subTask")
    int getNumberOfSubTasks();

    @Delete
    void delete(SubTask subTask);

    @Update
    void update(SubTask subTask);
}
