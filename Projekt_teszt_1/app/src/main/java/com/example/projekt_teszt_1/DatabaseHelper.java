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
