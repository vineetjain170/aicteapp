package com.vscholars.stack2code.aicte_phaseone;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by shaggy on 10-09-2017.
 */

public class firebaseMessage  extends FirebaseMessagingService {
    SharedPreferences sharedPreferences;
    int noOfNotification;
    String notification_title,notification_body,notification_message;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below

        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        String message=remoteMessage.getData().get("message");

        sendNotification(remoteMessage);
        sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        noOfNotification=sharedPreferences.getInt("notification_count",-1);
        notification_title=new String();
        notification_body=new String();
        notification_message=new String();
        if(noOfNotification==-1){
            noOfNotification=1;
            editor.putInt("notification_count",1);
            editor.commit();
            notification_title=title+"!@#$%^&*()";
            notification_body=body+"!@#$%^&*()";
            notification_message=message+"!@#$%^&*()";
        }else {

            editor.putInt("notification_count",++noOfNotification);
            editor.commit();
            String titlePrev=sharedPreferences.getString("notification_title",null);
            String bodyPrev=sharedPreferences.getString("notification_body",null);
            String messagePrev=sharedPreferences.getString("notification_message",null);
            notification_title=title+"!@#$%^&*()"+titlePrev;
            notification_body=body+"!@#$%^&*()"+bodyPrev;
            notification_message=message+"!@#$%^&*()"+messagePrev;

        }

        editor.putString("notification_title",notification_title);
        editor.putString("notification_body",notification_body);
        editor.putString("notification_message",notification_message);
        editor.commit();
    }
    private void sendNotification(RemoteMessage remoteMessage) {

        Intent intent = new Intent(this, Announcements.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);

    }

}
