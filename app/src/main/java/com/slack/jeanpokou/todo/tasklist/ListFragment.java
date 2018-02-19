package com.slack.jeanpokou.todo.tasklist;


import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.taskaddedit.AddEditActivity;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.taskdetail.DetailActivity;

import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ListFragment extends android.support.v4.app.Fragment implements TaskListMvp.View {


    private TaskListMvp.Presenter mPresenter;
    private TextView mTextView = null;
    private ListNavigator mListNavigator ;

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
        TasksAdapter adapter = new TasksAdapter(Lists.<Task>newArrayList());

        taskRecyclerView.setAdapter(adapter);
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
        mPresenter.result(requestCode,resultCode);
    }

    @Override
    public void showTasks(List<Task> listTasks) {
        for (Task task : listTasks) {
            mTextView.append(task.getTitle());
        }
    }

    @Override
    public void showTaskMarkedActive(Task task) {


    }

    @Override
    public void showTaskDetailsUi(String taskId) {
        Intent intent = new Intent(getContext(), ListActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TASKID, taskId);
        startActivity(intent);
    }

    @Override
    public void showSuccessSavedMessage() {
        Toast.makeText(getContext(), "TASK ADDED", Toast.LENGTH_LONG).show();
    }



    private static class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

        private List<Task> mTasks;

        /**
         * Instantiates a new Tasks adapter.
         *
         * @param mTasks the m tasks
         */
        TasksAdapter(List<Task> mTasks) {
            this.mTasks = mTasks;
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
            View taskView = inflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(taskView);
        }

        /**
         * The type View holder.
         */
        static class ViewHolder extends RecyclerView.ViewHolder {

            private CheckBox mCheckBox;

            private TextView mTextView;

            /**
             * Instantiates a new View holder.
             *
             * @param itemView the item view
             */
            ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.task_title);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.task_status);
            }
        }
    }
}
