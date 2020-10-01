package com.example.projekt_teszt_1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateEvent extends AppCompatActivity {

    private TextView hallgatoNevTextView;
    private AutoCompleteTextView AutohallgatoNevTextView;
    private static final String[] NEVEK = new String[] {
            "Bartoss Norbert", "Takács Bendegúz", "Nyári Edina", "Tóth László", "Tóth Lajos"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        hallgatoNevTextView= findViewById(R.id.hallgato_nev);
        AutohallgatoNevTextView = findViewById(R.id.autohallgato_nev);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, NEVEK);
        AutohallgatoNevTextView.setAdapter(adapter);
    }
}