package com.example.projekt_teszt_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.navigation.NavigationView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class EditEvent  extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Hallgato student;
    Esemeny thisEvent;
    DatabaseHelper db;
    SignaturePad StudentSigno1;
    //SignaturePad StudentSigno2;
    EditText neptun;
    EditText name;
    EditText sex;
    EditText faculty;

    Button Alldone;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Toolbar toolbar = findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        thisEvent =getIntent().getParcelableExtra("event");
        db=new DatabaseHelper(EditEvent.this);
        verifyStoragePermissions(this);
        TextView desc=findViewById(R.id.multiline_desc);
        desc.setText(thisEvent.getDesc());
        name=findViewById(R.id.text_input_student_name);
        sex=findViewById(R.id.text_input_student_sex);
        faculty=findViewById(R.id.text_input_student_faculty);

        neptun= findViewById(R.id.text_input_student_neptun);
        neptun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(neptun.getText().toString().length()==6)
                {
                    student=db.getHallgatoByNeptun(neptun.getText().toString());
                    if(student!=null){
                        name.setText(student.getName());
                        sex.setText(student.getSex());
                        faculty.setText(student.getFaculty());

                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        StudentSigno1 = (SignaturePad) findViewById(R.id.student_event_signo_1);
        //StudentSigno2 = (SignaturePad) findViewById(R.id.student_event_signo_2);
        Alldone=findViewById(R.id.event_close_student_button);
        nextButton=findViewById(R.id.event_next_student_button);
        StudentSigno1.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //StudentSigno2.setEnabled(false);
            }

            @Override
            public void onSigned() {

                //StudentSigno2.setEnabled(true);
            }

            @Override
            public void onClear() {

                //StudentSigno2.setEnabled(false);
            }
        });
        /*StudentSigno2.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                StudentSigno1.setEnabled(false);
            }

            @Override
            public void onSigned() {

                Alldone.setEnabled(true);
                nextButton.setEnabled(true);
            }

            @Override
            public void onClear() {

                Alldone.setEnabled(false);
                nextButton.setEnabled(false);
            }
        });*/
        Button sign1Clear =findViewById(R.id.signo1_clear);
        //Button sign2Clear =findViewById(R.id.signo2_clear);
        sign1Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentSigno1.clear();
            }
        });
        /*sign2Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentSigno2.clear();
            }
        });*/


        Alldone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton.callOnClick();
                Intent result = new Intent(view.getContext(),MainActivity.class);
                startActivity(result);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(neptun.getText().toString().length()==6){
                Bitmap signo1Bitmap = StudentSigno1.getSignatureBitmap();
                //Bitmap signo2Bitmap = StudentSigno2.getSignatureBitmap();
                String eventnum=String.valueOf(thisEvent.getId());
                String signo1Name=neptun.getText().toString()+"_"+eventnum+"_Signo1.jpg";
                Log.d("event",signo1Name);
                //String signo2Name=neptun.getText().toString()+"_"+eventnum+"_Signo2.jpg";
                if (addJpgSignatureToGallery(signo1Bitmap,signo1Name)){
                    //addJpgSignatureToGallery(signo2Bitmap,neptun.getText().toString()+"_"+eventnum+"_Signo2.jpg")) {
                    db.updateHallgato(student,thisEvent.getId());
                    Toast.makeText(getBaseContext(), "Aláírások elmentve.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Nem sikerült elmenteni az aláírást!", Toast.LENGTH_SHORT).show();
                }
                    name.setText("");
                    neptun.setText("");
                    sex.setText("");
                    faculty.setText("");
                    StudentSigno1.clear();
            }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Nem lehet külső tárhelyre menteni képet.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature,String filename) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("Alairas"), filename);
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
