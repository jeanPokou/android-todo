package com.slack.jeanpokou.todo.util;


import android.content.Context;
import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksRepository;
import com.slack.jeanpokou.todo.data.source.local.TasksDataContractLocalImpl;
import com.slack.jeanpokou.todo.data.source.local.ToDoDataBase;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {

    public static TasksRepository provideTaskRepository(@NonNull Context context) {
        checkNotNull(context) ;
        ToDoDataBase localDataBase = ToDoDataBase.getInstance(context);
        return TasksRepository.getInstance(
                TasksDataContractLocalImpl.getInstance(
                        new AppExecutors(),
                        localDataBase.taskDao()
                )
        );
    }
}
