package com.example.derongliu.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.derongliu.androidtest.R;

import java.util.List;

public class AidlActivity extends Activity {

    private IMyService myService;
    private TextView textView;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = IMyService.Stub.asInterface(service);
            try {
                List<Student> students = myService.getStudent();
                if (students != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Student student : students) {
                        stringBuilder.append(student.toString());
                    }
                    textView.setText(stringBuilder.toString());
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        textView = findViewById(R.id.aidl_text);
        Button button = findViewById(R.id.aidl_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent("com.example.derongliu.aidl.MyService");
                intentService.setPackage("com.example.derongliu.opengltest");
                intentService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AidlActivity.this.bindService(intentService, serviceConnection, BIND_AUTO_CREATE);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myService != null) {
            unbindService(serviceConnection);
        }
    }
}
