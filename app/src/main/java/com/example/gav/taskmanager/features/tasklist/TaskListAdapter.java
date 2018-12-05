package com.example.gav.taskmanager.features.tasklist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.gav.taskmanager.R;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>{
    public List<Task> taskList;
    private final OnTaskClickListener listener;

    public TaskListAdapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
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

    public void setItems(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    public void clearItems() {
        taskList.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int i) {
        this.taskList.remove(i);
        notifyItemRemoved(i);
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
