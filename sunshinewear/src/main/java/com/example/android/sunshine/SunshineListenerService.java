package com.example.android.sunshine;

/**
 * Created by brian on 08/01/2017.
 */

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;


public class SunshineListenerService extends WearableListenerService {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals("/wear_message")) {
            final String message = new String(messageEvent.getData());
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }
}