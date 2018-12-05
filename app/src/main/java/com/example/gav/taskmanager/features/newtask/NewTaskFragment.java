package com.example.gav.taskmanager.features.newtask;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.features.tasklist.Task;
import com.example.gav.taskmanager.features.tasklist.TaskListViewModel;


public class NewTaskFragment extends Fragment {

    private TextView tvPriority;
    private ImageButton ibAddTask;
    private EditText etTitle;
    private Priority currentPriority;
    private PriorityDialogFragment priorityDialogFragment;

    private TaskListViewModel viewModel;

    public static final String TAG = "NewTaskFragment";

    public static NewTaskFragment newInstance() {
        NewTaskFragment fragment = new NewTaskFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        addSpannable();
        initListeners();
    }

    private void initViews(View view) {
        tvPriority = view.findViewById(R.id.tvPriority);
        ibAddTask = view.findViewById(R.id.ibAddTask);
        etTitle = view.findViewById(R.id.etTitle);

        ibAddTask.setEnabled(false);

        viewModel = ViewModelProviders.of(this).get(TaskListViewModel.class);
    }

    private void initListeners() {
        ibAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPriority != null) {
                    Log.d(TAG, "Добавили новую задачу");
                    String taskName = etTitle.getText().toString();
                    Task task = new Task(taskName, currentPriority.getColor());
                    FragmentActivity activity = getActivity();

                    if (activity != null) {
                        viewModel.insertTask(activity, task);

                    }
                } else
                    Toast.makeText(getContext(), "Please, pick priority", Toast.LENGTH_SHORT).show();

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

        tvPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Нажали на приоритет");
                if (priorityDialogFragment == null)
                    priorityDialogFragment = PriorityDialogFragment.newInstance();

                priorityDialogFragment.show(getChildFragmentManager(), PriorityDialogFragment.TAG);

            }
        });
    }

    private void addSpannable() {
        Spannable text = new SpannableString(tvPriority.getText().toString());

        int color = getResources().getColor(R.color.black);
        text.setSpan(new ForegroundColorSpan(color), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPriority.setText(text);
    }


    public void onPriorityChosen(Priority priority) {
        this.currentPriority = priority;
        changePriorityTextView();
    }

    public void onInsertTask() {
        Activity activity = getActivity();
        if (activity != null)
            activity.finish();
    }

    private void changePriorityTextView() {
        if (currentPriority != null) {
            String text = getResources().getString(R.string.priority_marker) + " " + currentPriority.getTitle();
            Spannable spannable = new SpannableString(text);
            spannable.setSpan(new ForegroundColorSpan(currentPriority.getColor()), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPriority.setText(spannable);

        }
    }
}
