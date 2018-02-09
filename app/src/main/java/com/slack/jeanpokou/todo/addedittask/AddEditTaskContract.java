package com.slack.jeanpokou.todo.addedittask;


import com.slack.jeanpokou.todo.BasePresenter;
import com.slack.jeanpokou.todo.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView {

        void showEmptyTaskError();

        void showTaskList();



    }

    interface Presenter extends BasePresenter {

        boolean isDataMissing();

        void populateTask();

        void saveTask(String title, String description);

    }


}
