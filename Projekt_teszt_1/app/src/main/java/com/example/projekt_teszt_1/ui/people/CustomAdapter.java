package com.example.projekt_teszt_1.ui.people;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projekt_teszt_1.Esemeny;
import com.example.projekt_teszt_1.R;

import java.io.File;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Esemeny> {

    Context con;
    ArrayList<Esemeny> data;
    String nep;
    private static LayoutInflater inflater = null;

    public CustomAdapter(@NonNull Context context, int resource, ArrayList<Esemeny> objects,String neptun) {
        super(context, resource, objects);
        this.con = context;
        this.data = objects;
        nep=neptun;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Esemeny getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.list_item_event_student, null);
        TextView eventName = (TextView) vi.findViewById(R.id.event_text_name);
        eventName.setText(data.get(position).getName());
        TextView eventLoc = (TextView) vi.findViewById(R.id.event_text_loc);
        eventLoc.setText(data.get(position).getLocation());
        TextView eventDate = (TextView) vi.findViewById(R.id.event_text_date_start);
        eventDate.setText(data.get(position).getStart_date());
        ImageView signo1=(ImageView)vi.findViewById(R.id.signo1);
Log.d("event","/storage/emulated/0/Pictures/Alairas/"+nep+"_"+String.valueOf(data.get(position).getId())+"_Signo1.jpg");
        File file = new  File("/storage/emulated/0/Pictures/Alairas/"+nep+"_"+String.valueOf(data.get(position).getId())+"_Signo1.jpg");

        if(file.exists()){

            Log.d("event","Hello");
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            signo1.setImageBitmap(bitmap);

        }

        return vi;
    }
}
