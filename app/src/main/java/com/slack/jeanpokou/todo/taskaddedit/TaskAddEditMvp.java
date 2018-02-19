package com.slack.jeanpokou.todo.taskaddedit;


import com.slack.jeanpokou.todo.BaseNavigator;
import com.slack.jeanpokou.todo.BasePresenter;
import com.slack.jeanpokou.todo.BaseView;

public interface TaskAddEditMvp {

    interface View extends BaseView <Presenter>{
        void showEmptyTaskError();

    }

    interface Presenter extends BasePresenter {

        boolean isDataMissing();

        void populateTask();

        void saveTask(String title, String description);

    }

    interface Navigator extends BaseNavigator {
        void navigateToList();
    }


}
