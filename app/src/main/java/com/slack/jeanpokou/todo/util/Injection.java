package com.slack.jeanpokou.todo.util;


import android.content.Context;
import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.source.TasksRepository;
import com.slack.jeanpokou.todo.data.source.local.LocalDataSource;
import com.slack.jeanpokou.todo.data.source.local.TaskDataBase;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {

    public static TasksRepository provideTaskRepository(@NonNull Context context) {
        checkNotNull(context) ;
        TaskDataBase localDataBase = TaskDataBase.getInstance(context);
        return TasksRepository.getInstance(
                LocalDataSource.getInstance(
                        new AppExecutors(),
                        localDataBase.taskDao()
                )
        );
    }
}
