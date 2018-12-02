package com.example.gav.taskmanager.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.features.productivity.ProductivityFragment;
import com.example.gav.taskmanager.features.tasklist.TaskListFragment;

import java.util.HashMap;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private final HashMap<Integer, String> tabTitles = new HashMap<Integer, String>();
    private Context context;
    private ProductivityFragment productivityFragment;

    public TabsFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        initTabTitles();
    }

    private void initTabTitles() {
        tabTitles.put(0, context.getResources().getString(R.string.tasks));
        tabTitles.put(1, context.getResources().getString(R.string.productivity));
    }

    @Override
    public Fragment getItem(int i) {
        Fragment result = null;
        switch (i) {
            case 0: result = TaskListFragment.newInstance(); break;
            case 1:
                if (productivityFragment == null) {
                    productivityFragment = ProductivityFragment.newInstance();
                }
                result = productivityFragment; break;
            default: break;
        }
        return result;
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
