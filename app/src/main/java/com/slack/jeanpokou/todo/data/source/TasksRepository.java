package com.slack.jeanpokou.todo.data.source;


import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.local.TasksDataContractLocalImpl;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksRepository  implements TasksDataContract{

    private static TasksRepository INSTANCE = null;
    private final TasksDataContractLocalImpl mTasksLocalDataSource;

    public  static TasksRepository getInstance( TasksDataContractLocalImpl tasksLocalDataSource) {

        if(INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksLocalDataSource);
        }
        return INSTANCE;

    }


    private TasksRepository(TasksDataContractLocalImpl tasksLocalDataSource) {
        mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    @Override
    public void getTasks(@NonNull final LoadTaskCallBack callback) {

        checkNotNull(callback);

        mTasksLocalDataSource.getTasks(new LoadTaskCallBack() {
            @Override
            public void onTasksLoaded(List<Task> taskList) {
                callback.onTasksLoaded(taskList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void insertTask(@NonNull Task tasks) {
        mTasksLocalDataSource.insertTask(tasks);
    }
}
