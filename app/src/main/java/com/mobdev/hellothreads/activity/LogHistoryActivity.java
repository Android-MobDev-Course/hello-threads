package com.mobdev.hellothreads.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.mobdev.hellothreads.R;
import com.mobdev.hellothreads.adapter.MyAdapter;
import com.mobdev.hellothreads.model.LogDescriptor;
import com.mobdev.hellothreads.persistence.LogDescriptorManager;
import com.mobdev.hellothreads.task.log.LogDownloadTaskManager;

import java.util.List;

public class LogHistoryActivity extends AppCompatActivity {

    private static final String TAG = "LogHistoryActivity";

    private RecyclerView mRecyclerView = null;

    private LinearLayoutManager mLayoutManager = null;

    private MyAdapter mAdapter = null;

    private Context mContext = null;

    private LogDescriptorManager logDescriptorManager = null;

    private LogDownloadTaskManager logDownloadTaskManager = null;

    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_history);
        init();
        observeLogData();
    }

    private void init(){

        mContext = this;

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        hideProgressBar();

        mRecyclerView  = (RecyclerView)findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager  = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.scrollToPosition(0);

        mRecyclerView.setLayoutManager(mLayoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // specify an adapter (see also next example)
        mAdapter  = new MyAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        setTitle(R.string.image_list_title);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.logDescriptorManager = LogDescriptorManager.getInstance(this);
        this.logDownloadTaskManager = LogDownloadTaskManager.getInstance();
        this.logDownloadTaskManager.setLogDescriptorManager(this.logDescriptorManager);
    }

    private void showProgressBar() {
        if(progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        if(progressBar != null)
            progressBar.setVisibility(View.INVISIBLE);
    }

    private void observeLogData(){
        this.logDescriptorManager.getLogLiveDataList().observe(this, new Observer<List<LogDescriptor>>() {
            @Override
            public void onChanged(List<LogDescriptor> logDescriptors) {
                hideProgressBar();
                if(logDescriptors != null){
                    Log.d(TAG, "Update Log List Received ! List Size: " + logDescriptors.size());
                    refreshRecyclerView(logDescriptors, 0);
                }
                else
                    Log.e(TAG, "Error observing Log List ! Received a null Object !");
            }
        });
    }

    private void refreshRecyclerView(List<LogDescriptor> updatedList, int scrollPosition){
        mAdapter.setDataset(updatedList);
        mAdapter.notifyDataSetChanged();
        if(scrollPosition >= 0)
            mLayoutManager.scrollToPosition(scrollPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_list_load:
                startLogDownloadTasks();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startLogDownloadTasks() {
        showProgressBar();
        this.logDownloadTaskManager.downloadUpdatedLogList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_loader, menu);
        return true;
    }
}
