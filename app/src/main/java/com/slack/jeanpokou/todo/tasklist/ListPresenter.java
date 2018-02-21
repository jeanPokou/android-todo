package com.slack.jeanpokou.todo.tasklist;

import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.data.source.TasksRepository;
import com.slack.jeanpokou.todo.taskaddedit.AddEditActivity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class ListPresenter implements TaskListMvp.Presenter {

    private final static String TAG = ListPresenter.class.getSimpleName();
    private final TasksRepository repository;
    private final TaskListMvp.View taskListView;
    private final TaskListMvp.Navigator listNavigator;

    public ListPresenter(@NonNull TasksRepository tasksRepository, @NonNull TaskListMvp.View view, @NonNull TaskListMvp.Navigator navigator) {

        repository = checkNotNull(tasksRepository, "repository can not be null");
        taskListView = checkNotNull(view, "tasks view can not be null");
        listNavigator = checkNotNull(navigator);

        taskListView.attachPresenter(this);

    }

    @Override
    public void addNewTask(Task task) {
        repository.saveTasks(task, new TasksDataContract.saveTasksCallback() {
            @Override
            public void onSuccess(Long id) {
                taskListView.showSuccessSavedTasks(id);
            }

            @Override
            public void onError() {
                taskListView.showErrorSavedTasks();

            }
        });
        processLoadTask();
    }


    private void processLoadTask() {
        repository.retrieveTasks(new TasksDataContract.retrieveTasksCallBack() {
            @Override
            public void onError() {
                taskListView.showErrorSavedTasks();
            }

            @Override
            public void onSuccess(List<Task> taskList) {
                taskListView.showTasks(taskList);

            }
        });
    }


    @Override
    public void result(int requestCode, int resultCode, Long resultData) {
        if (AddEditActivity.REQUEST_ADD_TASK == requestCode && android.app.Activity.RESULT_OK == resultCode) {
            taskListView.showSuccessSavedTasks(resultData);
        }

    }

    @Override
    public void navigateToAddEditTask() {
        listNavigator.navigateToAddEdit();
    }


    /** Base Presenter Functions **/
    @Override
    public void start() {
        processLoadTask();
    }


    @Override
    public void stop() {

    }


}
