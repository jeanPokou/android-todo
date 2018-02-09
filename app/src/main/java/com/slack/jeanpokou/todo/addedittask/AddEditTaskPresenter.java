package com.slack.jeanpokou.todo.addedittask;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksRepository;


public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {

    @NonNull
    private final AddEditTaskContract.View mAddEditTaskView;

    private boolean mIsDataMissing;

    @Nullable
    private String mTaskId;

    @NonNull
    private final TasksRepository mTasksRepository;

    /**
     * Create a presenter for add/edit view
     */

    public AddEditTaskPresenter(@NonNull final AddEditTaskContract.View addEditTaskView,
            @NonNull final TasksRepository tasksRepository, final String taskId, boolean shouldLoadDataFromRepo) {
        mAddEditTaskView = addEditTaskView;
        mTasksRepository = tasksRepository;
        mTaskId = taskId;
        mIsDataMissing = shouldLoadDataFromRepo;

        // attach presenter to view
        mAddEditTaskView.attach(this);

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
        createTask(title, description);

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
            mAddEditTaskView.showTaskList();
        }
    }
}
