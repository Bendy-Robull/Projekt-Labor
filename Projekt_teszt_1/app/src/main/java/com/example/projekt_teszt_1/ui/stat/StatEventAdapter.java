package com.example.projekt_teszt_1.ui.stat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projekt_teszt_1.EditEvent;
import com.example.projekt_teszt_1.Esemeny;
import com.example.projekt_teszt_1.R;

import java.util.List;

public class StatEventAdapter extends ArrayAdapter<Esemeny> {
    List<Esemeny> data;
    Context con;
    private static LayoutInflater inflater = null;

    public StatEventAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Esemeny> objects) {
        super(context, resource, textViewResourceId, objects);
        data=objects;
        con=context;
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
            vi = inflater.inflate(R.layout.stat_event_list_item, null);
        TextView eventName = (TextView) vi.findViewById(R.id.stat_event_name);
        eventName.setText(data.get(position).getName());
        TextView eventLoc = (TextView) vi.findViewById(R.id.stat_event_loc);
        eventLoc.setText(data.get(position).getLocation());
        TextView eventDate = (TextView) vi.findViewById(R.id.stat_event_start);
        eventDate.setText(data.get(position).getStart_date());
        return vi;
    }
}
