package com.example.su.musicsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by su on 5/2/18.
 */

public class AudioPlayerBroadcastReceiver extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        System.out.println("intent action = " + action);
        long id = intent.getLongExtra("id", -1);
    }
}
