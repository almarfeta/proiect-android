package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {

    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationChannel();
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
                new Intent(this, MyBroadcastReceiver.class), PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Button startNotificationButton = findViewById(R.id.startNotificationButton);
        startNotificationButton.setOnClickListener(e -> {
            setNotificationAlarm(1000);
        });

        Button stopNotificationButton = findViewById(R.id.stopNotificationButton);
        stopNotificationButton.setOnClickListener(e -> {
            cancelNotificationAlarm();
        });
    }

    public void setNotificationAlarm(long interval) {
        long triggerAtMillis = System.currentTimeMillis() + interval;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        if (Build.VERSION.SDK_INT >= 23) {
             alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }

    public void cancelNotificationAlarm() {
        alarmManager.cancel(pendingIntent);
    }

    private void notificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Reminder";
            String description = "Reminder Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}