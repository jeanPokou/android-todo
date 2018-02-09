package com.slack.jeanpokou.todo.tasks;


import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.data.source.TasksRepository;
import java.util.List;

public class TasksPresenter implements TasksContract.Presenter {

    private final TasksRepository mTaskRepository;

    private final TasksContract.View mView;

    public TasksPresenter(@NonNull TasksRepository tasksRepository, @NonNull TasksContract.View view) {
        mTaskRepository = checkNotNull(tasksRepository, "repository can not be null");
        mView = checkNotNull(view, "tasks view can not be null");

        mView.attach(this);

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
    public void addNewTask() {
        mView.showAddTask();
    }

    @Override
    public void loadTask() {
        mTaskRepository.getTasks(new TasksDataContract.LoadTaskCallBack() {
            @Override
            public void onDataNotAvailable() {

            }

            @Override
            public void onTasksLoaded(List<Task> taskList) {
                mView.showTasks(taskList);
            }
        });
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }


}
