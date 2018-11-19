package com.example.gav.taskmanager;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.gav.taskmanager.adapter.TaskListAdapter;
import com.example.gav.taskmanager.pojo.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvTasks;
    private TaskListAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //setTasksToAdapter();
    }

    private void initViews() {
        rvTasks = findViewById(R.id.rvTasks);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvTasks.setLayoutManager(layoutManager);
        taskAdapter = new TaskListAdapter(this, getTasks(), new TaskListAdapter.OnTaskClickListener() {
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
    }

    private void setTasksToAdapter() {
        taskAdapter.setItems(getTasks());
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            result.add(new Task("Выполнить задание №" + i, 0xffffff/(i+1)*10000));
        }
        return result;
    }
}
