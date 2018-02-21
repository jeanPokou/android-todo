package com.slack.jeanpokou.todo.tasklist;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.slack.jeanpokou.todo.taskaddedit.AddEditActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class ListNavigator implements TaskListMvp.Navigator {

    @NonNull private final ListFragment fragment;

    public ListNavigator( @NonNull ListFragment fragment) {
        this.fragment = checkNotNull(fragment, "task fragment can not be null");
    }

    @Override
    public void navigateToAddEdit() {
        Intent intent = new Intent(fragment.getContext(), AddEditActivity.class);
        fragment.startActivityForResult(intent,AddEditActivity.REQUEST_ADD_TASK);

    }
}
