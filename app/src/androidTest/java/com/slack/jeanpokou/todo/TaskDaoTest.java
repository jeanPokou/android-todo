package com.slack.jeanpokou.todo;


import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.local.TaskDataBase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private static final Task ONE_TASK = new Task("task1","description1");
    private TaskDataBase mDataBase;

    @Test
    public void test() {
        Assert.assertEquals(1,1);
    }
    @Before
    public void setUp() {
        mDataBase = Room.inMemoryDatabaseBuilder
                (
                        InstrumentationRegistry.getContext(),TaskDataBase.class
                ).build();
    }
    @After
    public void clean() {
        mDataBase.close();
    }

    @Test
    public void insertTaskAndGetTasks() {
        mDataBase.taskDao().insert(ONE_TASK);
        List<Task> tasks = mDataBase.taskDao().getTask();
        assertThat(tasks.size(),is(1));
    }

}
