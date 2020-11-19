package com.example.projekt_teszt_1.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.projekt_teszt_1.DatabaseHelper;
import com.example.projekt_teszt_1.EditEvent;
import com.example.projekt_teszt_1.Esemeny;
import com.example.projekt_teszt_1.Hallgato;
import com.example.projekt_teszt_1.R;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Esemeny> {
    Context con;
    List<Esemeny> data;
    DatabaseHelper db;
    private static LayoutInflater inflater = null;

    public EventAdapter(Context context, int resource, List<Esemeny> data, DatabaseHelper database) {
        super(context,resource,data);
        // TODO Auto-generated constructor stub
        this.con = context;
        this.data = data;
        db=database;
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
            vi = inflater.inflate(R.layout.event_list_item, null);
        TextView eventName = (TextView) vi.findViewById(R.id.list_item_event_name);
        eventName.setText(data.get(position).getName());
        Log.d("event",String.valueOf(data.get(position).getId()));
        TextView eventLoc = (TextView) vi.findViewById(R.id.list_item_event_loc);
        eventLoc.setText(data.get(position).getLocation());
        TextView eventDate = (TextView) vi.findViewById(R.id.list_item_event_date_st);
        eventDate.setText(data.get(position).getStart_date());
        final Button edit= (Button) vi.findViewById(R.id.list_item_button_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent i = new Intent(con, EditEvent.class);
        Log.d("event",data.get(position).toString());
        i.putExtra("event",data.get(position));
                con.startActivity(i);
            }
        });

        Button done= (Button) vi.findViewById(R.id.list_item_button_ok);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setEnabled(false);
                data.get(position).setClosed(true);

            }
        });

        Button delete= (Button) vi.findViewById(R.id.list_item_button_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteEvent(data.get(position).getId());
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        return vi;
    }
}
