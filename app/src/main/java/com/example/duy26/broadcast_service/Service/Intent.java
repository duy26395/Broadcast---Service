package com.example.duy26.broadcast_service.Service;

import android.app.IntentService;
import android.os.SystemClock;
import android.support.annotation.Nullable;

/**
 * Created by duy26 on 06/05/17.
 */

public class Intent extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static  final String Action_1 = "SET_PROGRESSBAR";
    public Intent(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable android.content.Intent intent) {
        android.content.Intent broadcastintent = new android.content.Intent();
        broadcastintent.setAction(Intent.Action_1);
        for (int i = 0; i <= 100; i++) {
            broadcastintent.putExtra("percent", i);
            sendBroadcast(broadcastintent);
            SystemClock.sleep(100);
        }
    }


}
