package com.slack.jeanpokou.todo.data.source.local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.slack.jeanpokou.todo.data.Task;

/**
 *  Room DataBase for Task table
 */

@Database(entities = {Task.class},version = 1)
public abstract class TaskDataBase extends RoomDatabase {
    private static TaskDataBase INSTANCE;

    private static final  Object sLock = new Object();

    public abstract TaskDao taskDao();


    public static TaskDataBase getInstance(Context context) {
        synchronized (sLock) {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder
                        (
                        context.getApplicationContext(),
                        TaskDataBase.class,"Tasks.db"
                        ).build();
            }
            return INSTANCE;
        }
    }

}
