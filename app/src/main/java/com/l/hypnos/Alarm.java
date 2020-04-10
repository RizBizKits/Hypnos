package com.l.hypnos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("working", "IT WORKS!!!!!!!!!!!");
        Toast.makeText(context,"Wake up",Toast.LENGTH_LONG).show();
        Uri alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), alarmRingtone);
        r.play();

    }
}
