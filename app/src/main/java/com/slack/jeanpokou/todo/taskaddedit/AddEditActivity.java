package com.slack.jeanpokou.todo.taskaddedit;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.util.Injection;

public class AddEditActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    private AddEditTaskPresenter presenter;
    private  AddEditNavigator navigator;

    private String taskId;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedit_task_edit);

        // setup toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        // attachPresenter fragment to activity
        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (addEditTaskFragment == null) {
            addEditTaskFragment = AddEditTaskFragment.newInstance();

            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, addEditTaskFragment).commit();
        }


        // get Intent with extra  sent from TaskActivity
        String taskId = getIntent().getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID);
        setToolBarTitle(taskId);

        /**
         * Use presenter and attaching ListFragment, Repository and TaskId
         */

        navigator = new AddEditNavigator(this);

        presenter = new AddEditTaskPresenter(
                taskId
                , addEditTaskFragment
                , Injection.provideTaskRepository(getApplicationContext())
                , navigator
                , false
        );
    }

    private void setToolBarTitle(String taskId) {
        if (taskId == null) {
            mActionBar.setTitle("NEW TASK");
        } else {
            mActionBar.setTitle("EDIT TASK");
        }
    }
}
