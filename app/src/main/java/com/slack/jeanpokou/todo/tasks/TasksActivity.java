package com.slack.jeanpokou.todo.tasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.util.Injection;

public class TasksActivity extends AppCompatActivity {

    private TasksPresenter mTasksPresenter;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tasks);

            // Inserting the Fragment
            TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame) ;
            if(tasksFragment == null) {
                tasksFragment = TasksFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.content_frame,tasksFragment).commit();

            }

            // Create the Presenter
            mTasksPresenter = new TasksPresenter(Injection.provideTaskRepository(getApplicationContext()) ,tasksFragment);
        }

    }
