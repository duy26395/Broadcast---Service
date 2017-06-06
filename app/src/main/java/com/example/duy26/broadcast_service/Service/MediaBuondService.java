package com.example.duy26.broadcast_service.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.duy26.broadcast_service.R;


/**
 * Created by duy26 on 06/05/17.
 */
public class MediaBuondService extends Service {
    private IBinder iBinder = new MediaBinder();
    private MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.thuong);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer.start();
        Log.i("Media","Start");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.reset();
        Log.i("Media","Stop");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
    public void previous(int second){
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-1000*second);
    }
    public void next(int secon)
    {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+1000*secon);
    }
    public class MediaBinder extends Binder{
        public MediaBuondService getService(){
            return MediaBuondService.this;
        }
    }
}
