package com.example.gav.taskmanager.features.tasklist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.features.newtask.NewTaskActivity;
import com.example.gav.taskmanager.features.newtask.PriorityDialogFragment;
import com.example.gav.taskmanager.database.DatabaseHelper;
import com.example.gav.taskmanager.main.ProductivityUpdateListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TaskListFragment extends Fragment {

    private RecyclerView rvTasks;
    private LinearLayout llEmptyTasks;
    private TaskListAdapter taskAdapter;
    private FloatingActionButton fabAddTask;

    public static final int NEW_TASK_ACTIVITY = 001;

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    private void initViews(View view) {
        rvTasks = view.findViewById(R.id.rvTasks);
        llEmptyTasks = view.findViewById(R.id.llEmptyTasks);
        fabAddTask = view.findViewById(R.id.favAddTask);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTasks.setLayoutManager(layoutManager);
        taskAdapter = new TaskListAdapter(getContext(), getTasks(), new TaskListAdapter.OnTaskClickListener() {
            @Override public void onTaskClick(int index) {
                //Toast.makeText(MainActivity.this, "Item Clicked", Toast.LENGTH_LONG).show();
                //RecyclerView.ViewHolder viewHolderForAdapterPosition = rvTasks.findViewHolderForAdapterPosition(index);
                //viewHolderForAdapterPosition.itemView.setBackgroundColor(0xFF000fff);
            }
        });
        rvTasks.setAdapter(taskAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTasks.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        rvTasks.addItemDecoration(dividerItemDecoration);

        swipeDelete();
    }

    private void checkVisibilityViews() {
        if (rvTasks.getAdapter().getItemCount() != 0) {
            rvTasks.setVisibility(View.VISIBLE);
            llEmptyTasks.setVisibility(View.INVISIBLE);
        }
        else {
            llEmptyTasks.setVisibility(View.VISIBLE);
            rvTasks.setVisibility(View.INVISIBLE);
        }
    }

    private void swipeDelete() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                if (viewHolder instanceof TaskListAdapter.TaskListViewHolder) {
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        DatabaseHelper.getDatabase(activity).taskDao().delete(taskAdapter.taskList.get(viewHolder.getAdapterPosition()));
                        taskAdapter.removeItem(viewHolder.getAdapterPosition());
                        checkVisibilityViews();
                        addToDone();

                    }
                }
            }
        };
        new ItemTouchHelper(callback).attachToRecyclerView(rvTasks);
    }

    private void addToDone() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int month = calendar.get(Calendar.MONTH);

            String key = getString(R.string.month_number) + " " + month;
            SharedPreferences preferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE);
            int defaultValue = 0;
            int value = preferences.getInt(key, defaultValue);

            value++;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            editor.commit();

            if (activity instanceof ProductivityUpdateListener) {
                ((ProductivityUpdateListener) activity).onProductivityUpdate(50);
            }
        }

    }

    private void initListeners() {
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.startActivityForResult( new Intent(activity, NewTaskActivity.class), NEW_TASK_ACTIVITY);
                }

            }
        });
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            int priority = PriorityDialogFragment.PRIORITY_COLOR_LIST[new Random().nextInt(PriorityDialogFragment.PRIORITY_COLOR_LIST.length)];
            result.add(new Task("Выполнить задание №" + i, priority));
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            taskAdapter.setItems(DatabaseHelper.getDatabase(activity).taskDao().getAll());
        }
        checkVisibilityViews();
    }
}
