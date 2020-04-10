package com.l.hypnos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.AlarmManager.AlarmClockInfo;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener  {

    private FloatingActionButton add_btn;
    private Button btn_dismiss;
    private Button btn_unset;
    Calendar c = Calendar.getInstance();
    TextView txt_timeSet;
    int counter=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_btn = (FloatingActionButton) findViewById(R.id.add_btn);
        btn_dismiss = (Button)findViewById(R.id.btn_dismiss);
        btn_unset = (Button)findViewById(R.id.btn_unset);

        txt_timeSet = (TextView) findViewById(R.id.txt_timeSet);
        btn_dismiss.setVisibility(View.INVISIBLE);


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter += 1;
                if (counter == 10) {
                    txt_timeSet.setText("No alarms set. You're waking by yourself for now.");
                    Alarm.stopRingtone();
                    dismissAlarm();
                    counter = 0;
                    btn_dismiss.setVisibility(View.INVISIBLE);
                } else {
                    int tapNum = 10;
                    int tapLeft = tapNum - counter;
                    txt_timeSet.setText("Keeping Clicking! Only " + tapLeft + " taps to go." );
                }

            }
        });

        btn_unset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm.stopRingtone();
                dismissAlarm();
                txt_timeSet.setText("No alarms set. You're waking by yourself for now.");

                btn_dismiss.setVisibility(View.INVISIBLE);
            }
        });
    }

     void scheduleAlarm(long timeToRing) {
         Intent intent = new Intent(MainActivity.this, Alarm.class);
         PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
         AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

         Log.i("the time is = ", "" + timeToRing);
         Log.i("the CURRENT time is = ", "" + System.currentTimeMillis());

         am.setExact(AlarmManager.RTC_WAKEUP,timeToRing, pi);
     }

    void dismissAlarm() {
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
    }


    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    // DATE FORMATTER
    private String getDate(long milli) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeInMillis(milli);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String dateString = simpleDateFormat.format(calendar.getTimeInMillis());
        Log.i("CONVERSION", "" + dateString);

        return dateString;
    }

    // DATE FORMATTER
    private String getTimeAndDate(long milli) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeInMillis(milli);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a 'on' EEE, MMM d");
        String dateString = simpleDateFormat.format(calendar.getTimeInMillis());
        Log.i("CONVERSION =", "" + dateString);

        return dateString;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        // Ensure leading 0
        String timeFormatted = "%1$02d";
        String finalMinSet = String.format(timeFormatted, minute);
        String finalHourSet = String.format(timeFormatted, hourOfDay);
        String finalTime = finalHourSet + ":" + finalMinSet;


        Log.i("1st CURRENT HOUR = ", "" + c.HOUR_OF_DAY);
        Log.i("1st CURRENT MIN = ", "" + c.MINUTE);

        c.setTimeInMillis(System.currentTimeMillis());

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 000);


        long setTime = c.getTimeInMillis();

        if (setTime > System.currentTimeMillis()) {
            scheduleAlarm(setTime);
            String whatDate = getTimeAndDate(setTime);
            txt_timeSet.setText("Alarm set! Hypnos will wake you at " + whatDate);

        } else { // set time for next day
            long nextDay = 24 * 60 * 60 * 1000;
            long finTime = setTime + nextDay;
            String whatDate = getTimeAndDate(finTime);
            txt_timeSet.setText("Sweet! Hypnos will wake you at " + whatDate);
        }

        btn_dismiss.setVisibility(View.VISIBLE);

    }

    public String getTime() {
        TextView txt_timeSet = (TextView) findViewById(R.id.txt_timeSet);
        return txt_timeSet.toString();
    }

}
