package com.slack.jeanpokou.todo.tasklist;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.util.Injection;

public class ListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private ListPresenter listPresenter;

    private ListNavigator listNavigator;


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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Instantiating ListFragment
        ListFragment listFragment = (ListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);
        if (listFragment == null) {
            listFragment = ListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.content_frame, listFragment).commit();

        }

        // Create the ListPresenter
        listPresenter = new ListPresenter(
                Injection.provideTaskRepository(getApplicationContext())
                , listFragment
                , new ListNavigator(listFragment)
        );
    }

}
