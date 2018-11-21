package com.example.gav.taskmanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.pojo.Task;

import java.util.ArrayList;
import java.util.Collection;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>{
    public ArrayList<Task> taskList = new ArrayList<>();
    private final OnTaskClickListener listener;
    private final Context context;

    public TaskListAdapter(Context context, ArrayList<Task> taskList, OnTaskClickListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, viewGroup, false);
        final TaskListViewHolder taskListViewHolder = new TaskListViewHolder(view);
        taskListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (taskListViewHolder.getAdapterPosition() != RecyclerView.NO_POSITION)
                    listener.onTaskClick(taskListViewHolder.getAdapterPosition());
            }
        });
        return taskListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder taskListViewHolder, int i) {
        if (i != RecyclerView.NO_POSITION)
            taskListViewHolder.bind(taskList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setItems(Collection<Task> tasks) {
        taskList.addAll(tasks);
        notifyDataSetChanged();
    }

    public void clearItems() {
        taskList.clear();
        notifyDataSetChanged();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvMarker;

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMarker = itemView.findViewById(R.id.tvMarker);
        }

        public void bind(final Task task, final OnTaskClickListener listener) {
            tvTitle.setText(task.getText());
            tvMarker.setTextColor(task.getPriority());
        }
    }

    public interface OnTaskClickListener {
        void onTaskClick(int index);
    }
}
