package i7.pacials.Clases;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by i7 on 26-11-16.
 */

public class Persona implements Serializable{
    private String Birthdate;
    private String CivilStatus;
    private String Direction;
    private String FullName;
    private String Occupation;
    private int CI;
    public Persona(){

    }

    public Persona(String birthdate, String civilStatus, String direction, String fullName, String occupation) {
        Birthdate = birthdate;
        CivilStatus = civilStatus;
        Direction = direction;
        FullName = fullName;
        Occupation = occupation;
    }

    @Override
    public boolean equals(Object o) {
        try{
            Gson w=new Gson();
            String q=w.toJson(o);
            Persona x= w.fromJson(q,Persona.class);
            return x.getCI()==CI;
        }catch (Exception e){
        }
        return false;
    }

    public boolean equals(Persona x) {
        return CI==x.getCI();
    }

    public int getCI() {
        return CI;
    }

    public void setCI(int CI) {
        this.CI = CI;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getCivilStatus() {
        return CivilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        CivilStatus = civilStatus;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }
}
