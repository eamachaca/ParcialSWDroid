package i7.pacials.FirebaseNotification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by i7 on 12-11-16.
 */

import i7.pacials.Clases.Conexion;

public class MyFirebaseInstanceService extends FirebaseInstanceIdService{
    public static final String TAG="NOTICIAS";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"token: "+token);
        Conexion.addToken(token);

    }
}
