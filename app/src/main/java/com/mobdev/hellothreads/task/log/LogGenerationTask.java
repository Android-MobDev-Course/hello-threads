package com.mobdev.hellothreads.task.log;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class LogGenerationTask {

    private String name = null;

    /*
     * Field containing the Thread this task is running on.
     */
    private Thread mThread;

    /*
     * An object that contains the ThreadPool singleton.
     */
    private LogGenerationTaskManager taskManager;

    /*
     * Fields containing references to the two runnable objects that handle downloading and
     * decoding of the image.
     */
    private Runnable mTaskRunnable;

    private int state;

    public LogGenerationTask(String name) {
        this.name = name;
        this.taskManager = LogGenerationTaskManager.getInstance();
        this.mTaskRunnable = new LogGenerationTaskRunnable(this);
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
