package com.example.projekt_teszt_1;

import android.os.Parcel;
import android.os.Parcelable;

public class Esemeny implements Parcelable {

    private int id;
    private String name;
    private String location;
    private String start_date;
    private String end_date;
    private static int largest_id=0;


    Esemeny(int id,String _name, String _loc, String _start, String _end,String _desc){
        this.id = id;
        name = _name;
        location = _loc;
        start_date = _start;
        end_date = _end;
        desc=_desc;
        largest_id=id;
    }
    Esemeny(String _name, String _loc, String _start, String _end,String _desc){
        this.id = largest_id==0?1:largest_id*2;
        name = _name;
        location = _loc;
        start_date = _start;
        end_date = _end;
        desc=_desc;
        largest_id=id;
    }
    @Override
    public String toString() {
        return "Esemeny{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", desc='" + desc + '\'' +
                ", closed=" + closed +
                '}';
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    private boolean closed;



    protected Esemeny(Parcel in) {
        id = in.readInt();
        name = in.readString();
        location = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        desc=in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Esemeny> CREATOR = new Creator<Esemeny>() {
        @Override
        public Esemeny createFromParcel(Parcel in) {
            return new Esemeny(in);
        }

        @Override
        public Esemeny[] newArray(int size) {
            return new Esemeny[size];
        }
    };
}
