package com.example.gav.taskmanager.features.tasklist;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.example.gav.taskmanager.database.AppDatabase;
import com.example.gav.taskmanager.main.App;
import com.example.gav.taskmanager.main.DeleteTaskListener;

import java.util.List;

public class FinishTasksViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private final LiveData<List<FinishTask>> finishTasks;

    public FinishTasksViewModel(@NonNull Application application) {
        super(application);
        appDatabase = ((App)getApplication()).getDatabase();
        finishTasks = appDatabase.finishTaskDao().getAll();
    }

    public LiveData<List<FinishTask>> getFinishTasks() {
        return finishTasks;
    }

    public void insertTask(Context context, FinishTask finishTask) {
        DeleteTaskListener deleteTaskListener;
        try {
            deleteTaskListener = (DeleteTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement InsertTaskListener");
        }
        new InsertFinishTaskListener(deleteTaskListener, appDatabase).execute(finishTask);
    }

    private static class InsertFinishTaskListener extends AsyncTask<FinishTask, Void, Void> {

        private AppDatabase db;
        private final DeleteTaskListener deleteTaskListener;

        InsertFinishTaskListener(DeleteTaskListener deleteTaskListener, AppDatabase appDatabase) {
            this.db = appDatabase;
            this.deleteTaskListener = deleteTaskListener;
        }

        @Override
        protected Void doInBackground(final FinishTask... params) {
            db.finishTaskDao().insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
