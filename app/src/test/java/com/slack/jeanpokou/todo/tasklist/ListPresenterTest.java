package com.slack.jeanpokou.todo.tasklist;

import com.google.common.collect.Lists;
import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.data.source.TasksRepository;
import com.slack.jeanpokou.todo.data.source.local.LocalDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;


public class ListPresenterTest {

    private static final List<Task> MANY_TASK = Lists.newArrayList
            (
                    new Task("task 1 ", "description 1"),
                    new Task("task 2 ", "description 2"),
                    new Task("task 3 ", "description 3")
            );
    @Mock
    private TaskListMvp.View mView = new ListFragment() ;

    @Mock
    private TasksRepository mTasksRepository;

    @Mock
    private LocalDataSource mTasksDataContractLocal;

    @Mock
    private TaskListMvp.Navigator mNavigator;

    private TaskListMvp.Presenter mPresenter ;

    @Captor
    private ArgumentCaptor<TasksDataContract.retrieveTasksCallBack> mCallback;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //mTasksRepository  = TasksRepository.getInstance(mTasksDataContractLocal) ;

    }

    @Test
    public void ViewShouldSetPresenter(){
        mPresenter = new ListPresenter( mTasksRepository,mView,mNavigator);
        verify(mView).attachPresenter(mPresenter);
    }
    @Test
    public void ViewShouldShowTask() {


        // when presenter load tasks list
        mPresenter = new ListPresenter(mTasksRepository,mView,mNavigator);

        mPresenter.start();

        verify(mTasksRepository).retrieveTasks(mCallback.capture());

        mCallback.getValue().onSuccess(MANY_TASK);

        // view should show the same task list
        verify(mView).showTasks(MANY_TASK);
    };


}