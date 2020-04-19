package com.mobdev.hellothreads.task;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class MyTask {

    private String name = null;

    /*
     * Field containing the Thread this task is running on.
     */
    private Thread mThread;

    /*
     * An object that contains the ThreadPool singleton.
     */
    private static MyTaskManager taskManager;

    /*
     * Fields containing references to the two runnable objects that handle downloading and
     * decoding of the image.
     */
    private Runnable mTaskRunnable;

    private int state;

    public MyTask(String name) {
        this.name = name;
        this.taskManager = MyTaskManager.getInstance();
        this.mTaskRunnable = new MyTaskRunnable(this);
    }

    public void setThread(Thread thread) {
        this.mThread = thread;
    }

    // Delegates handling the current state of the task to the PhotoManager object
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
