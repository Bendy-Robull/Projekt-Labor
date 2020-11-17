package com.example.projekt_teszt_1.ui.people;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.projekt_teszt_1.DatabaseHelper;
import com.example.projekt_teszt_1.Hallgato;
import com.example.projekt_teszt_1.R;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment {

    List<Hallgato> students;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_student, container, false);
        DatabaseHelper db= new DatabaseHelper(this.getContext());
        students=db.getHallgato();
        StudentAdapter adapter=new StudentAdapter(getContext(),R.layout.student_list_item,students);
        ListView studentList=root.findViewById(R.id.student_list);
        studentList.setAdapter(adapter);
        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i= new Intent(getActivity(),StudentActivity.class);
                Hallgato h=(Hallgato)parent.getItemAtPosition(position);
                i.putExtra("student",h);
                startActivity(i);
            }
        });
        return root;
    }
}