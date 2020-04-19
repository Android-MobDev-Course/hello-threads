package com.mobdev.hellothreads.task;

import com.mobdev.hellothreads.model.LogDescriptor;
import com.mobdev.hellothreads.utils.LogFactory;

import java.util.Random;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class MyTaskRunnable implements Runnable {

    private static final String TAG = "MyTaskRunnable";

    private static final int RANDOM_TASK_DURATION_MS = 5000;

    private Random random = null;

    private MyTask myTask = null;

    private LogDescriptor logDescriptor = null;

    public MyTaskRunnable(MyTask myTask) {
        this.myTask = myTask;
    }

    @Override
    public void run() {

        /*
         * Stores the current Thread in the the PhotoTask instance, so that the instance
         * can interrupt the Thread.
         */
        this.myTask.setThread(Thread.currentThread());

        myTask.handleState(MyTaskManager.TASK_STARTED);

        this.random = new Random();

        try {
            retrieveLogDescriptor();
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            // If the decode failed, there's no bitmap.
            if (null == logDescriptor) {
                // Sends a failure status to the PhotoTask
                myTask.handleState(MyTaskManager.TASK_FAILED);
            } else {
                //TODO Do something with the data !
                myTask.handleState(MyTaskManager.TASK_COMPLETE);
            }

            // Clears the Thread's interrupt flag
            Thread.interrupted();
        }

    }

    private void retrieveLogDescriptor(){
        try{
            Thread.sleep(random.nextInt(RANDOM_TASK_DURATION_MS));

            if(isError())
                this.logDescriptor = null;
            else
                this.logDescriptor = LogFactory.createRandomLogDescriptor();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isError(){
        return random.nextInt(25)==0;
    }
}
