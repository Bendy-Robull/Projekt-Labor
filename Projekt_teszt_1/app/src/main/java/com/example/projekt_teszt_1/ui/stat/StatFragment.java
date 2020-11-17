package com.example.projekt_teszt_1.ui.stat;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.projekt_teszt_1.DatabaseHelper;
import com.example.projekt_teszt_1.Esemeny;
import com.example.projekt_teszt_1.R;
import com.example.projekt_teszt_1.ui.home.EventAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

public class StatFragment extends Fragment {

    List<Esemeny> events;
    DatabaseHelper databaseHelper;
    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_stat, container, false);
        databaseHelper=new DatabaseHelper(this.getContext());
        events=databaseHelper.getEsemeny(); //létrehoz egy listát, és a databasehelper feltölti
        //Toast.makeText(this.getContext(),String.valueOf(all.size()) , Toast.LENGTH_SHORT).show();
        EventAdapter esemenyArrayAdapter=new EventAdapter(getContext(), R.layout.stat_event_list_item, events);
        ListView list =(ListView) root.findViewById(R.id.stat_list);
        list.setAdapter(esemenyArrayAdapter);
        PieChart Chart_faculty = (PieChart) root.findViewById(R.id.stat_pie_chart_faculty);
        Chart_faculty.addPieSlice(new PieModel("MIK", databaseHelper.getCountStudents("MIK"), Color.parseColor("#FE6DA8")));
        Chart_faculty.addPieSlice(new PieModel("MK", databaseHelper.getCountStudents("MK"), Color.parseColor("#56B7F1")));
        Chart_faculty.addPieSlice(new PieModel("GTK", databaseHelper.getCountStudents("GTK"), Color.parseColor("#CDA67F")));
        Chart_faculty.addPieSlice(new PieModel("MFTK", databaseHelper.getCountStudents("MFTK"), Color.parseColor("#FED70E")));
        Chart_faculty.startAnimation();

        PieChart Chart_sex = (PieChart) root.findViewById(R.id.stat_pie_chart_sex);
        Chart_sex.addPieSlice(new PieModel("Férfi", databaseHelper.getCountBySex(false), Color.parseColor("#FE6DA8")));
        Chart_sex.addPieSlice(new PieModel("Nő", databaseHelper.getCountBySex(true), Color.parseColor("#56B7F1")));
        Chart_sex.startAnimation();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PieChart Chart_faculty = (PieChart) root.findViewById(R.id.stat_pie_chart_faculty);
                Chart_faculty.addPieSlice(new PieModel("MIK", databaseHelper.getCountStudents("MIK"), Color.parseColor("#FE6DA8")));
                Chart_faculty.addPieSlice(new PieModel("MK", databaseHelper.getCountStudents("MK"), Color.parseColor("#56B7F1")));
                Chart_faculty.addPieSlice(new PieModel("GTK", databaseHelper.getCountStudents("GTK"), Color.parseColor("#CDA67F")));
                Chart_faculty.addPieSlice(new PieModel("MFTK", databaseHelper.getCountStudents("MFTK"), Color.parseColor("#FED70E")));
                Chart_faculty.startAnimation();

                PieChart Chart_sex = (PieChart) root.findViewById(R.id.stat_pie_chart_sex);
                Chart_sex.addPieSlice(new PieModel("Férfi", databaseHelper.getCountBySex(false), Color.parseColor("#FE6DA8")));
                Chart_sex.addPieSlice(new PieModel("Nő", databaseHelper.getCountBySex(true), Color.parseColor("#56B7F1")));
                Chart_sex.startAnimation();

                FloatingActionButton fab= root.findViewById(R.id.fab_stat);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        return root;
    }
}