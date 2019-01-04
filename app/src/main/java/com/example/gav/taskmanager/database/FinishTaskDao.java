package com.example.gav.taskmanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;

import com.example.gav.taskmanager.features.tasklist.FinishTask;
import com.example.gav.taskmanager.features.tasklist.Task;

import java.util.List;

@Dao
public interface FinishTaskDao {

    @Query("SELECT * FROM FinishTask")
    LiveData<List<FinishTask>> getAll();

    @Insert
    void insert(FinishTask finishTask);

    @Insert
    Completable insertReactively(FinishTask...finishTask);

}
