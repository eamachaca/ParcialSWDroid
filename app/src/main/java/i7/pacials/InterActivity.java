package i7.pacials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import i7.pacials.Clases.Persona;
import i7.pacials.Clases.Personal;

public class InterActivity extends AppCompatActivity {
    Personal x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x=(Personal)getIntent().getExtras().getSerializable("Usuario");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mensajeRef = ref.child("Persona").child(""+x.getPersonID());
        setContentView(R.layout.activity_inter);
        mensajeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Persona xq=dataSnapshot.getValue(Persona.class);
                xq.setCI(x.getPersonID());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Ha surgido un error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void PasarCLiente(View view) {
        Intent intent = new Intent(this, ClientActivity.class);
        intent.putExtra("Usuario",x);
        startActivity(intent);
    }

    public void PasarPersona(View view) {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("Usuario",x);
        startActivity(intent);
    }
}
