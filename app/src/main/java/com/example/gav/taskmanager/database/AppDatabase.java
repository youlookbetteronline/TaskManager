package com.example.gav.taskmanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gav.taskmanager.features.tasklist.FinishTask;
import com.example.gav.taskmanager.features.tasklist.Task;

@Database(entities = {Task.class, FinishTask.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract FinishTaskDao finishTaskDao();
}
