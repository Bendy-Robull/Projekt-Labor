package com.example.projekt_teszt_1.ui.stat;

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

import java.util.List;

public class StatFragment extends Fragment {

    List<Esemeny> events;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_stat, container, false);
        DatabaseHelper databaseHelper=new DatabaseHelper(this.getContext());
        events=databaseHelper.getEsemeny(); //létrehoz egy listát, és a databasehelper feltölti
        //Toast.makeText(this.getContext(),String.valueOf(all.size()) , Toast.LENGTH_SHORT).show();
        EventAdapter esemenyArrayAdapter=new EventAdapter(getContext(), R.layout.stat_event_list_item, events);
        ListView list =(ListView) root.findViewById(R.id.stat_list);
        list.setAdapter(esemenyArrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*StackedBarChart mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);
                StackedBarModel s1 = new StackedBarModel("12.4");
                s1.addBar(new BarModel(2.3f, 0xFF63CBB0));
                s1.addBar(new BarModel(2.3f, 0xFF56B7F1));
                s1.addBar(new BarModel(2.3f, 0xFFCDA67F));
                mStackedBarChart.addBar(s1);
                mStackedBarChart.startAnimation();*/

                /*PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
                mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
                mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
                mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
                mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));
                mPieChart.startAnimation();*/
            }
        });
        return root;
    }
}