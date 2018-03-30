package com.slack.jeanpokou.todo.taskaddedit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.slack.jeanpokou.todo.R;


public class AddEditFragment extends Fragment implements TaskAddEditMvp.View    {

    private static final String TAG = AddEditFragment.class.getSimpleName();
    public static final String ARGUMENT_EDIT_TASK_ID = " EDIT_TASK_ID" ;
    public static final int REQUEST_ADD_EDIT = 1;


    private TaskAddEditMvp.Presenter presenter;

    public AddEditFragment() {
    }

    public static AddEditFragment newInstance() {
        return new AddEditFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit, container, false);
        final EditText mTitle = (EditText) root.findViewById(R.id.add_task_title);
        final EditText mDescription = (EditText) root.findViewById(R.id.add_task_description);
        // setup the floating action button
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveTask(mTitle.getText().toString(), mDescription.getText().toString());
                // Log.i(TAG, "description is " + mDescription.getText().toString() + " and title is " + mTitle.getText().toString());
            }
        });
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void attachPresenter(TaskAddEditMvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void detachPresenter() {

    }

    @Override
    public void showErrorOnSaveTasks() {
        Toast.makeText(getContext(), "ERROR IN INSERTION OPERATION", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorOnEmptyTask() {

        Toast.makeText(getContext(), "ERROR TASK IS EMPTY", Toast.LENGTH_SHORT).show();

    }
}
