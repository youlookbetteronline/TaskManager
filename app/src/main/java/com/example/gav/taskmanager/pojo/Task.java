package com.example.gav.taskmanager.pojo;

public class Task {
    private String text;
    private int priority;

    public static final int SIMPLE_PRIORITY = 0xffffff00;
    public static final int IMPORTANT_PRIORITY = 0xff00ff11;
    public static final int VERY_IMPORTANT_PRIORITY = 0xff65006e;
    public static final int URGENT_PRIORITY = 0xffff0000;
    public static final int[] PRIORITY_COLOR_LIST = new int[]{SIMPLE_PRIORITY, IMPORTANT_PRIORITY, VERY_IMPORTANT_PRIORITY, URGENT_PRIORITY};


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
