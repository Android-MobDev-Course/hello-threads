package com.mobdev.hellothreads.task.image;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;

import com.mobdev.hellothreads.R;
import com.mobdev.hellothreads.model.TaskStatusDescriptor;
import com.mobdev.hellothreads.task.generic.GenericTask;

import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class ImageDownloadTaskManager {

    private static final String TAG = "MyTaskManager";

    /*
     * Status indicators
     */
    static final int IMAGE_TASK_FAILED = -1;
    static final int IMAGE_TASK_STARTED = 1;
    static final int IMAGE_TASK_COMPLETE = 2;

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

    private static final ImageDownloadTaskManager instance = new ImageDownloadTaskManager();

    private Handler mHandler;

    public static ImageDownloadTaskManager getInstance() {
        return instance;
    }

    private ImageDownloadTaskManager() {

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

        initHandlerUI();
    }

    public void handleState(ImageDownloadTask imageDownloadTask, int state) {

        switch (state) {
            // The task finished downloading and decoding the image
            case IMAGE_TASK_STARTED:
                Log.d(TAG, imageDownloadTask.getName() + " Started ...");
                sendHandlerMessage(imageDownloadTask, state);
                break;
            case IMAGE_TASK_COMPLETE:
                Log.d(TAG, imageDownloadTask.getName() + " Completed Correctly !");
                sendHandlerMessage(imageDownloadTask, state);
                break;
            case IMAGE_TASK_FAILED:
                Log.d(TAG, imageDownloadTask.getName() + " Task Failed !");
                sendHandlerMessage(imageDownloadTask, state);
                break;
            default:
                Log.e(TAG, imageDownloadTask.getName() + " Task Error !");
                sendHandlerMessage(imageDownloadTask, state);
                break;
        }
    }

    public void scheduleNewImageDownload(ImageView imageView){
        ImageDownloadTask imageDownloadTask = new ImageDownloadTask(String.format(Locale.ITALY, "ImageDownload-%d",imageView.getId()), imageView);
        instance.mDownloadThreadPool.execute(imageDownloadTask.getTaskRunnable());
    }

    private void sendHandlerMessage(ImageDownloadTask imageDownloadTask, int state){
        Message completeMessage = mHandler.obtainMessage(state, imageDownloadTask);
        completeMessage.sendToTarget();
    }

    private void initHandlerUI(){

        mHandler = new Handler(Looper.getMainLooper()) {

            /*
             * handleMessage() defines the operations to perform when the
             * Handler receives a new Message to process.
             */
            @Override
            public void handleMessage(Message inputMessage) {

                // Gets the image task from the incoming Message object.
                ImageDownloadTask imageTask = (ImageDownloadTask) inputMessage.obj;

                ImageView imageView = imageTask.getImageView();

                // If this input view isn't null
                if (imageView != null) {

                    switch (inputMessage.what) {
                         case IMAGE_TASK_STARTED:
                                imageView.setImageResource(R.drawable.loading);
                                break;
                            case IMAGE_TASK_COMPLETE:
                                imageView.setImageResource(imageTask.getImageDrawableId());
                                break;
                            case IMAGE_TASK_FAILED:
                                imageView.setImageResource(R.drawable.missing_image);
                                break;
                            default:
                                super.handleMessage(inputMessage);
                        }
                }
            }
        };
    }
}
