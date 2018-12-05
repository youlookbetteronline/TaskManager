package com.example.gav.taskmanager.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.gav.taskmanager.features.tasklist.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAll();

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);
}
