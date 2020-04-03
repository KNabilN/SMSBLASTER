//package com.fitness.android.smsgeneratormvvm.SMSService;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.IBinder;
//import android.os.Looper;
//import android.os.Message;
//import android.telephony.SmsManager;
//import android.widget.Button;
//
//import com.fitness.android.smsgeneratormvvm.UI.MainActivity;
//import com.fitness.android.smsgeneratormvvm.UI.SMSender;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.JobIntentService;
//
//public class SmService extends JobIntentService {
//
//    private final LocalService mBinder = new LocalService();
//
//    public class LocalService extends Binder {
//        public SmService getService()
//        {
//            return SmService.this;
//        }
//    }
//
//    public SmService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
//
//    @Override
//    protected void onHandleWork(@NonNull Intent intent) {
//        sendSms();
//    }
//
//    public static void enqueueWork(Context context, Intent work) {
//        enqueueWork(context, SmService.class, 1, work);
//        sendSms();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return START_STICKY;
//    }
//
//
//    public static void sendSms()
//    {
//
//        new Thread(){
//            public void run() {
//                synchronized (this) {
//                    Looper.prepare();
//                    SmsManager sm = SmsManager.getDefault();
//                    for (int i = 0; i < SMSender.numbers.size(); i++) {
//                        try {
//                            String message = SMSender.numbers.get(i);
//                            Message msg = Message.obtain(); // Creates an new Message instance
//                            msg.obj = message; // Put the string into Message, into "obj" field.
//                            msg.setTarget(SMSender.handler); // Set the Handler
//                            msg.sendToTarget(); //Send the message
//                            sm.sendTextMessage(SMSender.numbers.get(i), null
//                                    , SMSender.message,null,null);
//                            wait(15000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }
//
//            }
//
//        }.start();
//    }
//
//
//}
//
