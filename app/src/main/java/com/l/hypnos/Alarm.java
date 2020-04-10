package com.l.hypnos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {
    public static final int REQUEST_CODE = 222;
    private static Ringtone r  = null;
    private static Vibrator vib = null;


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"Wake up",Toast.LENGTH_LONG).show();
        vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] mVibratePattern = new long[]{0, 400, 200, 400};

        vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, 0));

        Uri alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(context.getApplicationContext(), alarmRingtone);
        r.play();

    }

    public static void stopRingtone() {
        r.stop();
        vib.cancel();

    }
}
