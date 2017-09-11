package com.mostafaabdel_fatah.servicedemo5;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Messenger messenger = null;
    Boolean isBind = false;
    static final int Job_1 = 1;
    static final int Job_2 = 2;
    static final int Job_1_ResPoint = 3;
    static final int Job_2_ResPoint = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        Intent intent = new Intent(MainActivity.this,MyService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void getMessage_btnClicked(View view) {
        String text = ((Button)view).getText().toString();
        if (text.equals("Get First Message form service")){
            Message msg = Message.obtain(null,Job_1);
            msg.replyTo = new Messenger(new ResponseHandle());
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else if (text.equals("Get Second Message form service")) {
            Message msg = Message.obtain(null,Job_2);
            msg.replyTo = new Messenger(new ResponseHandle());
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messenger = new Messenger(iBinder);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBind = false;isBind = false;
            messenger = null;
        }
    };

    @Override
    protected void onStop() {
        isBind = false;
        messenger = null;
        unbindService(serviceConnection);
        super.onStop();
    }

    class ResponseHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String message;
            switch (msg.what) {
                case Job_1_ResPoint:
                case Job_2_ResPoint:
                    message = msg.getData().getString("response_message");
                    textView.setText(message);
                    break;
            }
            super.handleMessage(msg);
        }
    }

}
