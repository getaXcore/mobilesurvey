package surveyor.id.com.mobilesurvey.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.security.acl.NotOwnerException;

import surveyor.id.com.mobilesurvey.R;

/**
 * Created by 001810240 on 4/6/2020.
 */

public class NotificationClass {
    private static final String NOTIF_CHANNEL_ID = "surveyor.id.com.mobilesurvey";
    private static final String CHANNEL_NAME = "MY Background Service";
    private static final int NOTIFICATION_ID = 8195825;
    private final Context context;

    public NotificationClass(Context context){
        this.context = context;
    }

    public void NotifyBuilder(String textTitle,String textContent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,NOTIF_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_bell_notification)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Mobile Survey");
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
