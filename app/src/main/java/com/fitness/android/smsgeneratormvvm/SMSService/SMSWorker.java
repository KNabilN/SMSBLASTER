package com.fitness.android.smsgeneratormvvm.SMSService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;

import com.fitness.android.smsgeneratormvvm.R;
import com.fitness.android.smsgeneratormvvm.UI.MainActivity;
import com.fitness.android.smsgeneratormvvm.UI.SMSender;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ForegroundInfo;
import androidx.work.Operation;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SMSWorker extends Worker {
    public static NotificationManager manager;
    public SMSWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @NonNull
    @Override
    public Result doWork() {

        sendSms();
        return Result.success();
    }
    public void sendSms()
    {
        final List<String> numbers = SMSender.numbers;
        final String message = SMSender.message;

        new Thread(){
            public void run() {
                synchronized (this) {
                    Looper.prepare();
                    SmsManager sm = SmsManager.getDefault();
                    for (int i = 0; i < numbers.size(); i++) {
                        try {

                            sm.sendTextMessage(numbers.get(i), null
                                    , message,null,null);
                            setForegroundAsync(showNotification("SMS BLASTER", "Sent To: " + numbers.get(i)));

                            wait(15000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }

                        if (SMSender.cancel) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                manager.deleteNotificationChannel("task_channel");
                            }
                            manager.cancelAll();
                            break;
                        }

                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            manager.deleteNotificationChannel("task_channel");
                    }
                    manager.cancelAll();

                }

            }

        }.start();
    }

    private ForegroundInfo showNotification(String task, String desc) {
        String channelId = "task_channel";
        String channelName = "task_name";
//        Operation intent = WorkManager.getInstance(getApplicationContext()).cancelWorkById(getId());

        Intent retIntent = new Intent(getApplicationContext(),SMSender.class);
        retIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, retIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
        }
        Notification builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(task)
                .setContentText(desc)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(android.R.drawable.ic_delete, "Cancel", intent)
                .build();

        return new ForegroundInfo(builder);
    }



}


