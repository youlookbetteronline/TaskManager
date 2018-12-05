package com.example.gav.taskmanager.main;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.gav.taskmanager.database.AppDatabase;

public class App extends Application {

    private AppDatabase database;

    public AppDatabase getDatabase() {
        if (database == null) {
            database = Room
                    .databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
