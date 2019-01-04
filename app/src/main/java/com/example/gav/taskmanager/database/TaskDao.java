package com.example.gav.taskmanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gav.taskmanager.features.tasklist.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM Task")
    Flowable<List<Task>> getAllReactively();

    @Insert
    void insert(Task task);

    @Insert
    Completable insertReactively(Task ...task);

    @Delete
    void delete(Task task);

    @Delete
    Completable deleteReactively(Task ...task);
}
