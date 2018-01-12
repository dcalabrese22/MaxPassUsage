package com.dcalabrese22.dan.skipassusage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.dcalabrese22.dan.skipassusage.MainActivity;
import com.dcalabrese22.dan.skipassusage.R;
import com.google.android.gms.location.LocationResult;

public class LocationChangedReceiver extends BroadcastReceiver {

    public static final String INTENT_FILTER =
            "com.dcalabrese22.dan.LocationChangedReceiver.NEW_LOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        LocationResult locationResult = LocationResult.extractResult(intent);
        if (locationResult != null) {
            Location location = locationResult.getLastLocation();
            double lat = location.getLatitude();
            double longitude = location.getLongitude();
            String ladAndLong = String.valueOf(lat + "," + longitude);
            Toast.makeText(context, ladAndLong, Toast.LENGTH_LONG).show();
        }
        showNotification(context, buildNotificationIntent(context));
    }

    private PendingIntent buildNotificationIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, 4,
                intent, PendingIntent.FLAG_ONE_SHOT);
    }

    private void showNotification(Context context, PendingIntent pendingIntent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notifyId = 1;
        String channelId = "com.dcalabrese22.dan.skipassuse.IN_LOCATION";
        String title = "title";
        String body = "body";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Message";
            String description = "Notify the user if they are near a ski resort";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
            Notification.Builder builder = new Notification.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            notificationManager.notify(notifyId, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            notificationManager.notify(notifyId, builder.build());
        }
    }
}
