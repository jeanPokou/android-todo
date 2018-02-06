package com.slack.jeanpokou.todo.tasks;


import com.slack.jeanpokou.todo.BasePresenter;
import com.slack.jeanpokou.todo.BaseView;
import com.slack.jeanpokou.todo.data.Task;
import java.util.List;

public interface TasksContract {

    interface View extends BaseView<Presenter> {
        // show view for adding new task
        void showAddTask();

        void showTasks(List<Task> listTasks);

    }

    interface Presenter extends BasePresenter {

        void addNewTask(Task task);

        void addNewTask();

        void loadTask();
    }
}
