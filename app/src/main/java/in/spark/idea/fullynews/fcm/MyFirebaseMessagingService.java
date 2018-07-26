package in.spark.idea.fullynews.fcm;

/**
 * Created by Kanagasabapathi on 11/4/2017.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import in.spark.idea.fullynews.R;
import in.spark.idea.fullynews.TNHMain;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        Intent intent = new Intent(this, TNHMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

// this is a my insertion looking for a solution
        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.ic_launcher: R.drawable.ic_launcher;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle(remoteMessage.getFrom())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // build a complex notification, with buttons and such
            //
            notificationBuilder = notificationBuilder.setContent(getComplexNotificationView(remoteMessage));
        } else {
            // Build a simpler notification, without buttons
            //
            notificationBuilder = notificationBuilder.setContentTitle(remoteMessage.getFrom())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSmallIcon(android.R.drawable.ic_menu_gallery);
        }


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random r = new Random();
        int i1 = r.nextInt(180 - 5) + 5;

        notificationManager.notify(i1 /* ID of notification */, notificationBuilder.build());
    }

    private RemoteViews getComplexNotificationView(RemoteMessage remoteMessage) {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews notificationView = new RemoteViews(
                this.getPackageName(),
                R.layout.customnotif
        );

        // Locate and set the Image into customnotificationtext.xml ImageViews
        notificationView.setImageViewResource(
                R.id.imagenotileft,
                R.drawable.ic_launcher);

        // Locate and set the Text into customnotificationtext.xml TextViews
        notificationView.setTextViewText(R.id.titletext, remoteMessage.getNotification().getBody());

        return notificationView;
    }
}

