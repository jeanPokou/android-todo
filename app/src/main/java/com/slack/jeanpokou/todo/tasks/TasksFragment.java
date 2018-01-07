package com.slack.jeanpokou.todo.tasks;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.R;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment implements TasksContract.View {


    private TasksContract.Presenter mPresenter;
    private TextView mTextView = null;

    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_tasks, container, false);
        mTextView = rootView.findViewById(R.id.testTask);
        // Inflate the layout for this fragment
        // Setup the Recycler View
        RecyclerView taskRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_tasks);
        TasksAdapter adapter = new TasksAdapter(Lists.newArrayList(
                new Task("TITLE 1", "DESC 1",true),
                new Task("TITLE 2", "DESC 2" ),
                new Task("TITLE 3", "DESC 3",true)
        )
        );
        taskRecyclerView.setAdapter(adapter);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        // Return rootView
        return rootView;
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showTasks(List<Task> listTasks) {
        for (Task task: listTasks ) {
            mTextView.append(task.getTitle());
        }
    }

    public static TasksFragment newInstance() {
        return  new TasksFragment();
    }

    private static class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
        private List<Task> mTasks;

        TasksAdapter(List<Task> mTasks) {
            this.mTasks = mTasks;
        }

        @Override
        public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View taskView = inflater.inflate(R.layout.list_item,parent,false);
            return new ViewHolder(taskView);
        }

        @Override
        public void onBindViewHolder(TasksAdapter.ViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.mTextView.setText(task.getTitle());
            holder.mCheckBox.setChecked(task.isCompleted());

        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;
            private CheckBox mCheckBox;
            ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.task_title);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.task_status);
            }
        }
    }
}
