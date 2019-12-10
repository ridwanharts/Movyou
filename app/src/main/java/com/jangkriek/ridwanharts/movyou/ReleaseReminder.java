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

import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;

import java.util.Calendar;
import java.util.List;

import static com.jangkriek.ridwanharts.movyou.SettingsActivity.NOTIFICATION_CHANNEL_ID;
import static com.jangkriek.ridwanharts.movyou.SettingsActivity.NOTIFICATION_ID;

public class ReleaseReminder extends BroadcastReceiver {

    private static int notifId;

    public void cancelAlarmNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent(context));

    }

    private static PendingIntent pendingIntent(Context context){
        Intent intent = new Intent(context, DailyReminder.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String judul = intent.getStringExtra("mjudul");
        int id = intent.getIntExtra("mid", 0);
        long movieId = intent.getLongExtra("m_id", 0);
        String deskripsi = intent.getStringExtra("mdeskripsi");
        String tanggal = intent.getStringExtra("mtanggal");
        String gambar = intent.getStringExtra("mgambar");
        String rating = intent.getStringExtra("mrating");
        ItemResult itemResult = new ItemResult(movieId, judul, deskripsi, tanggal, gambar, rating);
        String desc = String.valueOf(String.format(context.getString(R.string.release_today), judul));
        sendNotif(context, context.getString(R.string.app_name), desc, id, itemResult);

    }

    private void sendNotif(Context context, String judul, String deskripsi, int id, ItemResult itemResult) {
        NotificationManager notif = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(judul)
                .setContentText(deskripsi)
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

    public void setAlarmRelease(Context context, List<ItemResult>itemResults){
        int notidDelay=0;
        for(int i=0;i<itemResults.size();i++){
            cancelAlarmNotification(context);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReleaseReminder.class);
            intent.putExtra("mjudul", itemResults.get(i).getmJudul());
            intent.putExtra("mdeskripsi", itemResults.get(i).getmDeskripsi());
            intent.putExtra("mtanggal", itemResults.get(i).getmTanggal());
            intent.putExtra("mgambar", itemResults.get(i).getmGambar());
            intent.putExtra("mrating", itemResults.get(i).getmRating());
            intent.putExtra("mid", notifId);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND,1);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+notidDelay,AlarmManager.INTERVAL_DAY, pIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+notidDelay, pendingIntent(context));
            }
            notifId=notifId+1;
            notidDelay=notidDelay+3000;
        }
        Toast.makeText(context, "Release movie reminder notification enabled", Toast.LENGTH_SHORT).show();
    }
}
