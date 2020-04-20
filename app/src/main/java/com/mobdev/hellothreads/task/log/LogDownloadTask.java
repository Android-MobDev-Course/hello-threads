package com.mobdev.hellothreads.task.log;

import com.mobdev.hellothreads.model.LogDescriptor;

import java.util.List;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class LogDownloadTask {

    private String name = null;

    /*
     * Field containing the Thread this task is running on.
     */
    private Thread mThread;

    /*
     * An object that contains the ThreadPool singleton.
     */
    private LogDownloadTaskManager taskManager;

    /*
     * Fields containing references to the runnable object
     */
    private Runnable mTaskRunnable;

    private List<LogDescriptor> logDescriptorList = null;

    public LogDownloadTask(String name) {
        this.name = name;
        this.taskManager = LogDownloadTaskManager.getInstance();
        this.mTaskRunnable = new LogDownloadTaskRunnable(this);
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

    public List<LogDescriptor> getLogDescriptorList() {
        return logDescriptorList;
    }

    public void setLogDescriptorList(List<LogDescriptor> logDescriptorList) {
        this.logDescriptorList = logDescriptorList;
    }
}
