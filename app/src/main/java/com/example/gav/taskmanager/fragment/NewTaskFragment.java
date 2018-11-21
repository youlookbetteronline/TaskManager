package com.example.gav.taskmanager.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.activity.NewTaskActivity;

public class NewTaskFragment extends Fragment {

    private TextView tvPriority;
    private ImageButton ibAddTask;
    private EditText etTitle;

    public static NewTaskFragment newInstance() {
        NewTaskFragment fragment = new NewTaskFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        initViews(view);
        addSpannable();
        initListeners();

        return view;
    }

    private void initViews(View view) {
        tvPriority = view.findViewById(R.id.tvPriority);
        ibAddTask = view.findViewById(R.id.ibAddTask);
        etTitle = view.findViewById(R.id.etTitle);

        ibAddTask.setEnabled(false);
    }

    private void addSpannable() {
        Spannable text = new SpannableString(tvPriority.getText().toString());
        text.setSpan(new ForegroundColorSpan(Color.RED), 0, 1,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPriority.setText(text);
    }

    private void initListeners() {
        ibAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Add new item success", Toast.LENGTH_SHORT).show();
            }
        });

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ibAddTask.setEnabled(!TextUtils.isEmpty(s));
            }
        });
    }

}
