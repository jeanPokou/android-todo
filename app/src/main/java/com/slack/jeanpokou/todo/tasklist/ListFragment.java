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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.taskdetail.DetailActivity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ListFragment extends android.support.v4.app.Fragment implements TaskListMvp.View {


    private TaskListMvp.Presenter mPresenter;
    private TextView mTextView = null;
    private ListNavigator mListNavigator;
    private TasksAdapter mAdapter = null;
    public final String TAG = ListFragment.class.getSimpleName();

    /**
     * Instantiates a new Tasks fragment.
     */
    public ListFragment() {
    }

    /**
     * New instance tasks fragment.
     *
     * @return the tasks fragment
     */
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        mTextView = rootView.findViewById(R.id.testTask);
        // Inflate the layout for this fragment
        // Setup the Recycler View
        RecyclerView taskRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_tasks);

        /*TasksAdapter adapter = new TasksAdapter(Lists.newArrayList(
                new Task("TITLE 1", "DESC 1", true),
                new Task("TITLE 2", "DESC 2"),
                new Task("TITLE 3", "DESC 3", true)
        )
        );*/
        mAdapter = new TasksAdapter(Lists.<Task>newArrayList(), new TaskItemListener() {
            @Override
            public void onTaskClick(View view, Task taskId) {
                mPresenter.deleteTaskById(taskId.getId());
            }

        });

        taskRecyclerView.setAdapter(mAdapter);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // setup floating action button
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                mPresenter.navigateToAddEditTask();
            }
        });
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



    /**
     * On Result from AddEditTask AddEditActivity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Long id = data.getLongExtra ("TASK_INSERTED_ID", -1);
        mPresenter.result(requestCode, resultCode,id);
    }

    @Override
    public void showTasks(List<Task> taskList) {
        mAdapter.mTasks = taskList;
    }



    @Override
    public void showTaskDetailsUi(String taskId) {
        Intent intent = new Intent(getContext(), ListActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TASKID, taskId);
        startActivity(intent);
    }

    @Override
    public void showSuccessSavedTasks(Long id) {
        Toast.makeText(getContext(), "TASK ADDED WITH ID" + id, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorSavedTasks() {
        Toast.makeText(getContext(), "ERROR ADDING TASK",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessDeleteTask() {
        Toast.makeText(getContext(), "TASK REMOVED SUCCESSFULLY",Toast.LENGTH_LONG).show();
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


        @Override
        public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View taskView = inflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(taskView);
        }


        /**
         * The type View holder.
         */

         class ViewHolder extends RecyclerView.ViewHolder {

            private CheckBox mCheckBox;

            private TextView mTextView;

            /**
             * Instantiates a new View holder.
             * @param itemView the item view
             */

            ViewHolder(final View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.task_title);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.task_status);

                itemView.setOnClickListener( new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(listener != null ) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onTaskClick(itemView, mTasks.get(position));
                            }
                        }
                    }
                });

            }

        }
    }

    public  interface  TaskItemListener {
       void onTaskClick(View view, Task taskId);

    }

}
