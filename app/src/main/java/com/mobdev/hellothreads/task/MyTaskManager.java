package com.mobdev.hellothreads.task;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mobdev.hellothreads.model.TaskStatusDescriptor;

import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class MyTaskManager {

    private static final String TAG = "MyTaskManager";

    /*
     * Status indicators
     */
    static final int TASK_FAILED = -1;
    static final int TASK_STARTED = 1;
    static final int TASK_COMPLETE = 2;

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

    private static final MyTaskManager instance = new MyTaskManager();

    private MutableLiveData<TaskStatusDescriptor> taskStatus;

    private TaskStatusDescriptor taskStatusDescriptor;

    private Handler mHandler;

    public static MyTaskManager getInstance() {
        return instance;
    }

    private MyTaskManager() {

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

        taskStatus = new MutableLiveData<>();

        initHandlerUI();
    }

    public void handleState(MyTask myTask, int state) {

        switch (state) {
            // The task finished downloading and decoding the image
            case TASK_STARTED:
                Log.d(TAG, myTask.getName() + " Started ...");
                taskStatusDescriptor.incrementStarted();
                notifyStatusUpdate();
                break;
            case TASK_COMPLETE:
                Log.d(TAG, myTask.getName() + " Completed Correctly !");
                taskStatusDescriptor.incrementCompleted();
                notifyStatusUpdate();
                break;
            default:
                Log.e(TAG, myTask.getName() + " Task Error !");
                taskStatusDescriptor.incrementErrors();
                notifyStatusUpdate();
                break;
        }
    }

    private void notifyStatusUpdate(){
        //We are in a background thread and we can not use the setValue method
        //Using setValue you will receive an Exception: "java.lang.IllegalStateException: Cannot invoke setValue on a background thread"
        taskStatus.postValue(this.taskStatusDescriptor);
    }

    public void retrieveLastLog(int count) {

        if(taskStatusDescriptor == null)
            taskStatusDescriptor = new TaskStatusDescriptor();

        for(int i=0; i<count; i++){
            taskStatusDescriptor.incrementScheduled();
            MyTask myTask = new MyTask(String.format(Locale.ITALY, "MyTask-%d",taskStatusDescriptor.getScheduledCount()));
            instance.mDownloadThreadPool.execute(myTask.getTaskRunnable());
        }
    }

    private void initHandlerUI(){

//        mHandler = new Handler(Looper.getMainLooper()) {
//
//            /*
//             * handleMessage() defines the operations to perform when the
//             * Handler receives a new Message to process.
//             */
//            @Override
//            public void handleMessage(Message inputMessage) {
//
//                // Gets the image task from the incoming Message object.
//                PhotoTask photoTask = (PhotoTask) inputMessage.obj;
//
//                // Sets an PhotoView that's a weak reference to the
//                // input ImageView
//                PhotoView localView = photoTask.getPhotoView();
//
//                // If this input view isn't null
//                if (localView != null) {
//
//                    /*
//                     * Gets the URL of the *weak reference* to the input
//                     * ImageView. The weak reference won't have changed, even if
//                     * the input ImageView has.
//                     */
//                    URL localURL = localView.getLocation();
//
//                    /*
//                     * Compares the URL of the input ImageView to the URL of the
//                     * weak reference. Only updates the bitmap in the ImageView
//                     * if this particular Thread is supposed to be serving the
//                     * ImageView.
//                     */
//                    if (photoTask.getImageURL() == localURL)
//
//                        /*
//                         * Chooses the action to take, based on the incoming message
//                         */
//                        switch (inputMessage.what) {
//
//                            // If the download has started, sets background color to dark green
//                            case DOWNLOAD_STARTED:
//                                localView.setStatusResource(R.drawable.imagedownloading);
//                                break;
//
//                            /*
//                             * If the download is complete, but the decode is waiting, sets the
//                             * background color to golden yellow
//                             */
//                            case DOWNLOAD_COMPLETE:
//                                // Sets background color to golden yellow
//                                localView.setStatusResource(R.drawable.decodequeued);
//                                break;
//                            // If the decode has started, sets background color to orange
//                            case DECODE_STARTED:
//                                localView.setStatusResource(R.drawable.decodedecoding);
//                                break;
//                            /*
//                             * The decoding is done, so this sets the
//                             * ImageView's bitmap to the bitmap in the
//                             * incoming message
//                             */
//                            case TASK_COMPLETE:
//                                localView.setImageBitmap(photoTask.getImage());
//                                recycleTask(photoTask);
//                                break;
//                            // The download failed, sets the background color to dark red
//                            case DOWNLOAD_FAILED:
//                                localView.setStatusResource(R.drawable.imagedownloadfailed);
//
//                                // Attempts to re-use the Task object
//                                recycleTask(photoTask);
//                                break;
//                            default:
//                                // Otherwise, calls the super method
//                                super.handleMessage(inputMessage);
//                        }
//                }
//            }
//        };
    }

    public MutableLiveData<TaskStatusDescriptor> getTaskStatus() {
        return taskStatus;
    }
}
