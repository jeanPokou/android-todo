package com.slack.jeanpokou.todo.taskaddedit;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.data.source.TasksRepository;

import java.util.List;


public class AddEditTaskPresenter implements TaskAddEditMvp.Presenter, TasksDataContract.LoadTaskCallBack {

    @NonNull
    private final TaskAddEditMvp.View mAddEditTaskView;

    @NonNull
    private final TaskAddEditMvp.Navigator mAddEditNavigator;

    private boolean mIsDataMissing;

    @Nullable
    private String mTaskId;

    @NonNull
    private final TasksRepository mTasksRepository;

    /**
     *  Inject ListPresenter , View , Repository and taskId to edit
     * @param taskId
     * @param addEditTaskView
     * @param tasksRepository
     * @param shouldLoadDataFromRepo
     */
    public AddEditTaskPresenter(@Nullable String taskId, @NonNull final TaskAddEditMvp.View addEditTaskView,
                                @NonNull final TasksRepository tasksRepository, @NonNull TaskAddEditMvp.Navigator navigator, boolean shouldLoadDataFromRepo) {
        mAddEditTaskView = addEditTaskView;
        mTasksRepository = tasksRepository;
        mTaskId = taskId;
        mIsDataMissing = shouldLoadDataFromRepo;
        mAddEditNavigator = navigator;


        // attachPresenter presenter to view
        mAddEditTaskView.attachPresenter(this);
    }

    @Override
    public boolean isDataMissing() {
        return false;
    }

    @Override
    public void populateTask() {

    }

    @Override
    public void saveTask(final String title, final String description) {

        if (isNewTask()) {
            createTask(title, description);
        } else {
            updateTask(title, description);
        }

    }

    /**
     * Update Task with new title and description
     *
     * @param title
     * @param description
     */
    private void updateTask(String title, String description) {

    }

    private boolean isNewTask() {
        return mTaskId == null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    private void createTask(final String title, final String description) {
        Task newTask = new Task(title, description);
        if (newTask.isEmpty()) {
            mAddEditTaskView.showEmptyTaskError();
        } else {
            mTasksRepository.insertTask(newTask);
            mAddEditNavigator.navigateToList();
        }
    }

    @Override
    public void onTasksLoaded(List<Task> taskList) {
    }

    @Override
    public void onDataNotAvailable() {

    }
}
