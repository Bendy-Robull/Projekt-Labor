package com.example.projekt_teszt_1;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateEvent extends AppCompatActivity {


    MapView map;
    GoogleMap gmap;
    EditText name;
    EditText location;
    EditText start;
    EditText end;
    EditText desc;
    LatLng latLng;
    MarkerOptions markerOptions = new MarkerOptions();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Context ctx=this;
        name =findViewById(R.id.text_input_event_name);
        location=findViewById(R.id.text_input_event_location);
        start =findViewById(R.id.text_input_event_start);
        end =findViewById(R.id.text_input_event_end);
        desc =findViewById(R.id.text_input_event_desc);

        map=findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);

        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap=googleMap;
            }
        });

        Button refreshMap =findViewById(R.id.map_refresh_button);
        refreshMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("loc",location.getText().toString());
                        Geocoder coder= new Geocoder(ctx, Locale.getDefault());
                        List<Address> addresses;
                        try {
                            addresses = coder.getFromLocationName(location.getText().toString(), 5);
                            if (addresses != null) {
                                Address loc = addresses.get(0);
                                double lat = loc.getLatitude();
                                double lng = loc.getLongitude();
                                Log.i("Lat",""+lat);
                                Log.i("Lng",""+lng);
                                LatLng latLng = new LatLng(lat,lng);
                                markerOptions.title(location.getText().toString());
                                markerOptions.position(latLng);
                                gmap.addMarker(markerOptions);
                                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
        });

        Button eventCreateButton=findViewById(R.id.event_next_button);
        eventCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("event_key", new Esemeny(name.getText().toString(),location.getText().toString(),start.getText().toString(),end.getText().toString(),desc.getText().toString()));
                setResult(200, resultIntent);
                finish();

            }
        });






    }
    @Override
    public void onResume() {
        map.onResume();
        super.onResume();
    }
}