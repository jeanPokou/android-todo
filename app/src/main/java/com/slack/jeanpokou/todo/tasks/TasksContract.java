package com.slack.jeanpokou.todo.tasks;


import com.slack.jeanpokou.todo.BasePresenter;
import com.slack.jeanpokou.todo.BaseView;
import com.slack.jeanpokou.todo.data.Task;

import java.util.List;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

        void showTasks(List<Task> listTasks);

    }

    interface Presenter extends BasePresenter {

        void loadTask();
        void addNewTask(Task task);

    }
}
