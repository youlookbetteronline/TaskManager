package com.example.gav.taskmanager.features.tasklist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gav.taskmanager.R;
import com.example.gav.taskmanager.database.AppDatabase;
import com.example.gav.taskmanager.main.App;
import com.example.gav.taskmanager.main.ProductivityUpdateListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class TaskListFragment extends Fragment {

    private RecyclerView rvTasks;
    private LinearLayout llEmptyTasks;
    private TaskListAdapter taskAdapter;
    private TaskListViewModel taskListViewModel;
    private FinishTasksViewModel finishTasksViewModel;

    private SwipeRefreshLayout srRefreshTasks;
    CompositeDisposable compositeDisposable;

    public static final int NEW_TASK_ACTIVITY = 001;

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    private void initViews(View view) {
        rvTasks = view.findViewById(R.id.rvTasks);
        llEmptyTasks = view.findViewById(R.id.llEmptyTasks);
        srRefreshTasks = view.findViewById(R.id.srRefreshTasks);
        compositeDisposable = new CompositeDisposable();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTasks.setLayoutManager(layoutManager);
        taskAdapter = new TaskListAdapter(Collections.emptyList(), new TaskListAdapter.OnTaskClickListener() {
            @Override public void onTaskClick(int index) {
                //Toast.makeText(MainActivity.this, "Item Clicked", Toast.LENGTH_LONG).show();
                //RecyclerView.ViewHolder viewHolderForAdapterPosition = rvTasks.findViewHolderForAdapterPosition(index);
                //viewHolderForAdapterPosition.itemView.setBackgroundColor(0xFF000fff);
            }
        });
        rvTasks.setAdapter(taskAdapter);

        taskListViewModel = ViewModelProviders.of(this).get(TaskListViewModel.class);
        finishTasksViewModel = ViewModelProviders.of(this).get(FinishTasksViewModel.class);
        //loadTasksFromDb();
        loadTasksFromDbViaRxJava();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTasks.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        rvTasks.addItemDecoration(dividerItemDecoration);
        showHideWhenScroll();
        swipeDelete();
    }

    private void showHideWhenScroll() {
        final FloatingActionButton fab = getActivity().findViewById(R.id.favAddTask);
        rvTasks.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {

                    fab.hide();
                }
                else fab.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void checkVisibilityViews() {
        if (rvTasks.getAdapter().getItemCount() != 0) {
            rvTasks.setVisibility(View.VISIBLE);
            llEmptyTasks.setVisibility(View.INVISIBLE);
        }
        else {
            llEmptyTasks.setVisibility(View.VISIBLE);
            rvTasks.setVisibility(View.INVISIBLE);
        }
    }

    private void swipeDelete() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                if (viewHolder instanceof TaskListAdapter.TaskListViewHolder) {
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        int currentDay = getCurrentDay();
                        int countFinishTask = 1;
                        //taskListViewModel.deleteTask(activity, taskAdapter.taskList.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                        //finishTasksViewModel.insertTask(activity, new FinishTask(currentDay,countFinishTask));
                        deleteTaskViaRxJava(activity, taskAdapter.taskList.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                        insertFinishTaskViaRxJava(activity, new FinishTask(currentDay,countFinishTask));
                    }
                }
            }
        };
        new ItemTouchHelper(callback).attachToRecyclerView(rvTasks);
    }

    private void deleteTaskViaRxJava(FragmentActivity activity, Task task, int adapterPosition) {
        if (activity != null) {
            final AppDatabase db = App.getApp(activity).getDatabase();
            compositeDisposable.add(db.taskDao().deleteReactively(task)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->onDeleteTask(adapterPosition))
            );
        }
    }

    private void insertFinishTaskViaRxJava(FragmentActivity activity, FinishTask finishTask) {
        if (activity != null) {
            final AppDatabase db = App.getApp(activity).getDatabase();
            compositeDisposable.add(db.finishTaskDao().insertReactively(finishTask)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            );
        }
    }

    private int getCurrentDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.DAY_OF_WEEK);

    }

    private void addToDone() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            String key = getString(R.string.done_tasks_count);
            SharedPreferences preferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE);
            int defaultValue = 0;
            int value = preferences.getInt(key, defaultValue);

            value++;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            editor.commit();

            if (activity instanceof ProductivityUpdateListener) {
                ((ProductivityUpdateListener) activity).onProductivityUpdate(value);
            }
        }
    }

    private void initListeners() {
        srRefreshTasks.setOnRefreshListener(() -> loadTasksFromDbViaRxJava());
    }

    private void loadTasksFromDb() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            taskListViewModel.getTasks().observe(this, tasks -> {
                taskAdapter.setItems(tasks);
                checkVisibilityViews();
                srRefreshTasks.setRefreshing(false);
            });
        }

    }

    private void loadTasksFromDbViaRxJava() {
        FragmentActivity activity = getActivity();
        if (activity != null) {

            final AppDatabase db = App.getApp(activity).getDatabase();
            compositeDisposable.add(db.taskDao().getAllReactively()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    taskAdapter.setItems(tasks);
                    checkVisibilityViews();
                    srRefreshTasks.setRefreshing(false);
                })
            );
            /*taskListViewModel.getTasks().observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    taskAdapter.setItems(tasks);
                    checkVisibilityViews();
                    srRefreshTasks.setRefreshing(false);
                }
            });*/
        }

    }

    public void onDeleteTask(int index) {
        taskAdapter.removeItem(index);
        checkVisibilityViews();
        addToDone();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

}
