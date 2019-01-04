package com.example.gav.taskmanager.main;

import android.app.Application;
import androidx.room.Room;
import android.content.Context;

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

    public static App getApp(Context context) {
        return ((App) context.getApplicationContext());
    }
}
