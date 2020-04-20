package com.mobdev.hellothreads.task.image;

import com.mobdev.hellothreads.R;
import java.util.Random;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class ImageDownloadTaskRunnable implements Runnable {

    private static final String TAG = "MyTaskRunnable";

    private static final int RANDOM_TASK_DURATION_MS = 5000;

    private Random random = null;

    private ImageDownloadTask imageDownloadTask = null;

    private int drawableId = -1;

    public ImageDownloadTaskRunnable(ImageDownloadTask imageDownloadTask) {
        this.imageDownloadTask = imageDownloadTask;
    }

    @Override
    public void run() {

        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        /*
         * Stores the current Thread in the the Task instance, so that the instance
         * can if necessary interrupt the Thread.
         */
        this.imageDownloadTask.setThread(Thread.currentThread());

        imageDownloadTask.handleState(ImageDownloadTaskManager.IMAGE_TASK_STARTED);

        this.random = new Random();

        try {
            drawableId = pickRandomImageId();
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            if (-1 == drawableId) {
                imageDownloadTask.setImageDrawableId(-1);
                imageDownloadTask.handleState(ImageDownloadTaskManager.IMAGE_TASK_FAILED);
            } else {
                imageDownloadTask.setImageDrawableId(drawableId);
                imageDownloadTask.handleState(ImageDownloadTaskManager.IMAGE_TASK_COMPLETE);
            }

            // Clears the Thread's interrupt flag
            Thread.interrupted();
        }

    }

    private int pickRandomImageId(){

        try{
            Thread.sleep(random.nextInt(RANDOM_TASK_DURATION_MS));

            if(isError())
                return -1;
            else{
                if(random.nextInt(2) == 0)
                    return R.drawable.android_logo_blue;
                else
                    return R.drawable.android_logo_white;
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    private boolean isError(){
        return random.nextInt(25)==0;
    }
}
