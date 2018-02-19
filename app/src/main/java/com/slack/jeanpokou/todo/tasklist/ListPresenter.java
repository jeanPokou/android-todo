package com.slack.jeanpokou.todo.tasklist;

import static com.google.common.base.Preconditions.checkNotNull;
import android.support.annotation.NonNull;
import com.slack.jeanpokou.todo.taskaddedit.AddEditActivity;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.data.source.TasksRepository;
import java.util.List;


public class ListPresenter implements TaskListMvp.Presenter {

    private final TasksRepository mTaskRepository;

    private final TaskListMvp.View mTaskView;

    private final TaskListMvp.Navigator mListNavigator;

    public ListPresenter(@NonNull TasksRepository tasksRepository, @NonNull TaskListMvp.View view, @NonNull TaskListMvp.Navigator navigator) {

        mTaskRepository = checkNotNull(tasksRepository, "repository can not be null");
        mTaskView = checkNotNull(view, "tasks view can not be null");
        mListNavigator = checkNotNull(navigator);

        mTaskView.attachPresenter(this);

     /*
      addNewTask( new Task("task 1","desc 1"));
        addNewTask( new Task("task 2","desc 2"));
        addNewTask( new Task("task 3","desc 3"));
        addNewTask( new Task("task 4","desc 4"));
        */

    }

    @Override
    public void addNewTask(Task task) {
        mTaskRepository.insertTask(task);
        loadTask();
    }


    @Override
    public void loadTask() {
        mTaskRepository.getTasks(new TasksDataContract.LoadTaskCallBack() {
            @Override
            public void onDataNotAvailable() {

            }

            @Override
            public void onTasksLoaded(List<Task> taskList) {
                mTaskView.showTasks(taskList);
            }
        });
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddEditActivity.REQUEST_ADD_TASK == requestCode && android.app.Activity.RESULT_OK == resultCode) {
            mTaskView.showSuccessSavedMessage();
        }

    }

    @Override
    public void navigateToAddEditTask() {
        mListNavigator.navigateToAddEdit();
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }


}
