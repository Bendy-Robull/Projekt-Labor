package com.example.projekt_teszt_1.ui.people;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.projekt_teszt_1.DatabaseHelper;
import com.example.projekt_teszt_1.EditEvent;
import com.example.projekt_teszt_1.Esemeny;
import com.example.projekt_teszt_1.Hallgato;
import com.example.projekt_teszt_1.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentActivity extends AppCompatActivity {


    String neptun;
    Hallgato student;
    ArrayList<Esemeny> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        student=getIntent().getParcelableExtra("student");
        // Adat bekérés SQLite-ból
        //events=;
        //student=;
        DatabaseHelper db=new DatabaseHelper(this);
        List<Esemeny> allevent=db.getEsemeny();
        String evs=student.getEvents();
        Log.d("event",evs);
        events=new ArrayList<Esemeny>();
        for (Esemeny event:allevent
             ) {
            Log.d("event",String.valueOf(event.getId()));
            Log.d("event",String.valueOf(evs.contains(String.valueOf(event.getId()))));
            if (evs.contains(String.valueOf(event.getId()))){

                events.add(event);
            }
        }
        Log.d("event",String.valueOf(events.size()));
        //Log.d("event",events.get(events.size()-1).toString());
        EditText neptun =findViewById(R.id.text_input_stud_neptun);
        EditText name =findViewById(R.id.text_input_stud_name);
        EditText sex =findViewById(R.id.text_input_stud_sex);
        EditText fac =findViewById(R.id.text_input_stud_faculty);
        SwitchCompat trust=findViewById(R.id.stud_trust_switch);
        neptun.setText(student.getNeptun());
        name.setText(student.getName());
        sex.setText(student.getSex());
        fac.setText(student.getFaculty());
        trust.setChecked(student.isTrusty());
        CustomAdapter adapter=new CustomAdapter(this,R.layout.list_item_event_student,events,student.getNeptun());
        ListView view=findViewById(R.id.event_student_list);
        view.setAdapter(adapter);
    }
}

