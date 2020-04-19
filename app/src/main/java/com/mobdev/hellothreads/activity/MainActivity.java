package com.mobdev.hellothreads.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.hellothreads.R;
import com.mobdev.hellothreads.model.TaskStatusDescriptor;
import com.mobdev.hellothreads.task.MyTaskManager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button scheduleTaskButton = null;
    private TextView scheduledTaskTextView = null;
    private TextView startedTaskTextView = null;
    private TextView completedTaskTextView = null;
    private TextView errorTaskTextView = null;
    private EditText newTaskEditText = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        observeTasksStatus();
    }

    private void initUI(){

        this.mContext = this;

        scheduledTaskTextView = (TextView)findViewById(R.id.scheduledTaskTextView);
        startedTaskTextView = (TextView)findViewById(R.id.startedTaskTextView);
        completedTaskTextView = (TextView)findViewById(R.id.completedTaskTextView);
        errorTaskTextView = (TextView)findViewById(R.id.errorTaskTextView);
        newTaskEditText = (EditText)findViewById(R.id.newTaskEditText);

        scheduleTaskButton = (Button)findViewById(R.id.scheduleTaskButton);
        scheduleTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newTaskCount = getNewTaskNumber();

                if(newTaskCount > 0){
                    Log.d(TAG, "Scheduling new Tasks: " + newTaskCount);
                    MyTaskManager.getInstance().retrieveLastLog(newTaskCount);
                }
                else
                    Toast.makeText(mContext, "New Task Number Error !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void observeTasksStatus(){
        MyTaskManager.getInstance().getTaskStatus().observe(this, new Observer<TaskStatusDescriptor>() {
            @Override
            public void onChanged(TaskStatusDescriptor taskStatusDescriptor) {
                Log.d(TAG, "Task Status Update Received !");
                updateTaskStatus(taskStatusDescriptor);

            }
        });
    }

    private void updateTaskStatus(TaskStatusDescriptor taskStatusDescriptor){
        if(taskStatusDescriptor != null){
            scheduledTaskTextView.setText(String.format(Locale.ITALY, "%d",taskStatusDescriptor.getScheduledCount()));
            startedTaskTextView.setText(String.format(Locale.ITALY, "%d",taskStatusDescriptor.getStartedCount()));
            completedTaskTextView.setText(String.format(Locale.ITALY, "%d",taskStatusDescriptor.getCompletedCount()));
            errorTaskTextView.setText(String.format(Locale.ITALY, "%d",taskStatusDescriptor.getErrorCount()));
        }
    }

    private int getNewTaskNumber(){
        try {
            return Integer.parseInt(this.newTaskEditText.getText().toString());
        }catch (Exception e){
            Log.e(TAG, "Error parsing New Task Number ! Error: " + e.getLocalizedMessage());
            return 0;
        }
    }
}
