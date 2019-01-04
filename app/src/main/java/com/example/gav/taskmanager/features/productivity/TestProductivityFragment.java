package com.example.gav.taskmanager.features.productivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gav.taskmanager.views.DaysOfWeekView;

public class TestProductivityFragment extends Fragment {
    public static TestProductivityFragment newInstance() {
        TestProductivityFragment fragment = new TestProductivityFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new DaysOfWeekView(inflater.getContext());//super.onCreateView(inflater, container, savedInstanceState);
    }
}
