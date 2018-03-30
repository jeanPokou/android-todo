package com.slack.jeanpokou.todo.taskdetail;

import com.slack.jeanpokou.todo.BaseNavigator;
import com.slack.jeanpokou.todo.BasePresenter;
import com.slack.jeanpokou.todo.BaseView;
import com.slack.jeanpokou.todo.data.Task;

public interface TaskDetailMvp {

    interface View extends BaseView<Presenter> {

        void showNoTask();

        void setLoadingIndicator(boolean b);

        void showTask(Task task);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

    }

    interface Navigator extends BaseNavigator {


    }

}
