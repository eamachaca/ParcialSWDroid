package i7.pacials;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import i7.pacials.Clases.Conexion;
import i7.pacials.Clases.Personal;

public class LoginActivity extends AppCompatActivity {

    EditText user;
    EditText pass;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensajeRef = ref.child("Personal");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=(EditText) findViewById(R.id.EUser);
        pass=(EditText) findViewById(R.id.EPass);
    }

    public void login(View view) {
        if (user.getText()==null || pass.getText()==null) return;
        mensajeRef=mensajeRef.child(user.getText().toString());
        mensajeRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String x=dataSnapshot.child("clave").getValue(String.class);
                        if (x.equals(pass.getText().toString()))
                        {
                            Object xxx=dataSnapshot.getValue();
                            Personal xq=dataSnapshot.getValue(Personal.class);
                            xq.setUsuario(user.getText().toString());
                            Intent intent = new Intent(obtenercontext(), InterActivity.class);
                            intent.putExtra("Usuario",xq);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("error", "getUser:onCancelled", databaseError.toException());
                        // ...
                    }
                });
    }
    public Context obtenercontext(){
        return this.getApplicationContext();
    }
    public void cancelar(View view) {
        this.finish();
    }

}
