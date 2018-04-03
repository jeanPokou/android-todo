package com.slack.jeanpokou.todo.taskdetail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.slack.jeanpokou.todo.R;
import com.slack.jeanpokou.todo.data.Task;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailFragment extends Fragment implements TaskDetailMvp.View {

    private final String TAG = DetailFragment.class.getSimpleName();
    private TaskDetailMvp mTaskDetailMvp;
    private TextView mDetailTitle;
    private TextView mDetailDescription;
    private CheckBox mDetailComplete;

    private TaskDetailMvp.Presenter mPresenter;

    public static DetailFragment newInstance( @Nullable  String taskId) {
        Bundle args = new Bundle();
        args.putString("TASK_ID",taskId);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);
        mDetailTitle = (TextView) root.findViewById(R.id.task_detail_title);
        mDetailDescription = (TextView) root.findViewById(R.id.task_detail_description);
        mDetailComplete = (CheckBox) root.findViewById(R.id.task_detail_complete);

        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked on fab");
            }
        });

        return root;
    }

    @Override
    public void attachPresenter(@NonNull TaskDetailMvp.Presenter presenter) {
        Log.d(TAG, "presenter attached");
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void detachPresenter() {
        Log.d(TAG, "presenter detached");
    }

    @Override
    public void showNoTask() {

    }

    @Override
    public void setLoadingIndicator(boolean b) {

    }

    @Override
    public void showTask(Task task) {
        this.mDetailTitle.setText(task.getTitle());
        this.mDetailDescription.setText(task.getDescription());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
