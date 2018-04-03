package com.slack.jeanpokou.todo.tasklist;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.data.Task;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ListFragment extends android.support.v4.app.Fragment implements TaskListMvp.View {

    public final String TAG = ListFragment.class.getSimpleName();
    private TaskListMvp.Presenter mPresenter;
    private ListNavigator mListNavigator;
    private TasksAdapter mAdapter = null;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        TextView textView = rootView.findViewById(R.id.testTask);
        // Inflate the layout for this fragment
        // Setup the Recycler View
        RecyclerView taskRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_tasks);

        mAdapter = new TasksAdapter(Lists.<Task>newArrayList(), new TaskItemListener() {
            @Override
            public void onTaskClick(final Task taskId) {
                mPresenter.navigateToDetail(taskId.getId());

//                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
//                        .setTitle("REMOVE TASK ")
//                        .setMessage("DO YOU WANT TO REMOVE TASK ?" + taskId.getTitle())
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                // TODO notify recycler view of item removed
//                                 mPresenter.deleteTaskById(taskId.getId());
//                            }
//                        })
//                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        }).create();
//                alertDialog.show();
            }

            @Override
            public void onTaskCompleted(Task task) {
                mPresenter.completeTask(task);
            }

        });

        taskRecyclerView.setAdapter(mAdapter);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // setup floating action button
        // click on floating action navigate to AddEditTask
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                mPresenter.navigateToAddEdit();
            }
        });

        setHasOptionsMenu(true);
        // Return rootView
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void attachPresenter(TaskListMvp.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void detachPresenter() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Long id = data.getLongExtra("TASK_INSERTED_ID", -1);

        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void showTasks(List<Task> taskList) {
        mAdapter.mTasks = taskList;
    }


    @Override
    public void showTaskDetailsUi(String taskId) {
        mListNavigator.navigateToDetail(taskId);
    }

    @Override
    public void showSuccessSavedTasks() {
        Toast.makeText(getContext(), "TASK ADDED WITH SUCCESS", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorSavedTasks() {
        Toast.makeText(getContext(), "ERROR ADDING TASK", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasklist_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                Log.d(TAG, "clear all tasks");
                break;
            case R.id.menu_filter:
                Log.d(TAG, "filter tasks");
                break;
            case R.id.menu_refresh:
                Log.d(TAG, "Refresh Tasks");
                break;
        }
        return true;
    }

    @Override
    public void showSuccessDeleteTask() {
        Toast.makeText(getContext(), "TASK REMOVED SUCCESSFULLY", Toast.LENGTH_LONG).show();
    }

    public interface TaskItemListener {
        void onTaskClick(final Task task);

        void onTaskCompleted(final Task task);

    }

    // RecyclerView Adapter
    private static class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

        private List<Task> mTasks;
        private TaskItemListener listener;


        TasksAdapter(List<Task> mTasks, TaskItemListener listener) {
            this.mTasks = mTasks;
            this.listener = listener;
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        @Override
        public void onBindViewHolder(TasksAdapter.ViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.mTextView.setText(task.getTitle());
            holder.mCheckBox.setChecked(task.isCompleted());

        }


        @NonNull
        @Override
        public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View taskView = inflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(taskView);
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private CheckBox mCheckBox;

            private TextView mTextView;

            ViewHolder(final View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.task_title);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.task_status);

                if (listener != null) {

                    Log.d("Test", "inside view holder listener");
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int position = getAdapterPosition();
                                final Task task = mTasks.get(position);
                                if (position != RecyclerView.NO_POSITION) {
                                    listener.onTaskClick(task);
                                }
                            }
                        });

                        mCheckBox.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int position = getAdapterPosition();
                                final Task task = mTasks.get(position);
                                mCheckBox.setChecked(task.isCompleted());
                                if (position != RecyclerView.NO_POSITION) {
                                    //if (!task.isCompleted()) {
                                    listener.onTaskCompleted(task);
                                    //}
                                }
                            }
                        });

                }
            }

        }
    }
}

