package com.mobdev.hellothreads.task.log;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mobdev.hellothreads.model.LogDescriptor;
import com.mobdev.hellothreads.model.TaskStatusDescriptor;
import com.mobdev.hellothreads.persistence.LogDescriptorManager;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class LogDownloadTaskManager {

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

    private static final LogDownloadTaskManager instance = new LogDownloadTaskManager();

    public static LogDownloadTaskManager getInstance() {
        return instance;
    }

    private LogDescriptorManager logDescriptorManager;

    private LogDownloadTaskManager() {

        /*
         * Creates a work queue for the pool of Thread objects used for downloading, using a linked
         * list queue that blocks when the queue is empty.
         */
        mDownloadWorkQueue = new LinkedBlockingQueue<Runnable>();

        /*
         * Creates a new pool of Thread objects for the download work queue
         */
        mDownloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mDownloadWorkQueue);
    }

    public LogDescriptorManager getLogDescriptorManager() {
        return logDescriptorManager;
    }

    public void setLogDescriptorManager(LogDescriptorManager logDescriptorManager) {
        this.logDescriptorManager = logDescriptorManager;
    }

    public void handleState(LogDownloadTask logDownloadTask, int state) {

        switch (state) {
            // The task finished downloading and decoding the image
            case TASK_STARTED:
                Log.d(TAG, logDownloadTask.getName() + " Started ...");
                break;
            case TASK_COMPLETE:
                Log.d(TAG, logDownloadTask.getName() + " Completed Correctly !");
                saveNewLogList(logDownloadTask.getLogDescriptorList());
                break;
            default:
                Log.e(TAG, logDownloadTask.getName() + " Task Error !");
                break;
        }
    }

    private void saveNewLogList(List<LogDescriptor> logDescriptorList) {
        if(logDescriptorManager != null)
            logDescriptorManager.addLogList(logDescriptorList);
        else
            Log.e(TAG, "Log Description Manager = Null ! ");

    }

    public void downloadUpdatedLogList() {
        LogDownloadTask logDownloadTask = new LogDownloadTask(String.format(Locale.ITALY, "LogDownloadTask-%d",System.currentTimeMillis()));
        instance.mDownloadThreadPool.execute(logDownloadTask.getTaskRunnable());
     }
}
