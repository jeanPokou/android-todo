package com.slack.jeanpokou.todo.taskaddedit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditNavigator implements TaskAddEditMvp.Navigator {

    @NonNull private final Activity activity ;

    public AddEditNavigator(@NonNull  Activity activity) {
        this.activity = checkNotNull(activity , "ListActivity can not be null");
    }

    @Override
    public void navigateToList(Long id) {
        Intent intent = new Intent();
        intent.putExtra("TASK_INSERTED_ID", id);
        activity.setResult(Activity.RESULT_OK,intent);
        activity.finish();
    }
}
