package com.slack.jeanpokou.todo.taskdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Strings;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.data.source.TasksRepository;

import static com.slack.jeanpokou.todo.taskdetail.TaskDetailMvp.Navigator;
import static com.slack.jeanpokou.todo.taskdetail.TaskDetailMvp.Presenter;
import static com.slack.jeanpokou.todo.taskdetail.TaskDetailMvp.View;

public class DetailPresenter implements Presenter {

    private final TasksRepository repository;
    private final View detailView;
    private final Navigator detailNavigator;
    private final String TAG = DetailPresenter.class.getSimpleName();
    private final String taskId;

    DetailPresenter(@Nullable String taskId, @NonNull TasksRepository repository, @NonNull View view, @NonNull Navigator navigator) {
        this.repository = repository;
        this.detailView = view;
        this.detailNavigator = navigator;
        this.taskId = taskId;

        view.attachPresenter(this);
    }

    @Override
    public void start() {
        Log.d(TAG, "Presenter start");
        openTask();
    }

    private void openTask() {

        if (Strings.isNullOrEmpty(taskId)) {
            detailView.showNoTask();
            return;
        }
        // showing loading while retrieving data from repository
        detailView.setLoadingIndicator(true);
        repository.retrieveTask(taskId, new TasksDataContract.retrieveTaskCallBack() {
            @Override
            public void onSuccess(Task task) {

                // if view is not in Activity Stack
                if (!detailView.isActive()) {
                    Log.d(TAG, "task title is " + task.getTitle());
                    return;
                }
                detailView.setLoadingIndicator(false);
                detailView.showTask(task);
            }

            @Override
            public void onError() {
                Log.e(TAG, "error retrieving task");
                detailView.showNoTask();
            }
        });
    }

    @Override
    public void stop() {

    }
}
