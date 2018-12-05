package com.example.gav.taskmanager.features.tasklist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.gav.taskmanager.database.AppDatabase;
import com.example.gav.taskmanager.features.newtask.InsertTaskListener;
import com.example.gav.taskmanager.features.newtask.PriorityDialogListener;
import com.example.gav.taskmanager.main.App;
import com.example.gav.taskmanager.main.DeleteTaskListener;

import java.util.List;

public class TaskListViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private final LiveData<List<Task>> tasks;

    public TaskListViewModel(@NonNull Application application) {
        super(application);
        appDatabase = ((App)getApplication()).getDatabase();
        tasks = appDatabase.taskDao().getAll();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void insertTask(Context context, Task task) {
        InsertTaskListener insertTaskListener;
        try {
            insertTaskListener = (InsertTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement InsertTaskListener");
        }
        new InsertTaskAsyncTask(insertTaskListener, appDatabase).execute(task);
    }

    public void deleteTask(Context context, Task task, int index) {
        DeleteTaskListener deleteTaskListener;
        try {
            deleteTaskListener = (DeleteTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DeleteTaskListener");
        }
        new DeleteTaskAsyncTask(deleteTaskListener, appDatabase, index).execute(task);
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private final AppDatabase db;
        private final DeleteTaskListener deleteTaskListener;
        private final int index;

        DeleteTaskAsyncTask(DeleteTaskListener deleteTaskListener, AppDatabase appDatabase, int index) {
            this.db = appDatabase;
            this.deleteTaskListener = deleteTaskListener;
            this.index = index;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            db.taskDao().delete(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            deleteTaskListener.onDeleteTask(index);
        }
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private AppDatabase db;
        private final InsertTaskListener insertTaskListener;

        InsertTaskAsyncTask(InsertTaskListener insertTaskListener, AppDatabase appDatabase) {
            this.db = appDatabase;
            this.insertTaskListener = insertTaskListener;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            db.taskDao().insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            insertTaskListener.onInsertTask();
        }
    }
}
