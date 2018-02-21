package com.slack.jeanpokou.todo.data.source;

import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.Task;

import java.util.List;

/**
 * Contract between Repositories and  Data Sources
 */

public interface TasksDataContract {


    void retrieveTasks(@NonNull retrieveTasksCallBack callback);

    void saveTasks(@NonNull Task tasks, @NonNull saveTasksCallback callback);

    interface retrieveTasksCallBack {

        void onSuccess(List<Task> taskList);

        void onError();
    }

    interface saveTasksCallback {

        void onSuccess(Long id) ;

        void onError();

    }


}
