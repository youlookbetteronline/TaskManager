package com.example.gav.taskmanager.features.newtask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.gav.taskmanager.R;

import java.util.ArrayList;
import java.util.List;

public class PriorityDialogFragment extends DialogFragment {
    RecyclerView rvDialogPriority;
    TextView tvCancel;

    public static final String TAG = "PriorityDialogFragment";
    public static final int SIMPLE_PRIORITY = 0xfffdd735;
    public static final int IMPORTANT_PRIORITY = 0xff21897b;
    public static final int VERY_IMPORTANT_PRIORITY = 0xff2396f3;
    public static final int URGENT_PRIORITY = 0xfff44336;
    public static final int[] PRIORITY_COLOR_LIST = new int[]{SIMPLE_PRIORITY, IMPORTANT_PRIORITY, VERY_IMPORTANT_PRIORITY, URGENT_PRIORITY};
    public static final String[] PRIORITY_TITLE_LIST = new String[]{"Simple", "Important", "Very important", "Urgent"};
    private PriorityDialogListener priorityDialogListener;

    public static PriorityDialogFragment newInstance() {
        PriorityDialogFragment fragment = new PriorityDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_priority, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        initListeners();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (context instanceof PriorityDialogListener) {
                priorityDialogListener = (PriorityDialogListener) context;
            } else {
                throw new UnsupportedOperationException("Activity должно реализовать интерфейс PriorityDialogListener");
            }
        }
    }

    private void initViews(View view) {
        rvDialogPriority = view.findViewById(R.id.rvDialogPriority);
        tvCancel = view.findViewById(R.id.tvCancel);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvDialogPriority.setLayoutManager(layoutManager);
        PriorityDialogFragmentAdapter adapter = new PriorityDialogFragmentAdapter(getContext(), getPriorityList(),
                new PriorityDialogFragmentAdapter.OnPriorityClickListener() {
                    @Override
                    public void onPriorityClick(Priority priority) {
                        priorityDialogListener.onPriorityChosen(priority);
                        dismiss();
                    }
                });
        rvDialogPriority.setAdapter(adapter);
        rvDialogPriority.addItemDecoration(new DividerItemDecoration(rvDialogPriority.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation()));
    }

    private List<Priority> getPriorityList() {
        List<Priority> result = new ArrayList<>();
        for (int i = 0; i < PRIORITY_COLOR_LIST.length; i++) {
            result.add(new Priority(PRIORITY_COLOR_LIST[i], PRIORITY_TITLE_LIST[i]));
        }
        return result;
    }

    private void initListeners() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
