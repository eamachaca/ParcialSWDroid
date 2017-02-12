package i7.pacials.Clases;

import java.io.Serializable;

/**
 * Created by i7 on 29-11-16.
 */

public class Personal implements Serializable{
    private String Usuario;
    private int PersonID;
    private Persona persona;
    private String clave;

    public Personal( int personID, Persona persona,String usuario) {
        PersonID = personID;
        this.persona = persona;
        Usuario=usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    public Personal() {

    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }
    //public void i
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
