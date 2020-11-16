package com.example.projekt_teszt_1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.projekt_teszt_1.ui.home.HomeFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    String dateStart;
    String dateEnd;

    MarkerOptions markerOptions = new MarkerOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Context ctx = this;
        name = findViewById(R.id.text_input_event_name);
        location = findViewById(R.id.text_input_event_location);
        start = findViewById(R.id.text_input_event_start);
        end = findViewById(R.id.text_input_event_end);
        desc = findViewById(R.id.text_input_event_desc);

        start.setInputType(InputType.TYPE_NULL);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(v.getContext(), R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        //Log.d("date",String.valueOf(datePicker.getMonth()));
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getHour(),
                                timePicker.getMinute());

                        ;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
                        //Log.d("date",String.valueOf(calendar.getTime()));
                        dateStart = dateFormat.format(calendar.getTime());
                        //Log.d("date",dateStart);
                        start.setText(dateStart);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });
        start.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final View dialogView = View.inflate(v.getContext(), R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        //Log.d("date",String.valueOf(datePicker.getMonth()));
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getHour(),
                                timePicker.getMinute());

                        ;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
                        //Log.d("date",String.valueOf(calendar.getTime()));
                        dateStart = dateFormat.format(calendar.getTime());
                        //Log.d("date",dateStart);
                        start.setText(dateStart);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });
        end.setInputType(InputType.TYPE_NULL);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(v.getContext(), R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        //Log.d("date",String.valueOf(datePicker.getMonth()));
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getHour(),
                                timePicker.getMinute());

                        ;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
                        //Log.d("date",String.valueOf(calendar.getTime()));
                        dateEnd = dateFormat.format(calendar.getTime());
                        //Log.d("date",dateEnd);
                        end.setText(dateEnd);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });
        end.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final View dialogView = View.inflate(v.getContext(), R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
                        //Log.d("date",String.valueOf(datePicker.getMonth()));
                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getHour(),
                                timePicker.getMinute());

                        ;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
                        //Log.d("date",String.valueOf(calendar.getTime()));
                        dateEnd = dateFormat.format(calendar.getTime());
                        //Log.d("date",dateEnd);
                        end.setText(dateEnd);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });
        map = findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);

        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
            }
        });

        Button refreshMap = findViewById(R.id.map_refresh_button);
        refreshMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("loc", location.getText().toString());
                Geocoder coder = new Geocoder(ctx, Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = coder.getFromLocationName(location.getText().toString(), 5);
                    if (addresses != null) {
                        Address loc = addresses.get(0);
                        double lat = loc.getLatitude();
                        double lng = loc.getLongitude();
                        Log.i("Lat", "" + lat);
                        Log.i("Lng", "" + lng);
                        LatLng latLng = new LatLng(lat, lng);
                        markerOptions.title(location.getText().toString());
                        markerOptions.position(latLng);
                        gmap.addMarker(markerOptions);
                        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button eventCreateButton = findViewById(R.id.event_next_button);
        eventCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Esemeny esemeny;
                try{
                    esemeny =new Esemeny(1,name.getText().toString(), location.getText().toString(), dateStart, dateEnd, desc.getText().toString());
                    //Toast.makeText(CreateEvent.this, esemeny.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    //Toast.makeText(CreateEvent.this, "ERROR", Toast.LENGTH_SHORT).show();
                    esemeny=new Esemeny( 20, "0", "0", "0", "0", "0");
                }

                DatabaseHelper databaseHelper=new DatabaseHelper(CreateEvent.this);

                if (esemeny.getName()!="0"){
                    boolean success=databaseHelper.addData(esemeny);
                    if(success)
                    {
                        Intent i=new Intent(v.getContext(),MainActivity.class);
                        startActivity(i);
                    }
                    //Toast.makeText(CreateEvent.this, "SUCCESS= " + success, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onResume() {
        map.onResume();
        super.onResume();
    }
}