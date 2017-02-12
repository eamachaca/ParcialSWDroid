package i7.pacials.FirebaseNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import i7.pacials.Clases.Conexion;
import i7.pacials.PersonActivity;
import i7.pacials.R;

/**
 * Created by i7 on 12-11-16.
 */

public class MyFirebaseMessangingService extends FirebaseMessagingService{

    public static final String TAG="NOTICIAS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String from = remoteMessage.getFrom();
        Log.d(TAG,"Mensaje recibido de:"+from);

        if (remoteMessage.getNotification()!= null){
            Log.d(TAG,"Notificacion: "+remoteMessage.getNotification().getBody());
            
            mostrarNotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size()>0){
            Log.d(TAG, "Data: "+remoteMessage.getData());
            mostrarNotificacion("Este es un mensaje",remoteMessage.getData().get("message"));
        }

        //Conexion.addToken(remoteMessage.getTo());
    }

    private void mostrarNotificacion(String title, String body) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent= new Intent(this,PersonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_event)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }
}
