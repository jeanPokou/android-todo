package com.slack.jeanpokou.todo.tasklist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.slack.jeanpokou.todo.taskaddedit.AddEditActivity;
import com.slack.jeanpokou.todo.taskaddedit.AddEditTaskFragment;
import com.slack.jeanpokou.todo.taskaddedit.TaskAddEditMvp;
import static com.google.common.base.Preconditions.checkNotNull;

public class ListNavigator implements TaskListMvp.Navigator {

    @NonNull private final ListFragment mFragment;

    public ListNavigator( @NonNull ListFragment fragment) {
        this.mFragment = checkNotNull(fragment, "task fragment can not be null");
    }

    @Override
    public void navigateToAddEdit() {
        Intent intent = new Intent(mFragment.getContext(), AddEditActivity.class);
        mFragment.startActivityForResult(intent,AddEditActivity.REQUEST_ADD_TASK);

    }
}
