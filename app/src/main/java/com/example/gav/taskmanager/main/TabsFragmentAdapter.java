package com.example.gav.taskmanager.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.features.productivity.ProductivityFragment;
import com.example.gav.taskmanager.features.tasklist.TaskListFragment;

import java.util.HashMap;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private final HashMap<Integer, String> tabTitles = new HashMap<Integer, String>();
    private Context context;
    private ProductivityFragment productivityFragment;
    private TaskListFragment taskListFragment;

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
            case 0:
                if (taskListFragment == null) {
                    taskListFragment = TaskListFragment.newInstance();
                }
                result = taskListFragment; break;
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
