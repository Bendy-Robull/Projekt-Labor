package com.example.projekt_teszt_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_LOCATION = "COLUMN_LOCATION";
    public static final String COLUMN_START = "COLUMN_START";
    public static final String COLUMN_END = "COLUMN_END";
    public static final String COLUMN_INFORMATION = "COLUMN_INFORMATION";

    public DatabaseHelper(@Nullable Context context)
    {
        super(context, "database.db", null, 1);
    }

    //this is called the first time a database is accessed. there should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTableStatement= "CREATE TABLE " + EVENT_TABLE + " ( COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_LOCATION + " TEXT, " + COLUMN_START + " TEXT, " + COLUMN_END + " TEXT, " + COLUMN_INFORMATION + " TEXT)";
        db.execSQL(createTableStatement);
    }


    //this is called if the database version number changes. it prevent previous users apps from breaking when you change the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
    public boolean addData(Esemeny esemeny) //ide kell egy osztály
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, esemeny.getName());
        cv.put(COLUMN_LOCATION, esemeny.getLocation());
        cv.put(COLUMN_START, esemeny.getStart_date());
        cv.put(COLUMN_END, esemeny.getEnd_date());
        cv.put(COLUMN_INFORMATION, esemeny.getDesc());

        long insert = db.insert(EVENT_TABLE, null, cv);
        Log.v("HIBA", "cv: " + cv.toString());
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    public List<Integer> getEventStudents(int event_id){
        List<Hallgato> returnList=new ArrayList<>();
        String queryString="SELECT * FROM STUDENT_TABLE";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                String nept=cursor.getString(0);
                String nev=cursor.getString(1);
                Boolean nem=Boolean.parseBoolean(cursor.getString(2));
                String kar=cursor.getString(3);
                Boolean megbizhato=Boolean.parseBoolean(cursor.getString(4));
                int esemenyszamlalo=cursor.getInt(5);
                Hallgato ujhallgato=new Hallgato(nept, nev, nem, kar, megbizhato, esemenyszamlalo);
                if(ujhallgato.getEvents().contains(String.valueOf(event_id))) {
                    returnList.add(ujhallgato);
                }
            }while(cursor.moveToNext());
        }
        else
        {
            //failure
        }
        cursor.close();
        db.close();
        int cmik=0, cmk=0, cgtk=0,cmftk=0 , cn=0, cf=0;
        for(int i=0; i<returnList.size(); i++){
            switch (returnList.get(i).getFaculty()){
                case "MIK":
                    cmik++;
                    break;
                case "MK":
                    cmk++;
                    break;
                case "GTK":
                    cgtk++;
                    break;
                case "MFTK":
                    cmftk++;
                    break;
            }
            if(returnList.get(i).isFemale()){
                cn++;
            }
            else {
                cf++;
            }
        }
        List<Integer> counted=new ArrayList<Integer>();
        counted.add(cmk);  //mernokos hallgatok szama
        counted.add(cmik);  //mikes hallgatok
        counted.add(cgtk);  //gtks hallgatok
        counted.add(cmftk);
        counted.add(cf);    //fiuk szama
        counted.add(cn);    //lanyok szama
        return counted;
    }
    public void updateHallgato(Hallgato hallgato,int eventid){
        if(!hallgato.getEvents().contains(String.valueOf(eventid))){
            SQLiteDatabase db = this.getWritableDatabase();
            int x = hallgato.getEventnum();
            x += Math.pow(2, eventid);
            Log.d("event","updateHallgato: "+String.valueOf(x));
            db.execSQL("UPDATE STUDENT_TABLE SET COLUMN_EVENTCOUNTER='"+x+"' WHERE COLUMN_NEPTUN='"+hallgato.getNeptun()+"' ");
            Log.v("SQLN", "UPDATE SUCCES");
            db.close();
        }
        else{
            Log.v("SQLN", "UPDATE FAILED: IT CONTAINS THAT VALUE");
        }
    }
    public void deleteEvent(int event_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM EVENT_TABLE WHERE TABLE_ID='"+event_id+"'");
    }
    public Hallgato getHallgatoByNeptun(String nept){
        Hallgato guy = new Hallgato();
        String queryString="SELECT * FROM STUDENT_TABLE WHERE COLUMN_NEPTUN LIKE '"+nept+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            String neptun=cursor.getString(0);
            String name=cursor.getString(1);
            boolean sex=cursor.getInt(2)>0;
            String fac=cursor.getString(3);
            boolean trust=cursor.getInt(4)>0;
            int eventcount=cursor.getInt(5);
            guy=new Hallgato(neptun, name, sex, fac, trust, eventcount);
        }
        else
        {
            //ha nem tud mit kiolvasni
        }
        cursor.close();
        db.close();
        return guy;
    }

    public List<Hallgato> getHallgato(){ //egy listába összeszedi az összes hallgatót és return-olja
        List<Hallgato> returnList=new ArrayList<>();
        String queryString="SELECT * FROM STUDENT_TABLE";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                String nept=cursor.getString(0);
                String nev=cursor.getString(1);
                Boolean nem=Boolean.parseBoolean(cursor.getString(2));
                String kar=cursor.getString(3);
                Boolean megbizhato=Boolean.parseBoolean(cursor.getString(4));
                int esemenyszamlalo=cursor.getInt(5);

                Hallgato ujhallgato=new Hallgato(nept, nev, nem, kar, megbizhato, esemenyszamlalo);
                returnList.add(ujhallgato);
            }while(cursor.moveToNext());
        }
        else
        {
            //failure
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public int getCountStudents(String kar){ //meghivaskor egy ilyet irj bele, hogy "MIK" vagy "GTK"
        String queryString="SELECT * FROM STUDENT_TABLE WHERE COLUMN_FACULTY LIKE '"+kar+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString, null);
        int letszam=cursor.getCount();
        cursor.close();
        db.close();
        return letszam;
    }

    public int getCountBySex(int nem){ // ha 0 akkor ferfi, ha 1 akkor no
        String queryString="SELECT * FROM STUDENT_TABLE WHERE COLUMN_SEX LIKE '"+nem+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString, null);
        int sex=cursor.getCount();
        cursor.close();
        db.close();
        return sex;
    }

    public List<Esemeny> getEsemeny(){
        List<Esemeny> returnList=new ArrayList<>();

        //getting data
        String queryString="SELECT * FROM EVENT_TABLE";

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                String megnevezes=cursor.getString(1);
                String helyszin=cursor.getString(2);
                String kezdete=cursor.getString(3);
                String vege=cursor.getString(4);
                String megjegyzes=cursor.getString(5);
                Esemeny ujesemeny=new Esemeny(id, megnevezes, helyszin, kezdete, vege, megjegyzes);
                returnList.add(ujesemeny);
            }while(cursor.moveToNext());
        }
        else
        {
            //ha nem tud mit kiolvasni
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
