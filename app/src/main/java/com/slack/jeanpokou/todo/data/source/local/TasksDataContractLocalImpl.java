package com.slack.jeanpokou.todo.data.source.local;


import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.util.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksDataContractLocalImpl implements TasksDataContract{

    private static volatile TasksDataContractLocalImpl INSTANCE  ;

    private TaskDao mTaskDao;
    private AppExecutors mAppExecutors;

    private TasksDataContractLocalImpl(@NonNull AppExecutors appExecutors, @NonNull TaskDao taskDao) {
        mAppExecutors = appExecutors;
        mTaskDao = taskDao;
    }

    public static TasksDataContractLocalImpl getInstance(@NonNull AppExecutors appExecutors, @NonNull TaskDao taskDao) {
        if(INSTANCE == null) {
            synchronized (TasksDataContractLocalImpl.class) {
                if(INSTANCE == null) {
                    INSTANCE = new TasksDataContractLocalImpl(appExecutors,taskDao);
                }
            }
        }
        return INSTANCE;

    }

    @Override
    public void getTasks(@NonNull final LoadTaskCallBack callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTaskDao.getTask();

                // Run Callback on MainThread
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });


            }
        };
        // Run database operation on diskIO
        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void insertTask(@NonNull final Task task) {
        checkNotNull(task);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTaskDao.insert(task);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}
