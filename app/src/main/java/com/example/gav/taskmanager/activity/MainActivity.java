package com.example.gav.taskmanager.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.adapter.TaskListFragmentAdapter;

public class MainActivity extends AppCompatActivity {
    private TabLayout tlTabs;
    private ViewPager vpTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initFragment();
    }

    private void initViews() {
        vpTabs = findViewById(R.id.vpTabs);
        tlTabs = findViewById(R.id.tlTabs);

        vpTabs.setAdapter(new TaskListFragmentAdapter(getSupportFragmentManager(), this));
        tlTabs.setupWithViewPager(vpTabs);
    }

    private void initFragment() {
    }
}
