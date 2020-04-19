package com.mobdev.hellothreads.task.generic;

import com.mobdev.hellothreads.utils.LogFactory;

import java.util.Random;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class GenericTaskRunnable implements Runnable {

    private static final String TAG = "MyTaskRunnable";

    private static final int RANDOM_TASK_DURATION_MS = 5000;

    private Random random = null;

    private GenericTask genericTask = null;

    private boolean isSuccess = false;

    public GenericTaskRunnable(GenericTask genericTask) {
        this.genericTask = genericTask;
    }

    @Override
    public void run() {

        /*
         * Stores the current Thread in the the PhotoTask instance, so that the instance
         * can interrupt the Thread.
         */
        this.genericTask.setThread(Thread.currentThread());

        genericTask.handleState(GenericTaskManager.TASK_STARTED);

        this.random = new Random();

        try {
            retrieveLogDescriptor();
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            // If the decode failed, there's no bitmap.
            if (!isSuccess) {
                // Sends a failure status to the PhotoTask
                genericTask.handleState(GenericTaskManager.TASK_FAILED);
            } else {
                genericTask.handleState(GenericTaskManager.TASK_COMPLETE);
            }

            // Clears the Thread's interrupt flag
            Thread.interrupted();
        }

    }

    private void retrieveLogDescriptor(){
        try{
            Thread.sleep(random.nextInt(RANDOM_TASK_DURATION_MS));

            if(isError())
                this.isSuccess = false;
            else
                this.isSuccess = true;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isError(){
        return random.nextInt(25)==0;
    }
}