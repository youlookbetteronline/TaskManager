package com.example.gav.taskmanager.database;

import android.app.Activity;
import androidx.room.Room;

public class DatabaseHelper {
    public static AppDatabase getDatabase(Activity activity) {
        return Room.databaseBuilder(activity, AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();
    }
}
