package com.slack.jeanpokou.todo.tasks;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.util.Injection;

public class TasksActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        //setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //setup the navigation drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Inserting the Fragment
        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.content_frame, tasksFragment).commit();

        }

        // Create the Presenter
        mTasksPresenter = new TasksPresenter(Injection.provideTaskRepository(getApplicationContext()), tasksFragment);
    }

}
