package com.example.projekt_teszt_1.ui.people;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projekt_teszt_1.EditEvent;
import com.example.projekt_teszt_1.Hallgato;
import com.example.projekt_teszt_1.R;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Hallgato> {
    Context con;
    ArrayList<Hallgato> data;
    private static LayoutInflater inflater = null;

    public StudentAdapter(Context context,int res, ArrayList<Hallgato> data)
    {
        super(context,res,data);
        this.con = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Hallgato getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.student_list_item, null);
        TextView studentNeptun = (TextView) vi.findViewById(R.id.list_item_student_neptun);
        studentNeptun.setText(data.get(position).getNeptun());
        TextView studentName = (TextView) vi.findViewById(R.id.list_item_student_name);
        studentName.setText(data.get(position).getName());
        TextView studentSex = (TextView) vi.findViewById(R.id.list_item_student_sex);
        studentSex.setText(data.get(position).getSex());
        TextView studentFac = (TextView) vi.findViewById(R.id.list_item_student_faculty);
        studentFac.setText(data.get(position).getFaculty());
        return vi;
    }
}
