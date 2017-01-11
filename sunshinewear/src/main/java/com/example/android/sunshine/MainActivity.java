package com.example.android.sunshine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity {
    private TextView currentTimeText;
    private TextView currentDateText;
    private TextView highTempText;
    private TextView lowTempText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        final String dayOfWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        final String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        final int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        final int year = c.get(Calendar.YEAR);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);



        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                currentTimeText = (TextView) stub.findViewById(R.id.time);
                currentDateText = (TextView) stub.findViewById(R.id.date);
                highTempText = (TextView) stub.findViewById(R.id.highTemp);
                lowTempText = (TextView) stub.findViewById(R.id.lowTemp);

                //currentTimeText.setText(hour + ":" + minute);
                currentDateText.setText(dayOfWeek + ", " + month + " " + dayOfMonth + " " + year);
                highTempText.setText("25" + (char) 0x00B0);
                lowTempText.setText("22" + (char) 0x00B0);
            }
        });

    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            // Display message in UI
            currentTimeText.setText(message);
        }
    }
}
