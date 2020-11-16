package com.example.projekt_teszt_1.ui.people;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.projekt_teszt_1.Hallgato;
import com.example.projekt_teszt_1.R;

import java.util.ArrayList;

public class StudentFragment extends Fragment {

    ArrayList<Hallgato> students;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_student, container, false);
        students=new ArrayList<>();
        students.add(new Hallgato("ASDB3C","ASD ASD","Férfi","MIK",true,8));
        StudentAdapter adapter=new StudentAdapter(getContext(),R.layout.student_list_item,students);
        ListView studentList=root.findViewById(R.id.student_list);
        studentList.setAdapter(adapter);

        return root;
    }
}