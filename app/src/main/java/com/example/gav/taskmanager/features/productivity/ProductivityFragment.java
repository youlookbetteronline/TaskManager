package com.example.gav.taskmanager.features.productivity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.features.tasklist.FinishTask;
import com.example.gav.taskmanager.features.tasklist.FinishTasksViewModel;
import com.example.gav.taskmanager.views.LineChartView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProductivityFragment extends Fragment {
    public static final String TAG = "ProductivityFragment";
    private TextView tvDoneTasks;
    private LineChartView lcChart;
    private FinishTasksViewModel finishTasksViewModel;

    public static ProductivityFragment newInstance() {
        ProductivityFragment fragment = new ProductivityFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productivity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvDoneTasks = view.findViewById(R.id.tvDoneTasks);
        lcChart = view.findViewById(R.id.lcvChart);

        String key = getString(R.string.done_tasks_count);

        SharedPreferences preferences = getActivity().getSharedPreferences(key, Context.MODE_PRIVATE);
        int defaultValue = 0;
        int resultValue = preferences.getInt(key, defaultValue);
        tvDoneTasks.setText(getString(R.string.done_tasks) + " " + resultValue);

        finishTasksViewModel = ViewModelProviders.of(this).get(FinishTasksViewModel.class);
        loadFinishTasksFromDb();
    }

    private void loadFinishTasksFromDb() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            finishTasksViewModel.getFinishTasks().observe(getActivity(), new Observer<List<FinishTask>>() {
                @Override
                public void onChanged(@Nullable List<FinishTask> finishTasks) {
                    int[] chartData = parseDataForChart(finishTasks);
                    lcChart.setChartData(chartData);
                }
            });
        }
    }

    private int[] parseDataForChart(List<FinishTask> finishTasks) {
        int[] result = new int[7];
        for (FinishTask finishTask : finishTasks) {
            int finishTaskDay = finishTask.getDay();
            int finishTaskCount = finishTask.getCount();
            int resultIndex = finishTaskDay - 1;
            if (resultIndex < 0)
                resultIndex = 6;
            result[resultIndex] += finishTaskCount;
        }
        return result;
    }

    public void updateProductivity(int value) {
        String key = getString(R.string.done_tasks_count);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            SharedPreferences preferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE);
            int defaultValue = 0;
            tvDoneTasks.setText(getString(R.string.done_tasks) + " " + value);
        }

    }

    public void onDeleteTask() {
        loadFinishTasksFromDb();
    }
}
