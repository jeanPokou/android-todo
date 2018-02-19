package com.slack.jeanpokou.todo.taskdetail;

import com.slack.jeanpokou.todo.BaseNavigator;
import com.slack.jeanpokou.todo.BasePresenter;
import com.slack.jeanpokou.todo.BaseView;

public interface TaskDetailMvp {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }

    interface Navigator extends BaseNavigator {


    }

}
