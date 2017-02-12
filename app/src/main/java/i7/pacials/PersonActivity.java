package i7.pacials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

import i7.pacials.Clases.Persona;
import i7.pacials.Clases.Personal;
import i7.pacials.Controlador.ListaPersonaAdaptador;

public class PersonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaPersonaAdaptador listaPersonaAdaptador;
    Personal deito;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensajeRef = ref.child("Persona");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        recyclerView= (RecyclerView) findViewById(R.id.recycPerson);
        listaPersonaAdaptador = new ListaPersonaAdaptador();
        recyclerView.setAdapter(listaPersonaAdaptador);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        deito= (Personal) getIntent().getExtras().getSerializable("Usuario");
        listaPersonaAdaptador.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona ss=listaPersonaAdaptador.getDataset().get(recyclerView.getChildPosition(v));
                Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                intent.putExtra("Usuario",deito);
                intent.putExtra("Persona",ss);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        /*mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Persona> listaPersona=new ArrayList<Persona>();
                for (DataSnapshot postDataSnapshot: dataSnapshot.getChildren()) {
                    Persona post = postDataSnapshot.getValue(Persona.class);
                    listaPersona.add(post);
                }
                listaPersonaAdaptador.adicionarListaPersona(listaPersona);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
        mensajeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Persona post = dataSnapshot.getValue(Persona.class);
                post.setCI(Integer.parseInt(dataSnapshot.getKey()));
                listaPersonaAdaptador.adicionarPersona(post);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Persona post = dataSnapshot.getValue(Persona.class);
                post.setCI(Integer.parseInt(dataSnapshot.getKey()));
                listaPersonaAdaptador.actualizarPersona(post);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Persona post = dataSnapshot.getValue(Persona.class);
                post.setCI(Integer.parseInt(dataSnapshot.getKey()));
                listaPersonaAdaptador.eliminarPersona(post);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*mensajeRefPruebaa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String x = dataSnapshot.getKey();
                Object xq = mensajeRefPruebaa.getDatabase();
                System.out.println(dataSnapshot.getValue());
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Persona post = postSnapshot.getValue(Persona.class);
                    post.setCI(Integer.parseInt(postSnapshot.getKey()));
                    System.out.println(post.getCI() + " - " + post.getFullName());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    public void aPerson(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterPersonActivity.class);
        String x=new Gson().toJson(listaPersonaAdaptador.getDataset());
        intent.putExtra("listPerson",x);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
