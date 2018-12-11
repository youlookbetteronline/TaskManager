package com.example.gav.taskmanager.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.gav.taskmanager.features.tasklist.FinishTask;

import java.util.List;

@Dao
public interface FinishTaskDao {

    @Query("SELECT * FROM FinishTask")
    LiveData<List<FinishTask>> getAll();

    @Insert
    void insert(FinishTask finishTask);

}
