package com.slack.jeanpokou.todo.data.source;


import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.local.LocalDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksRepository implements TasksDataContract {

    private static TasksRepository INSTANCE = null;
    private final LocalDataSource mTasksLocalDataSource;

    private TasksRepository(LocalDataSource tasksLocalDataSource) {
        mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    public static TasksRepository getInstance(LocalDataSource tasksLocalDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksLocalDataSource);
        }
        return INSTANCE;

    }

    @Override
    public void retrieveTasks(@NonNull final retrieveTasksCallBack callback) {

        checkNotNull(callback);

        mTasksLocalDataSource.retrieveTasks(new retrieveTasksCallBack() {
            @Override
            public void onSuccess(List<Task> taskList) {
                callback.onSuccess(taskList);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void retrieveTask(@NonNull String taskId, @NonNull final retrieveTaskCallBack callback) {
        mTasksLocalDataSource.retrieveTask(taskId, new retrieveTaskCallBack() {
            @Override
            public void onSuccess(Task task) {
               callback.onSuccess(task);
            }

            @Override
            public void onError() {
                callback.onError();

            }
        });

    }

    @Override
    public void saveTasks(@NonNull Task tasks, @NonNull final saveTasksCallback callback) {

        checkNotNull(tasks);
        checkNotNull(callback);

        mTasksLocalDataSource.saveTasks(tasks, new saveTasksCallback() {

            @Override
            public void onSuccess(Long id) {
                callback.onSuccess(id);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });

    }

    @Override
    public void deleteTaskById(@NonNull String taskId) {
        checkNotNull(taskId, "taskId can not be null");

        mTasksLocalDataSource.deleteTaskById(taskId);
    }

}
