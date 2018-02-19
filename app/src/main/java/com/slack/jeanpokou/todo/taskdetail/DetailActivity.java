package com.slack.jeanpokou.todo.taskdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.slack.jeanpokou.todo.R;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASKID = "TASK_ID" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    }
}
