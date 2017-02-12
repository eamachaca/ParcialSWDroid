package i7.pacials.Controlador;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import i7.pacials.Clases.Image;
import i7.pacials.R;

/**
 * Created by i7 on 30-11-16.
 */

public class ListaImagenesAdapter extends RecyclerView.Adapter<ListaImagenesAdapter.ViewHolder> implements View.OnClickListener{


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
    private ArrayList<Image> dataset; //string por las URL xD
    public ListaImagenesAdapter(){
        dataset=new ArrayList<>();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagen,parent,false);
        view.setOnClickListener(this);//esta linea tambien es del click
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataset.size()==position) return;
        Image zp= dataset.get(position);
        Bitmap p=zp.getImage();
        holder.image.setImageBitmap(p);
        holder.nameImage.setText(zp.getName());
        holder.image.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarImagen(Image x)
    {
        dataset.add(x);
        notifyDataSetChanged();
    }
    public void adicionarListaImagen(ArrayList<Image> listaPersona) {
        dataset.addAll(listaPersona);
        notifyDataSetChanged();
    }
    public void actualizarImagen(Image x)
    {
        if (dataset.remove(x)){
            dataset.add(x);
            notifyDataSetChanged();
        }
    }

    public ArrayList<Image> getDataset() {
        return dataset;
    }

    public void eliminarImagen(Image x){
        dataset.remove(x);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView nameImage;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.fImagenView);
            nameImage = (TextView) itemView.findViewById(R.id.nameImage);
        }

    }
}
