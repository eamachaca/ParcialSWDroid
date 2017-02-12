package i7.pacials.Clases;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

/**
 * Created by i7 on 06-02-17.
 */

 public class Conexion {
    public static String URL="https://deitodeito.vagrantshare.com/";

    public static void addToken(String token){
        SyncHttpClient client=new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("token", token);
        client.post(URL+"addtoken", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("deito","no se que es este msj");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Error","tampoco se que es este");
            }
        });
    }
}
