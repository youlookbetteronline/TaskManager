package com.example.gav.taskmanager.features.newtask;

public class Priority {
    private final int color;
    private final String title;

    public Priority(int color, String title) {
        this.color = color;
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}
