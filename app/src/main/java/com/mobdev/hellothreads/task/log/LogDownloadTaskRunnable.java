package com.mobdev.hellothreads.task.log;

import com.mobdev.hellothreads.model.LogDescriptor;
import com.mobdev.hellothreads.utils.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class LogDownloadTaskRunnable implements Runnable {

    private static final String TAG = "MyTaskRunnable";

    private static final int RANDOM_TASK_DURATION_MS = 5000;

    private static final int LOG_LIST_SIZE = 20;

    private Random random = null;

    private LogDownloadTask logDownloadTask = null;

    private List<LogDescriptor> logDescriptorList = null;

    public LogDownloadTaskRunnable(LogDownloadTask logDownloadTask) {
        this.logDownloadTask = logDownloadTask;
    }

    @Override
    public void run() {

        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        /*
         * Stores the current Thread in the the Task instance, so that the instance
         * can if necessary interrupt the Thread.
         */
        this.logDownloadTask.setThread(Thread.currentThread());

        logDownloadTask.handleState(LogDownloadTaskManager.TASK_STARTED);

        this.random = new Random();

        try {
            retrieveLogDescriptor();
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            if (null == logDescriptorList) {
                logDownloadTask.handleState(LogDownloadTaskManager.TASK_FAILED);
            } else {
                logDownloadTask.setLogDescriptorList(logDescriptorList);
                logDownloadTask.handleState(LogDownloadTaskManager.TASK_COMPLETE);
            }

            // Clears the Thread's interrupt flag
            Thread.interrupted();
        }

    }

    private void retrieveLogDescriptor(){

        try{
            Thread.sleep(random.nextInt(RANDOM_TASK_DURATION_MS));

            logDescriptorList = new ArrayList<>();

            for(int i=0 ; i<LOG_LIST_SIZE; i++){
                logDescriptorList.add(LogFactory.createRandomLogDescriptor());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
