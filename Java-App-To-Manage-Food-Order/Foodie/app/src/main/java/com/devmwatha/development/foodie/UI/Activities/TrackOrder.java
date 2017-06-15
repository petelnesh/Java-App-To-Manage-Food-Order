package com.devmwatha.development.foodie.UI.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.devmwatha.development.foodie.Models.Beans.FoodItems;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.UI.Fragments.FragmentSoftDrinks;

import java.util.concurrent.TimeUnit;

public class TrackOrder extends AppCompatActivity {
    TextView textView;
    FoodItems foodItems;
    private static CountDownTimer countDownTimer;
    private static int notificationid;
    int getMinutes;
        int remaining_time;
    private static final String TIMER_STATE_KEY = "timergone";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView) findViewById(R.id.calculatetime);
        setSupportActionBar(toolbar);
        int time=2;
        getMinutes=time;
        int noOfMinutes = getMinutes * 60 * 1000;
        startTimer(noOfMinutes);

        if (savedInstanceState!=null && savedInstanceState.containsKey(TIMER_STATE_KEY))
        {
            startTimer(remaining_time);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(TIMER_STATE_KEY,remaining_time);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt("remaining");
        startTimer(remaining_time*60000);
    }

    private void postAlert() {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setContentTitle("We have delivered!")
        .setContentText("your food is here now")
.setSmallIcon(R.drawable.a)
        .setAutoCancel(false)
        .setTicker("Pay now");
        Intent intent=new Intent(this,TrackOrder.class);
        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(TrackOrder.class).addNextIntent(intent);
        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationid,builder.build());
        notificationid++;

    }

    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds

                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                textView.setText(hms);//set text
                String remaining=textView.getText().toString();
                String hour=remaining.substring(0,1);
                String minutes=remaining.substring(3,4);
                String seconds=remaining.substring(6,7);
                int time_in_hrs=Integer.parseInt(hour)*3600000;
                int time_in_min=Integer.parseInt(minutes)*60000;
                int time_in_sec=Integer.parseInt(seconds);
                int total_remain=time_in_hrs+time_in_min+time_in_sec;
                remaining_time = total_remain;
            }

            public void onFinish() {
                postAlert();
                textView.setText("WE ARE AT YOUR DOOR! OPEN..."); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
            }
        }.start();
    }
}
