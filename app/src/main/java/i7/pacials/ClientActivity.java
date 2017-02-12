package i7.pacials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import i7.pacials.Clases.Cliente;
import i7.pacials.Controlador.ListaClienteAdaptador;

public class ClientActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaClienteAdaptador listaClienteAdaptador;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensajeRef = ref.child("Cliente");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        recyclerView= (RecyclerView) findViewById(R.id.recyClient);
        listaClienteAdaptador = new ListaClienteAdaptador();
        recyclerView.setAdapter(listaClienteAdaptador);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        mensajeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cliente post = dataSnapshot.getValue(Cliente.class);
                listaClienteAdaptador.adicionarCliente(post);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Cliente post = dataSnapshot.getValue(Cliente.class);
                listaClienteAdaptador.actualizarCliente(post);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
