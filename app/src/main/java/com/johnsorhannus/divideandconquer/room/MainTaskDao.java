package com.johnsorhannus.divideandconquer.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.johnsorhannus.divideandconquer.MainTask;

import java.util.List;

@Dao
public interface MainTaskDao {

    @Insert
    void insert(MainTask mainTask);

    @Query("SELECT mainTask.id, mainTask.name, mainTask.dueDate, mainTask.color, 0 AS percentCompleted FROM mainTask INNER JOIN subTask on mainTask.id = subTask.mainTaskId WHERE mainTask.dueDate < :currentDateMillis GROUP BY mainTask.id HAVING SUM(subTask.completed) < COUNT(*) ORDER BY mainTask.dueDate ASC;")
    LiveData<List<MainTask>> getOverdueMainTasks(final long currentDateMillis);

    //SUM PROBABLY RETURNS NULL, WHICH IS PROBABLY WHY MAIN TASK DOES NOT SHOW UP IF IT DOES NOT HAVE ANY SUBTASKS ASSOCIATED WITH IT
    //PROBLEM: SQL STATEMENT FAILS WHEN NOTHING EXISTS IN SUBTASK B/C CROSS JOIN PRODUCES (# OF ENTRIES IN MAINTASK x # OF ENTRIES IN SUBTASK) ENTRIES WHICH IS ALWAYS ZERO WITH NO SUBTASKS
    @Query("SELECT DISTINCT mainTask.id, mainTask.name, mainTask.dueDate, mainTask.color, 0 AS percentCompleted\n" +
            "FROM mainTask\n" +
            "CROSS JOIN subTask\n" +
            "WHERE mainTask.dueDate >= :currentDateMillis AND ((mainTask.id = subTask.mainTaskId AND subTask.completed IN (0)) OR mainTask.id != subTask.mainTaskId)\n" +
            "ORDER BY mainTask.dueDate ASC;")
    LiveData<List<MainTask>> getActiveMainTasks(final long currentDateMillis);

    @Query("SELECT mainTask.id, mainTask.name, mainTask.dueDate, mainTask.color, 0 AS percentCompleted FROM mainTask INNER JOIN subTask on mainTask.id = subTask.mainTaskId GROUP BY mainTask.id HAVING SUM(subTask.completed) = COUNT(*) ORDER BY mainTask.dueDate ASC;")
    LiveData<List<MainTask>> getCompletedMainTasks();

    //@Query("SELECT * FROM mainTask ORDER BY mainTask.dueDate ASC")
    @Query("SELECT mainTask.id, mainTask.name, mainTask.color, mainTask.dueDate, IFNULL((((SELECT COUNT(id) FROM subTask WHERE mainTask.id = subTask.mainTaskId AND subTask.completed = 1) * 100)/(SELECT COUNT(id) FROM subTask WHERE mainTask.id = subTask.mainTaskId)), 0) AS percentCompleted\n" +
            "FROM mainTask\n" +
            "LEFT JOIN subTask ON mainTask.id = subTask.mainTaskId\n" +
            "GROUP BY mainTask.name\n" +
            "ORDER BY mainTask.dueDate ASC")
    LiveData<List<MainTask>> getAllMainTasks();

    @Query("SELECT * FROM mainTask WHERE dueDate = (SELECT MAX(dueDate) FROM mainTask);")
    LiveData<MainTask> getLatestDueDate(); //maybe * would work instead

    @Query("SELECT mainTask.id, mainTask.name, mainTask.dueDate, mainTask.color, IFNULL((((SELECT COUNT(id) FROM subTask WHERE mainTask.id = subTask.mainTaskId AND subTask.completed = 1) * 100)/(SELECT COUNT(id) FROM subTask WHERE mainTask.id = subTask.mainTaskId)), 0) AS percentCompleted FROM mainTask WHERE mainTask.id = :mainTaskId")
    LiveData<MainTask>getMainTask(final int mainTaskId);

    @Delete
    void delete(MainTask mainTask);

    @Update
    void update(MainTask mainTask);
}
