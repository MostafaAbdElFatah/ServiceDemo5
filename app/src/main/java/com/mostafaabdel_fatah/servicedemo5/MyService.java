package com.mostafaabdel_fatah.servicedemo5;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;


public class MyService extends Service {

    static final int Job_1 = 1;
    static final int Job_2 = 2;
    static final int Job_1_ResPoint = 3;
    static final int Job_2_ResPoint = 4;
    Messenger messenger = new Messenger(new IcomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "Service Binding...", Toast.LENGTH_SHORT).show();
        return messenger.getBinder();
    }

    class IcomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Message msg1;
            String message;
            Bundle bundle = new Bundle();
            switch (msg.what) {
                case Job_1:
                    message = "This is The First Message From Service......";
                    msg1 = Message.obtain(null,Job_1_ResPoint);
                    bundle.putString("response_message",message);
                    msg1.setData(bundle);
                    try {
                        msg.replyTo.send(msg1);
                    }catch (Exception e){}
                    break;
                case Job_2:
                    message = "This is The Second Message From Service......";
                    msg1 = Message.obtain(null,Job_2_ResPoint);
                    bundle.putString("response_message",message);
                    msg1.setData(bundle);
                    try {
                        msg.replyTo.send(msg1);
                    }catch (Exception e){}
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
