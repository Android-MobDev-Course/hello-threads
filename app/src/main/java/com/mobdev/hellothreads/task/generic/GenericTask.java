package com.mobdev.hellothreads.task.generic;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class GenericTask {

    private String name = null;

    /*
     * Field containing the Thread this task is running on.
     */
    private Thread mThread;

    /*
     * An object that contains the ThreadPool singleton.
     */
    private GenericTaskManager taskManager;

    /*
     * Fields containing references to the runnable objects that handle the task
     */
    private Runnable mTaskRunnable;

    public GenericTask(String name) {
        this.name = name;
        this.taskManager = GenericTaskManager.getInstance();
        this.mTaskRunnable = new GenericTaskRunnable(this);
    }

    public void setThread(Thread thread) {
        this.mThread = thread;
    }

    void handleState(int state) {
        taskManager.handleState(this, state);
    }

    public Runnable getTaskRunnable() {
        return mTaskRunnable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
