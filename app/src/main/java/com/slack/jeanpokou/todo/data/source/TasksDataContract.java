package com.slack.jeanpokou.todo.data.source;

import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.Task;

import java.util.List;

/**
 *  Contract between Repositories and  Data Sources
 */

public interface TasksDataContract {

    interface LoadTaskCallBack {
        void onTasksLoaded(List<Task> taskList);
        void onDataNotAvailable();
    }

    void getTasks (@NonNull LoadTaskCallBack callback);
    void insertTask(@NonNull Task tasks);


}
