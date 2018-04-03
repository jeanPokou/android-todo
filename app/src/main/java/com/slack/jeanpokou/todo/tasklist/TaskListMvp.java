package com.slack.jeanpokou.todo.tasklist;


import com.slack.jeanpokou.todo.BaseNavigator;
import com.slack.jeanpokou.todo.BasePresenter;
import com.slack.jeanpokou.todo.BaseView;
import com.slack.jeanpokou.todo.data.Task;
import java.util.List;

public interface TaskListMvp {


    interface View extends BaseView<Presenter> {

        void showTaskDetailsUi(String taskId) ;

        void showTasks(List<Task> listTasks);

        void showSuccessSavedTasks();

        void showErrorSavedTasks();

        void showSuccessDeleteTask();
    }

    interface Presenter extends BasePresenter {

        void addNewTask(Task task);

        void result(int requestCode , int resultCode);

        void navigateToAddEdit();

        void deleteTaskById(String taskId);

        void navigateToDetail(String taskId);

        void completeTask(Task task);
    }

    interface Navigator extends BaseNavigator {

        void navigateToAddEdit();

        void navigateToDetail(String taskId);
    }
}
