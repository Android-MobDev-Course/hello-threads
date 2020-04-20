package com.mobdev.hellothreads.task.generic;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.mobdev.hellothreads.model.TaskStatusDescriptor;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class GenericTaskManager {

    private static final String TAG = "MyTaskManager";

    /*
     * Status indicators
     */
    static final int TASK_FAILED = -1;
    static final int TASK_STARTED = 1;
    static final int TASK_COMPLETE = 2;

    // Sets the amount of time an idle thread will wait for a task before terminating
    private static final int KEEP_ALIVE_TIME = 1;

    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    // Sets the initial threadpool size to 8
    private static final int CORE_POOL_SIZE = 8;

    // Sets the maximum threadpool size to 8
    private static final int MAXIMUM_POOL_SIZE = 8;

    // A queue of Runnables for the image download pool
    private final BlockingQueue<Runnable> mDownloadWorkQueue;

    // A managed pool of background download threads
    private final ThreadPoolExecutor mDownloadThreadPool;

    private static final GenericTaskManager instance = new GenericTaskManager();

    private MutableLiveData<TaskStatusDescriptor> taskStatus;

    private TaskStatusDescriptor taskStatusDescriptor;

    public static GenericTaskManager getInstance() {
        return instance;
    }

    private GenericTaskManager() {

        /*
         * Creates a work queue for the pool of Thread objects
         */
        mDownloadWorkQueue = new LinkedBlockingQueue<Runnable>();

        /*
         * Creates a new pool of Thread objects for the task work queue
         */
        mDownloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mDownloadWorkQueue);

        taskStatus = new MutableLiveData<>();

    }

    public void handleState(GenericTask genericTask, int state) {

        switch (state) {

            case TASK_STARTED:
                Log.d(TAG, genericTask.getName() + " Started ...");
                taskStatusDescriptor.incrementStarted();
                notifyStatusUpdate();
                break;
            case TASK_COMPLETE:
                Log.d(TAG, genericTask.getName() + " Completed Correctly !");
                taskStatusDescriptor.incrementCompleted();
                notifyStatusUpdate();
                break;
            default:
                Log.e(TAG, genericTask.getName() + " Task Error !");
                taskStatusDescriptor.incrementErrors();
                notifyStatusUpdate();
                break;
        }
    }

    private void notifyStatusUpdate(){
        //We are in a background thread and we can not use the setValue method
        //Using setValue you will receive an Exception: "java.lang.IllegalStateException: Cannot invoke setValue on a background thread"
        taskStatus.postValue(this.taskStatusDescriptor);
    }

    public void addNewTasks(int count) {

        if(taskStatusDescriptor == null)
            taskStatusDescriptor = new TaskStatusDescriptor();

        for(int i=0; i<count; i++){
            taskStatusDescriptor.incrementScheduled();
            GenericTask genericTask = new GenericTask(String.format(Locale.ITALY, "MyTask-%d",taskStatusDescriptor.getScheduledCount()));
            instance.mDownloadThreadPool.execute(genericTask.getTaskRunnable());
        }
    }

    public MutableLiveData<TaskStatusDescriptor> getTaskStatus() {
        return taskStatus;
    }
}
