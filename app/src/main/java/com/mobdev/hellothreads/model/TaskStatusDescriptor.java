package com.mobdev.hellothreads.model;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class TaskStatusDescriptor {

    private int scheduledCount = 0;
    private int startedCount = 0;
    private int completedCount = 0;
    private int errorCount = 0;

    public TaskStatusDescriptor() {
    }

    public void incrementScheduled(int count){
        this.scheduledCount += count;
    }

    public void incrementScheduled(){
        this.scheduledCount++;
    }

    public void incrementStarted(){
        this.startedCount++;
    }

    public void incrementCompleted(){
        this.completedCount++;
    }

    public void incrementErrors(){
        this.errorCount++;
    }

    public int getScheduledCount() {
        return scheduledCount;
    }

    public void setScheduledCount(int scheduledCount) {
        this.scheduledCount = scheduledCount;
    }

    public int getStartedCount() {
        return startedCount;
    }

    public void setStartedCount(int startedCount) {
        this.startedCount = startedCount;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
}
