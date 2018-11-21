package com.example.gav.taskmanager.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.adapter.TaskListAdapter;
import com.example.gav.taskmanager.pojo.Task;

import java.util.ArrayList;
import java.util.Random;

public class TaskListFragment extends Fragment {

    private RecyclerView rvTasks;
    private TaskListAdapter taskAdapter;
    private FloatingActionButton fabAddTask;

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        initViews(view);
        //setTasksToAdapter();

        return view;
    }

    private void initViews(View view) {
        rvTasks = view.findViewById(R.id.rvTasks);
        fabAddTask = view.findViewById(R.id.favAddTask);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTasks.setLayoutManager(layoutManager);
        taskAdapter = new TaskListAdapter(getContext(), getTasks(), new TaskListAdapter.OnTaskClickListener() {
            @Override public void onTaskClick(int index) {
                //Toast.makeText(MainActivity.this, "Item Clicked", Toast.LENGTH_LONG).show();
                RecyclerView.ViewHolder viewHolderForAdapterPosition = rvTasks.findViewHolderForAdapterPosition(index);
                //viewHolderForAdapterPosition.itemView.setBackgroundColor(0xFF000fff);
            }
        });
        rvTasks.setAdapter(taskAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTasks.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        rvTasks.addItemDecoration(dividerItemDecoration);

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTaskFragment newTaskFragment = NewTaskFragment.newInstance();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(getId(), newTaskFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void setTasksToAdapter() {
        taskAdapter.setItems(getTasks());
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            int priority = Task.PRIORITY_COLOR_LIST[new Random().nextInt(Task.PRIORITY_COLOR_LIST.length)];
            result.add(new Task("Выполнить задание №" + i, priority));
        }
        return result;
    }


}
