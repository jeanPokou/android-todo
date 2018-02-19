package com.slack.jeanpokou.todo.taskaddedit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditNavigator implements TaskAddEditMvp.Navigator {

    @NonNull private final Activity activity ;

    public AddEditNavigator(@NonNull  Activity activity) {
        this.activity = checkNotNull(activity , "ListActivity can not be null");
    }

    @Override
    public void navigateToList() {
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }
}
