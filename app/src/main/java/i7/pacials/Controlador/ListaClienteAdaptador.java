package i7.pacials.Controlador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import i7.pacials.Clases.Cliente;
import i7.pacials.Clases.Persona;
import i7.pacials.R;

/**
 * Created by i7 on 29-11-16.
 */

public class ListaClienteAdaptador extends RecyclerView.Adapter<ListaClienteAdaptador.ViewHolder> implements View.OnClickListener{
    //realizando el click
    private View.OnClickListener listener;
    public void setListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if (listener !=null)
            listener.onClick(v);
    }

    //no se que hize aca xD
    private ArrayList<Cliente> dataset;
    public ListaClienteAdaptador(){
        dataset=new ArrayList<>();
    }
    @Override
    public ListaClienteAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente,parent,false);
        view.setOnClickListener(this);//esta linea tambien es del click
        return new ListaClienteAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListaClienteAdaptador.ViewHolder holder, int position) {
        Cliente p= dataset.get(position);
        holder.nombreTextView.setText(p.getPersona().getFullName());
        holder.CodCliente.setText(Integer.toString(p.getPersonID()));
    }

    public void eliminarCliente(Cliente x){
        dataset.remove(x);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }
    final Cliente y=new Cliente();
    public void adicionarCliente(Cliente x)
    {
        y.setNumberPhone(x.getNumberPhone());
        y.setPersonID(x.getPersonID());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mensajeRef = ref.child("Persona").child(""+x.getPersonID());
        mensajeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Persona post = dataSnapshot.getValue(Persona.class);
                post.setCI(Integer.parseInt(dataSnapshot.getKey()));
                y.setPersona(post);
                dataset.add(y);
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void actualizarCliente(Cliente x)
    {
        if (dataset.remove(x)){
            dataset.add(x);
            notifyDataSetChanged();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView CodCliente;
        private TextView nombreTextView;
        private TextView clientID;
        public ViewHolder(View itemView){
            super(itemView);
            CodCliente=(TextView) itemView.findViewById(R.id.CodCliente);
            nombreTextView= (TextView) itemView.findViewById(R.id.NombreCliente);
            nombreTextView= (TextView) itemView.findViewById(R.id.clientID);
        }
    }
}
