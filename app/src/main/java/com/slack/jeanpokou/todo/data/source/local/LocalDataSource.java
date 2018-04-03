package com.slack.jeanpokou.todo.data.source.local;


import android.support.annotation.NonNull;

import com.slack.jeanpokou.todo.data.Task;
import com.slack.jeanpokou.todo.data.source.TasksDataContract;
import com.slack.jeanpokou.todo.util.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocalDataSource implements TasksDataContract {

    private static volatile LocalDataSource INSTANCE;

    private TaskDao mTaskDao;
    private AppExecutors mAppExecutors;

    private LocalDataSource(@NonNull AppExecutors appExecutors, @NonNull TaskDao taskDao) {
        mAppExecutors = appExecutors;
        mTaskDao = taskDao;
    }

    public static LocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull TaskDao taskDao) {
        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSource(appExecutors, taskDao);
                }
            }
        }
        return INSTANCE;

    }


    @Override
    public void retrieveTasks(@NonNull final retrieveTasksCallBack callback) {

        checkNotNull(callback, "callback can not be null");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTaskDao.getTask();

                // Run Callback on MainThread
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            callback.onError();
                        } else {
                            callback.onSuccess(tasks);
                        }
                    }
                });


            }
        };
        // Run database operation on diskIO
        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void retrieveTask(@NonNull final String taskId, @NonNull final retrieveTaskCallBack callback) {

        checkNotNull(taskId, "taskId should not be null");
        checkNotNull(callback, "callback can not be null");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Task task = mTaskDao.getTask(taskId);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!task.isEmpty()) {
                            callback.onSuccess(task);
                        } else {
                            callback.onError();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void saveTasks(@NonNull final Task task, @NonNull final saveTasksCallback callback) {
        checkNotNull(task, "task to save can not be null");
        checkNotNull(callback, "callback can not be null");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Long id = mTaskDao.insert(task);

                // Run Callback on MainThread
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (id != null) {
                            callback.onSuccess(id);
                        } else {
                            callback.onError();
                        }
                    }
                });


            }
        };
        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void deleteTaskById(@NonNull final String taskId) {
        checkNotNull(taskId, "taskId can not be null");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTaskDao.deleteTaskById(taskId);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void completeTask(@NonNull final String taskId) {
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
               mTaskDao.completeTask(taskId,true);
               mAppExecutors.mainThread().execute(new Runnable() {
                   @Override
                   public void run() {

                   }
               });
           }
       };
       mAppExecutors.diskIO().execute(runnable);
    }
}
