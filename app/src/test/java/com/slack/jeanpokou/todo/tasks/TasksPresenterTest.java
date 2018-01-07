package com.slack.jeanpokou.todo.tasks;

import com.google.common.collect.Lists;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.data.source.TasksRepository;
import com.slack.jeanpokou.todo.data.source.local.TasksDataContractLocalImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;


public class TasksPresenterTest {

    private static final List<Task> MANY_TASK = Lists.newArrayList
            (
                    new Task("task 1 ", "description 1"),
                    new Task("task 2 ", "description 2"),
                    new Task("task 3 ", "description 3")
            );
    @Mock
    private TasksContract.View mView = new TasksFragment() ;

    @Mock
    private TasksRepository mTasksRepository;

    @Mock
    private TasksDataContractLocalImpl mTasksDataContractLocal;

    private TasksContract.Presenter mPresenter ;

    @Captor
    private ArgumentCaptor<TasksDataContract.LoadTaskCallBack> mCallback;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //mTasksRepository  = TasksRepository.getInstance(mTasksDataContractLocal) ;

    }

    @Test
    public void ViewShouldSetPresenter(){
        mPresenter = new TasksPresenter( mTasksRepository,mView);
        verify(mView).setPresenter(mPresenter);
    }
    @Test
    public void ViewShouldShowTask() {


        // when presenter load tasks list
        mPresenter = new TasksPresenter(mTasksRepository,mView);

        mPresenter.loadTask();

        verify(mTasksRepository).getTasks(mCallback.capture());

        mCallback.getValue().onTasksLoaded(MANY_TASK);

        verify(mView).showTasks(MANY_TASK);
        // view should show the same task list
    };


}