package com.fitness.android.smsgeneratormvvm.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitness.android.smsgeneratormvvm.R;
import com.fitness.android.smsgeneratormvvm.SMSService.SMSWorker;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMSender extends AppCompatActivity implements View.OnClickListener {

    private EditText msg;
    public static Button snd;
    private SMSViewModel smsViewModel;
    public static List<String> numbers;
    public static String message;
    WorkManager mWorkManager;
    OneTimeWorkRequest mRequest;
    public static boolean cancel = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsender);
        // initial the UI
        msg = findViewById(R.id.msg);
        snd = findViewById(R.id.snd);
        snd.setOnClickListener(this);
        findViewById(R.id.cnc).setOnClickListener(this);

//        //bind service
//        bindService(new Intent(getBaseContext(),SmService.class)
//                , mConnection, Context.BIND_AUTO_CREATE);

        // enabled it till permission granted
        if (checkPer(Manifest.permission.SEND_SMS))
        {
            snd.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest
            .permission.SEND_SMS}, 1);
        }

        //Observe changes
        smsViewModel = ViewModelProviders.of(this).get(SMSViewModel.class);
        smsViewModel.numbers.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                numbers = strings;

            }
        });

//        //handel the thread
//        handler = new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                String message = (String) msg.obj; //Extract the string from the Message
//                Toast.makeText(SMSender.this, "Sent To: " +message, Toast.LENGTH_SHORT).show();
//            }
//        };

        //worker
        mWorkManager = WorkManager.getInstance();
        mRequest = new OneTimeWorkRequest.Builder(SMSWorker.class).build();



    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.snd)
        {
            //get data
            smsViewModel.getNumbers();
            //get message
            message = msg.getText().toString();
            if (!message.isEmpty())
            {
                cancel = false;
                //sms service activation
                mWorkManager.enqueue(mRequest);
            }
            else
            {

               NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    assert manager != null;
                    manager.deleteNotificationChannel("task_channel");
                }
                assert manager != null;
                manager.cancelAll();
                Toast.makeText(this, "The Message Please", Toast.LENGTH_SHORT).show();
            }
            
        }
        if (view.getId() == R.id.cnc)
        {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            cancel = true;
        }


    }

    private boolean checkPer(String permission)
    {
        // sms sender permissions
        int check = ContextCompat.checkSelfPermission(this, permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onStart() {
        super.onStart();
        cancel = false;
    }
}
