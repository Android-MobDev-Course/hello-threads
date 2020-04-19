package com.mobdev.hellothreads.task.image;

import android.widget.ImageView;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class ImageDownloadTask {

    private String name = null;

    /*
     * Field containing the Thread this task is running on.
     */
    private Thread mThread;

    /*
     * An object that contains the ThreadPool singleton.
     */
    private ImageDownloadTaskManager taskManager;

    /*
     * Fields containing references to the two runnable objects that handle downloading and
     * decoding of the image.
     */
    private Runnable mTaskRunnable;

    private ImageView imageView;

    private int imageDrawableId;

    public ImageDownloadTask(String name, ImageView imageView) {
        this.name = name;
        this.taskManager = ImageDownloadTaskManager.getInstance();
        this.mTaskRunnable = new ImageDownloadTaskRunnable(this);
        this.imageView = imageView;
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

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getImageDrawableId() {
        return imageDrawableId;
    }

    public void setImageDrawableId(int imageDrawableId) {
        this.imageDrawableId = imageDrawableId;
    }
}
