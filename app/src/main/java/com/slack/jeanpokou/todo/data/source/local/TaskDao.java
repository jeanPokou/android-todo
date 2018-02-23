package com.slack.jeanpokou.todo.data.source.local;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.slack.jeanpokou.todo.data.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    List<Task> getTask();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Task task);

    @Query("DELETE FROM tasks WHERE entryId =  :taskId")
    int deleteTaskById(String taskId);

}
