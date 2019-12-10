package com.jangkriek.ridwanharts.movyou;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.jangkriek.ridwanharts.movyou.main.MenuFragment;

import java.util.Calendar;

import static com.jangkriek.ridwanharts.movyou.SettingsActivity.NOTIFICATION_CHANNEL_ID;
import static com.jangkriek.ridwanharts.movyou.SettingsActivity.NOTIFICATION_ID;

public class DailyReminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        sendNotif(context,"Movyou", "Don't forget to see movie update", NOTIFICATION_ID);
    }

    private void sendNotif(Context context, String judul, String deskripsi, int id){

        NotificationManager notif = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(judul)
                .setContentText(deskripsi)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{500, 500, 500, 500, 500});
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notif.createNotificationChannel(notificationChannel);
        }

        notif.notify(id, builder.build());

    }

    public void setAlarmDaily(Context context){
        cancelAlarmNotification(context);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,1);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent(context));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent(context));
        }
        Toast.makeText(context, "Daily reminder notification enabled", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarmNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent(context));

    }

    private static PendingIntent pendingIntent(Context context){
        Intent intent = new Intent(context, DailyReminder.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
