package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aidlserver.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    IMyAidlInterface aidlService;
    private static String TAG = "MainActivity";

    private ServiceConnection connect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            aidlService = IMyAidlInterface.Stub.asInterface(iBinder);
            Log.d(TAG, "Remote Service Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("AIDLService");
        intent.setPackage("com.example.aidlserver");
        bindService(intent,connect,BIND_AUTO_CREATE);

        Log.d(TAG, "Service called");
        TextView btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int color = aidlService.getColor();
                    view.setBackgroundColor(color);
                }catch(Exception e){

                }
            }
        });
    }
}