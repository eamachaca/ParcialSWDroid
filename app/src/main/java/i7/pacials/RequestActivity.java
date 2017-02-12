package i7.pacials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import i7.pacials.Clases.Persona;
import i7.pacials.Clases.Personal;
import i7.pacials.Clases.Solicitud;

public class RequestActivity extends AppCompatActivity {

    Personal deito;
    Persona cliente;
    EditText monto;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensajeRef = ref.child("Solicitud");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        deito = (Personal) getIntent().getExtras().getSerializable("Usuario");
        cliente=(Persona)getIntent().getExtras().getSerializable("Cliente");
        EditText fSolicitud=(EditText)findViewById(R.id.EFechaSolicitud);
        Date dt = new Date();
        String xs = dt.toGMTString();
        fSolicitud.setText(xs);
        monto=(EditText)findViewById(R.id.EMontoRequerido);
    }

    public void aImagen(View view) {
        if ( !monto.getText().toString().isEmpty()){
            try {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String q=mensajeRef.push().getKey();
                Solicitud w=new Solicitud();
                w.setIDPersonal(deito.getUsuario());
                w.setMontoSolicitado(Integer.parseInt(monto.getText().toString()));
                mensajeRef.child(q).setValue(w);
                intent.putExtra("request",w);
                startActivity(intent);
                return;
            }catch (Exception e){

            }
        }
        Toast.makeText(this,"Porfavor Inserta un monto valido",Toast.LENGTH_LONG);
        return;
    }

    public void aClose(View view) {
        this.finish();
    }
}
