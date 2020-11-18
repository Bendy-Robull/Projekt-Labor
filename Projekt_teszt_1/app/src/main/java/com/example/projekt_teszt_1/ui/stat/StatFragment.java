package com.example.projekt_teszt_1.ui.stat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projekt_teszt_1.DatabaseHelper;
import com.example.projekt_teszt_1.Esemeny;
import com.example.projekt_teszt_1.Hallgato;
import com.example.projekt_teszt_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatFragment extends Fragment {

    List<Esemeny> events;
    DatabaseHelper databaseHelper;
    View root;
    Esemeny e;
    PieChart Chart_faculty;
    PieChart Chart_sex;
    TextView MK;
    TextView MIK;
    TextView MFTK;
    TextView GTK;
    TextView Male;
    TextView Female;
    static String TAG = "ExelLog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_stat, container, false);
        databaseHelper=new DatabaseHelper(this.getContext());
        events=databaseHelper.getEsemeny(); //létrehoz egy listát, és a databasehelper feltölti
        Log.d("asd","asd");
        StatEventAdapter esemenyArrayAdapter=new StatEventAdapter(getContext(), R.layout.stat_event_list_item, events);
        ListView list =(ListView) root.findViewById(R.id.stat_list);
        list.setAdapter(esemenyArrayAdapter);
        MK=root.findViewById(R.id.text_MK);
        MIK=root.findViewById(R.id.text_MIK);
        MFTK=root.findViewById(R.id.text_MFTK);
        GTK=root.findViewById(R.id.text_GTK);
        Male=root.findViewById(R.id.text_Male);
        Female=root.findViewById(R.id.text_Female);
        Chart_faculty= (PieChart) root.findViewById(R.id.stat_pie_chart_faculty);
        MK.setText("MK: "+databaseHelper.getCountStudents("MK"));
        MIK.setText("MIK: "+databaseHelper.getCountStudents("MIK"));
        MFTK.setText("MFTK: "+databaseHelper.getCountStudents("MFTK"));
        GTK.setText("GTK: "+databaseHelper.getCountStudents("GTK"));
        Chart_faculty.addPieSlice(new PieModel("MIK", databaseHelper.getCountStudents("MIK"), Color.parseColor("#8800FF")));
        Chart_faculty.addPieSlice(new PieModel("MK", databaseHelper.getCountStudents("MK"), Color.parseColor("#FF0000")));
        Chart_faculty.addPieSlice(new PieModel("GTK", databaseHelper.getCountStudents("GTK"), Color.parseColor("#0099FF")));
        Chart_faculty.addPieSlice(new PieModel("MFTK", databaseHelper.getCountStudents("MFTK"), Color.parseColor("#FFAA00")));
        Chart_faculty.startAnimation();

        Chart_sex = (PieChart) root.findViewById(R.id.stat_pie_chart_sex);
        Male.setText("Férfi: "+databaseHelper.getCountBySex(0));
        Female.setText("Nő: "+databaseHelper.getCountBySex(1));
        Chart_sex.addPieSlice(new PieModel("Férfi", databaseHelper.getCountBySex(0), Color.parseColor("#00A6FF")));
        Chart_sex.addPieSlice(new PieModel("Nő", databaseHelper.getCountBySex(1), Color.parseColor("#FF00DD")));
        Chart_sex.startAnimation();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                e=(Esemeny)parent.getItemAtPosition(position);
                Log.d("event",e.toString());
                final List <Integer> nums=databaseHelper.getEventStudents(e.getId());

                Chart_faculty.clearChart();
                MK.setText("MK: "+nums.get(0));
                MIK.setText("MIK: "+nums.get(1));
                GTK.setText("GTK: "+nums.get(2));
                MFTK.setText("MFTK: "+nums.get(3));
                Chart_faculty.addPieSlice(new PieModel("MIK", nums.get(1), Color.parseColor("#8800FF")));
                Chart_faculty.addPieSlice(new PieModel("MK", nums.get(0), Color.parseColor("#FF0000")));
                Chart_faculty.addPieSlice(new PieModel("GTK", nums.get(2), Color.parseColor("#0099FF")));
                Chart_faculty.addPieSlice(new PieModel("MFTK", nums.get(3), Color.parseColor("#FFAA00")));
                Chart_faculty.startAnimation();


                Chart_sex.clearChart();
                Male.setText("Férfi: "+nums.get(4));
                Female.setText("Nő: "+nums.get(5));
                Chart_sex.addPieSlice(new PieModel("Férfi", nums.get(4), Color.parseColor("#00A6FF")));
                Chart_sex.addPieSlice(new PieModel("Nő", nums.get(5), Color.parseColor("#FF00DD")));
                Chart_sex.startAnimation();


                FloatingActionButton fab= root.findViewById(R.id.fab_stat);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveExcelFile(v.getContext(),"stat.xls",e,nums);
                    }
                });
            }
        });
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean saveExcelFile(Context context, String fileName,Esemeny ev,List<Integer> nums) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;


        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("Statisztika");

        // Generate column headings
        Row row = sheet1.createRow(0);
        Row row2=sheet1.createRow(1);

        c = row.createCell(0);
        c.setCellValue("Esemény neve");
        c.setCellStyle(cs);

        c=row2.createCell(0);
        c.setCellValue(ev.getName());



        c = row.createCell(1);
        c.setCellValue("Fiúk - Lányok aránya");
        c.setCellStyle(cs);

        c=row2.createCell(1);
        c.setCellValue(nums.get(4)+" : "+nums.get(5));





        c = row.createCell(2);
        c.setCellValue("Karok megoszlása(MK, MIK, GTK, MFTK)");
        c.setCellStyle(cs);

        c=row2.createCell(2);
        c.setCellValue(nums.get(0)+" : "+nums.get(1)+" : "+nums.get(2)+" : "+nums.get(3));


        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));

        // Create a path where we will place our List of objects on external storage
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            Log.w("asd","1");
            wb.write(os);
            Log.w("asd","1");
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}