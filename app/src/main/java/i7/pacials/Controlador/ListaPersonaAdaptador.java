package i7.pacials.Controlador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import i7.pacials.Clases.Persona;
import i7.pacials.R;

/**
 * Created by i7 on 27-11-16.
 */

public class ListaPersonaAdaptador extends RecyclerView.Adapter<ListaPersonaAdaptador.ViewHolder> implements View.OnClickListener{
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
    private ArrayList<Persona> dataset;
    public ListaPersonaAdaptador(){
        dataset=new ArrayList<>();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.persona,parent,false);
        view.setOnClickListener(this);//esta linea tambien es del click
        return new ViewHolder(view);
    }

    public ArrayList<Persona> getDataset() {
        return dataset;
    }

    public void setDataset(ArrayList<Persona> dataset) {
        this.dataset = dataset;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataset.size()==position) return;
        Persona p= dataset.get(position);
        holder.nombreTextView.setText(p.getFullName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarPersona(Persona x)
    {
        dataset.add(x);
        notifyDataSetChanged();
    }
    public void adicionarListaPersona(ArrayList<Persona> listaPersona) {
        dataset.addAll(listaPersona);
        notifyDataSetChanged();
    }
    public void actualizarPersona(Persona x)
    {
        if (dataset.remove(x)){
            dataset.add(x);
            notifyDataSetChanged();
        }
    }
    public void eliminarPersona(Persona x){
        dataset.remove(x);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreTextView;
        public ViewHolder(View itemView){
            super(itemView);
            nombreTextView= (TextView) itemView.findViewById(R.id.NombrePersona);
        }
    }
}
