package com.example.projekt_teszt_1.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.example.projekt_teszt_1.CreateEvent;
import com.example.projekt_teszt_1.DatabaseHelper;
import com.example.projekt_teszt_1.Esemeny;
import com.example.projekt_teszt_1.Hallgato;
import com.example.projekt_teszt_1.R;
import com.example.projekt_teszt_1.EditEvent;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    ArrayList<Esemeny> events;
    ArrayList<Hallgato> studs;
    List<Esemeny> all;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        DatabaseHelper databaseHelper=new DatabaseHelper(this.getContext());
        all=databaseHelper.getEsemeny(); //létrehoz egy listát, és a databasehelper feltölti
        //Toast.makeText(this.getContext(),String.valueOf(all.size()) , Toast.LENGTH_SHORT).show();
        EventAdapter esemenyArrayAdapter=new EventAdapter(getContext(), R.layout.event_list_item, all,databaseHelper);
        ListView list =(ListView) root.findViewById(R.id.event_list);
        list.setAdapter(esemenyArrayAdapter);
        FloatingActionButton fab = (FloatingActionButton)root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CreateEvent.class);
                startActivityForResult(i,200);
            }
        });
        return root;
    }
    @Override
    public void onPause() {
        //Intent change
        super.onPause();
    }

}
