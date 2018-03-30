package com.slack.jeanpokou.todo.taskdetail;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.util.Injection;

public class DetailActivity extends AppCompatActivity {

    public static final int REQUEST_TASK_DETAIL = 1;
    public static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // get the id of the task passed
        String taskId = getIntent().getStringExtra("TASK_ID");
        Log.d(TAG, "clicked on task id " + taskId);

        // Instantiating DetailFragment
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance(taskId);
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, detailFragment).commit();
        }

        DetailNavigator detailNavigator = new DetailNavigator();

        // Create Detail Presenter
        DetailPresenter presenter = new DetailPresenter( taskId,
                Injection.provideTaskRepository(getApplicationContext()),
                detailFragment,
                detailNavigator
        );


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true;
    }
}
