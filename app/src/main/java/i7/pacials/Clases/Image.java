package i7.pacials.Clases;

import android.graphics.Bitmap;

import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;

/**
 * Created by i7 on 30-11-16.
 */

public class Image implements Serializable{
    private String cod;
    private String codigoRequisito;
    private String URL;
    private Bitmap image;
    private String name;
    public Image(String codigoRequisito, String URL) {
        this.codigoRequisito = codigoRequisito;
        this.URL = URL;
    }

    @Override
    public boolean equals(Object obj) {
        try{
            Image i=(Image)obj;
            return i.getCod()==this.cod;
        }catch (Exception e) {
            return super.equals(obj);
        }
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Image() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getCodigoRequisito() {
        return codigoRequisito;
    }

    public void setCodigoRequisito(String codigoRequisito) {
        this.codigoRequisito = codigoRequisito;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
