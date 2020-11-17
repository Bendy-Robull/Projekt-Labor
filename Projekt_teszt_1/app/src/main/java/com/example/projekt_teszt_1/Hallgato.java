package com.example.projekt_teszt_1;

import android.os.Parcel;
import android.os.Parcelable;

public class Hallgato implements Parcelable {
    String neptun;
    String name;
    boolean sex;
    String faculty;
    boolean trusty;
    int eventnum;

    public Hallgato(String _nep, String _name, boolean _sex, String _fac, boolean _trust, int _enum) {
        neptun = _nep;
        name = _name;
        sex = _sex;
        faculty = _fac;
        trusty = _trust;
        eventnum = _enum;
    }
    protected Hallgato(Parcel in) {
        neptun = in.readString();
        name = in.readString();
        sex = in.readByte() !=0;
        faculty = in.readString();
        trusty = in.readByte() != 0;
        eventnum = in.readInt();
    }

    public String getNeptun() {
        return neptun;
    }

    public void setNeptun(String neptun) {
        this.neptun = neptun;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        if(sex){
            return "Nő";
        }
        else {
            return "Férfi";
        }
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
    public boolean isFemale() {
        return sex;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public boolean isTrusty() {
        return trusty;
    }

    public void setTrusty(boolean trusty) {
        this.trusty = trusty;
    }

    public int getEventnum() {
        return eventnum;
    }

    public void setEventnum(int eventnum) {
        this.eventnum = eventnum;
    }
    public Hallgato() {
    }
    public String getEvents(){ //szipi-szupi hatványozós fv.
        String eredmeny="";
        int ec=eventnum;
        int hatvany=0;
        while (ec>1){
            hatvany=(int)(Math.log(ec)/Math.log(2));
            ec-=(int)Math.pow(2, hatvany);

            eredmeny+=" "+(hatvany)+".";
        }
        if(ec==1){
            eredmeny+=" 1.";
        }
        if(eredmeny==""){
            eredmeny="Nem szerepel egyik eseményen sem.";
        }
        return eredmeny;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(neptun);
        dest.writeString(name);
        dest.writeByte((byte) (sex ? 1 : 0));
        dest.writeString(faculty);
        dest.writeByte((byte) (trusty ? 1 : 0));
        dest.writeInt(eventnum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Hallgato> CREATOR = new Creator<Hallgato>() {
        @Override
        public Hallgato createFromParcel(Parcel in) {
            return new Hallgato(in);
        }

        @Override
        public Hallgato[] newArray(int size) {
            return new Hallgato[size];
        }
    };
}
