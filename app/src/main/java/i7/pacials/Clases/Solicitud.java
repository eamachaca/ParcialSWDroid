package i7.pacials.Clases;

import java.io.Serializable;

/**
 * Created by i7 on 08-02-17.
 */

public class Solicitud implements Serializable {
    private String IDCliente;
    private String IDPersonal;
    private int montoSolicitado;
    private String key;

    public Solicitud() {
    }

    public String getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(String IDCliente) {
        this.IDCliente = IDCliente;
    }

    public String getIDPersonal() {
        return IDPersonal;
    }

    public void setIDPersonal(String IDPersonal) {
        this.IDPersonal = IDPersonal;
    }

    public int getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(int montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key){
        this.key=key;
    }
}
