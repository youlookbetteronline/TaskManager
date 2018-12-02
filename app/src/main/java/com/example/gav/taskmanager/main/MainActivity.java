package com.example.gav.taskmanager.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.features.productivity.ProductivityFragment;

public class MainActivity extends AppCompatActivity implements ProductivityUpdateListener {
    private TabLayout tlTabs;
    private ViewPager vpTabs;
    private Toolbar appToolbar;


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

        final TabsFragmentAdapter adapter = new TabsFragmentAdapter(getSupportFragmentManager(), this);
        vpTabs.setAdapter(adapter);
        tlTabs.setupWithViewPager(vpTabs);

        setSupportActionBar(appToolbar);
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
}
