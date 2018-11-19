package com.example.gav.taskmanager.pojo;

public class Task {
    private String text;
    private int priority;

    public Task(String text, int priority) {
        this.text = text;
        this.priority = priority;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
