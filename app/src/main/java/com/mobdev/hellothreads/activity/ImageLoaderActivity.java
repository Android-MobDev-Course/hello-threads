package com.mobdev.hellothreads.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.mobdev.hellothreads.R;
import com.mobdev.hellothreads.task.image.ImageDownloadTaskManager;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class ImageLoaderActivity extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_loader);
        initUI();
   }

    private void initUI() {

        setTitle(R.string.image_loader_title);

        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView4 = (ImageView)findViewById(R.id.imageView4);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void startImageDownloadTasks(){
        ImageDownloadTaskManager.getInstance().scheduleNewImageDownload(imageView1);
        ImageDownloadTaskManager.getInstance().scheduleNewImageDownload(imageView2);
        ImageDownloadTaskManager.getInstance().scheduleNewImageDownload(imageView3);
        ImageDownloadTaskManager.getInstance().scheduleNewImageDownload(imageView4);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_image_load:
                startImageDownloadTasks();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_loader, menu);
        return true;
    }
}
