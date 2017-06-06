package com.example.duy26.broadcast_service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.duy26.broadcast_service.Service.MediaBuondService;
import com.example.duy26.broadcast_service.Service.MediaUnboundService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStartservice,btnStopservice,btnUnbin,btnPrevious,btnNext,btnbin;
    private TextView st1,st2;
    MediaBuondService mediaBuondService;
    boolean isBinder = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btnStartservice = (Button)findViewById(R.id.btn_start_service);
        btnStopservice = (Button)findViewById(R.id.btn_stop_service);
        btnbin = (Button)findViewById(R.id.btn_bind_service);
        btnUnbin = (Button)findViewById(R.id.btn_unbin_service);
        btnNext = (Button)findViewById(R.id.btn_next);
        btnPrevious = (Button)findViewById(R.id.btn_previous);

        st1 = (TextView)findViewById(R.id.tv_status);
        st2 = (TextView)findViewById(R.id.tv_status2);

        btnStartservice.setOnClickListener(this);
        btnStopservice.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnUnbin.setOnClickListener(this);
        btnbin.setOnClickListener(this);

    }
    private ServiceConnection serviceConnection= new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaBuondService.MediaBinder mediaBinder = (MediaBuondService.MediaBinder)service;
            mediaBuondService = mediaBinder.getService();
            isBinder = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinder = false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                st1.setText("Start");
                startService(new Intent(this, MediaUnboundService.class));
                break;
            case R.id.btn_stop_service:
                st1.setText("Stop");
                stopService(new Intent(this, MediaUnboundService.class));
                break;


            case R.id.btn_bind_service:
                if (!isBinder) {
                    Intent intent = new Intent(this, MediaBuondService.class);
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                    isBinder = true;
                    st2.setText("đã biner");
                }
                break;
            case R.id.btn_unbin_service:
                if (isBinder) {
                    unbindService(serviceConnection);
                    isBinder = false;
                    st2.setText("Unbin");
                }
                break;
            case R.id.btn_previous:
                if (isBinder) mediaBuondService.previous(2);
                break;
            case R.id.btn_next:
                if(isBinder)mediaBuondService.next(2);
        }
    }
    public MainActivity(){ super();}

    @Override
    protected void onStop() {
        super.onStop();
        if(isBinder){
            unbindService(serviceConnection);
            isBinder = false;
            st2.setText("unbund");
        }
    }

}

