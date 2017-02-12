package i7.pacials.Clases;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by i7 on 29-11-16.
 */

public class Cliente implements Serializable{
    private ArrayList<Integer> NumberPhone;
    private int PersonID;
    String ClientID;
    Persona persona;

    public Cliente(ArrayList<Integer> numberPhone, int personID, Persona persona) {
        NumberPhone = numberPhone;
        PersonID = personID;
        this.persona = persona;
    }

    public Cliente() {
    }

    @Override
    public boolean equals(Object o) {

        return super.equals(o);
    }

    public ArrayList<Integer> getNumberPhone() {
        return NumberPhone;
    }

    public void setNumberPhone(ArrayList<Integer> numberPhone) {
        NumberPhone = numberPhone;
    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
