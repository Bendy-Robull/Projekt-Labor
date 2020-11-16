package com.example.projekt_teszt_1.ui.people;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt_teszt_1.EditEvent;
import com.example.projekt_teszt_1.Esemeny;
import com.example.projekt_teszt_1.Hallgato;
import com.example.projekt_teszt_1.R;

import java.io.File;
import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {


    String neptun;
    Hallgato student;
    ArrayList<Esemeny> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        neptun=getIntent().getStringExtra("neptun");
        // Adat bekérés SQLite-ból
        //events=;
        //student=;

        CustomAdapter adapter=new CustomAdapter(this,R.layout.list_item_event_student,events);
        ListView view=findViewById(R.id.event_student_list);
        view.setAdapter(adapter);
    }
}

