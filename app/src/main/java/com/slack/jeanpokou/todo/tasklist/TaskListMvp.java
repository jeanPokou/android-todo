package com.slack.jeanpokou.todo.tasklist;


import com.slack.jeanpokou.todo.BaseNavigator;
import com.slack.jeanpokou.todo.BasePresenter;
import com.slack.jeanpokou.todo.BaseView;
import com.slack.jeanpokou.todo.data.Task;
import java.util.List;

public interface TaskListMvp {

    interface Model {

    }

    interface View extends BaseView<Presenter> {

        void showTaskDetailsUi(String taskId) ;

        void showTasks(List<Task> listTasks);

        void showTaskMarkedActive(Task task);


        void showSuccessSavedMessage();
    }

    interface Presenter extends BasePresenter {

        void addNewTask(Task task);

        void loadTask();

        void result(int requestCode , int resultCode);

        void navigateToAddEditTask();
    }

    interface Navigator extends BaseNavigator {

        void navigateToAddEdit();

    }
}
