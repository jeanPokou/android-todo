package com.slack.jeanpokou.todo.addedittask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.slack.jeanpokou.todo.R;

public class AddEditTaskFragment extends Fragment {


    public static AddEditTaskFragment newInstance() {
        return new AddEditTaskFragment();
    }

    public AddEditTaskFragment() {
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
        EditText mTitle = (EditText) root.findViewById(R.id.task_title);
        EditText mDescription = (EditText) root.findViewById(R.id.add_task_description);
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
