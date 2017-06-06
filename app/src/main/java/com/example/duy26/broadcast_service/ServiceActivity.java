package com.example.duy26.broadcast_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by duy26 on 06/05/17.
 */

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnStart,btnStop;
    ProgressBar progressBar;
    TextView textView;
    Intent serviceIntent;

    ResponseReceiver receiver = new ResponseReceiver();
    public class ResponseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(com.example.duy26.broadcast_service.Service.Intent.Action_1)){
                int percent = intent.getIntExtra("percent",-1);
                new SetprogressBarTask().execute(percent);
            }
        }
    }
    private class SetprogressBarTask extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressBar.setProgress(integer);
            super.onPostExecute(integer);
            textView.setText(integer+"%");
            if(integer == 100){
                textView.setText("Hoan thanh");
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_service);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(com.example.duy26.broadcast_service.Service.Intent.Action_1));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private void init() {
        btnStart = (Button)findViewById(R.id.btn_start);
        btnStop = (Button)findViewById(R.id.btn_stop);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        textView = (TextView)findViewById(R.id.tv_percent);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start :
                serviceIntent = new Intent(this, com.example.duy26.broadcast_service.Service.Intent.class);
                startService(serviceIntent);
                break;
            case  R.id.btn_stop:
                if (serviceIntent!=null) stopService(serviceIntent);
        }
    }
}
