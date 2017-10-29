package logicturtle.innovaceraccidentalert;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import logicturtle.innovaceraccidentalert.Activity.AccelerometerActivity;

import static android.content.Context.NOTIFICATION_SERVICE;


public class PushNotification {

    private PushNotification(){

    }

    public static void sendPushNotification(Context context, String contentTitle, String message, int drawableNotificationIcon){
            Intent intent = new Intent(context, AccelerometerActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
            android.app.Notification n = new android.app.Notification.Builder(context)
                    .setContentTitle(contentTitle)
                    .setContentText(message)
                    .setSmallIcon(drawableNotificationIcon)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_person_black_24dp,
                            "Dismiss",pIntent)
                    .build();
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
    }

}
