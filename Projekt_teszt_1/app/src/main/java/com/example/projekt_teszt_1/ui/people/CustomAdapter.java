package com.example.projekt_teszt_1.ui.people;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private static LayoutInflater inflater = null;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Esemeny> objects) {
        super(context, resource, objects);
        this.con = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
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
        ImageView signo2=(ImageView)vi.findViewById(R.id.signo2);

        File file = new  File("/sdcard/Images/test_image.jpg");

        if(file.exists()){

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            signo1.setImageBitmap(bitmap);

        }
        file=new  File("/sdcard/Images/test_image.jpg");

        if(file.exists()){

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            signo2.setImageBitmap(bitmap);

        };
        return vi;
    }
}
