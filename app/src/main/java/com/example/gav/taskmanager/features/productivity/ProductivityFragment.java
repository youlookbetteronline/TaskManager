package com.example.gav.taskmanager.features.productivity;


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

import java.util.Calendar;
import java.util.Date;

public class ProductivityFragment extends Fragment {
    public static final String TAG = "ProductivityFragment";
    TextView tvDoneTasks;
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int month = calendar.get(Calendar.MONTH);

        String key = getString(R.string.month_number) + " " + month;

        SharedPreferences preferences = getActivity().getSharedPreferences(key, Context.MODE_PRIVATE);
        int defaultValue = 0;
        int resultValue = preferences.getInt(key, defaultValue);
        tvDoneTasks.setText(getString(R.string.done_tasks) + " " + resultValue);
    }

    public void updateProductivity(int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int month = calendar.get(Calendar.MONTH);
        String key = getString(R.string.month_number) + " " + month;
        FragmentActivity activity = getActivity();
        if (activity != null) {
            SharedPreferences preferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE);
            int defaultValue = 0;
            tvDoneTasks.setText(getString(R.string.done_tasks) + " " + value);
        }

    }
}
