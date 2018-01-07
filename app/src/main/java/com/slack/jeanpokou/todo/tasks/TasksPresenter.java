package com.slack.jeanpokou.todo.tasks;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.data.source.TasksRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksPresenter implements TasksContract.Presenter {

    private TasksContract.View mView;
    private TasksRepository mTaskRepository;

    public TasksPresenter(@NonNull TasksRepository tasksRepository, @NonNull TasksContract.View view) {
        mTaskRepository = checkNotNull(tasksRepository , "repository can not be null");
        mView = checkNotNull(view ,"tasks view can not be null");

        mView.setPresenter(this);

     /*
      addNewTask( new Task("task 1","desc 1"));
        addNewTask( new Task("task 2","desc 2"));
        addNewTask( new Task("task 3","desc 3"));
        addNewTask( new Task("task 4","desc 4"));
        */

    }



    @Override
    public void start() {
    }

    @Override
    public void loadTask() {
        mTaskRepository.getTasks(new TasksDataContract.LoadTaskCallBack() {
            @Override
            public void onTasksLoaded(List<Task> taskList) {
                mView.showTasks(taskList);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addNewTask(Task task) {
        mTaskRepository.insertTask(task);
        loadTask();

    }


}
