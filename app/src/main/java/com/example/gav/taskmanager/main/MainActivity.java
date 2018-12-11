package com.example.gav.taskmanager.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.features.newtask.NewTaskActivity;
import com.example.gav.taskmanager.features.productivity.ProductivityFragment;
import com.example.gav.taskmanager.features.tasklist.TaskListFragment;

public class MainActivity extends AppCompatActivity implements ProductivityUpdateListener, DeleteTaskListener {
    private TabLayout tlTabs;
    private ViewPager vpTabs;
    private Toolbar appToolbar;
    private FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        vpTabs = findViewById(R.id.vpTabs);
        tlTabs = findViewById(R.id.tlTabs);
        appToolbar = findViewById(R.id.appToolbar);
        fabAddTask = findViewById(R.id.favAddTask);

        final TabsFragmentAdapter adapter = new TabsFragmentAdapter(getSupportFragmentManager(), this);
        vpTabs.setAdapter(adapter);
        tlTabs.setupWithViewPager(vpTabs);

        setSupportActionBar(appToolbar);

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult( new Intent(MainActivity.this, NewTaskActivity.class), TaskListFragment.NEW_TASK_ACTIVITY);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.actionWriteDeveloper:
                writeToDeveloper();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void writeToDeveloper() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","abc@gmail.com", null));
        startActivity(emailIntent);
    }

    @Override
    public void onProductivityUpdate(int value) {
        TabsFragmentAdapter adapter = (TabsFragmentAdapter) vpTabs.getAdapter();
        ProductivityFragment fragment = ((ProductivityFragment) adapter.getItem(1));
        fragment.updateProductivity(value);
    }

    @Override
    public void onDeleteTask(int index) {
        TabsFragmentAdapter adapter = (TabsFragmentAdapter) vpTabs.getAdapter();
        TaskListFragment taskListFragment = ((TaskListFragment) adapter.getItem(0));
        ProductivityFragment productivityFragment = ((ProductivityFragment) adapter.getItem(1));
        if (taskListFragment != null) {
            taskListFragment.onDeleteTask(index);
        }

    }

    @Override
    public void onDeleteTask() {
        TabsFragmentAdapter adapter = (TabsFragmentAdapter) vpTabs.getAdapter();
        ProductivityFragment productivityFragment = ((ProductivityFragment) adapter.getItem(1));

        if (productivityFragment != null) {
            productivityFragment.onDeleteTask();
        }

    }
}
