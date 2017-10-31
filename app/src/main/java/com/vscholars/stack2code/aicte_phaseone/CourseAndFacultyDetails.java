package com.vscholars.stack2code.aicte_phaseone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by vineet_jain on 18/7/17.
 */

public class CourseAndFacultyDetails extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_faculty_details);
        if (getIntent().getStringExtra("dataEntries").equals("nonzero")){
            String message=getIntent().getStringExtra("message");
            int parameters=getIntent().getIntExtra("parameters",0);
            int size=getIntent().getIntExtra("size",0);
            ArrayList<String>categoriesList=getIntent().getStringArrayListExtra("categoriesList");
            String[][][] data=(String[][][])getIntent().getSerializableExtra("data");
            HashMap<String,Integer>categories=(HashMap<String, Integer>) getIntent().getSerializableExtra("categories");
            ExpandableListView J_ListMain=(ExpandableListView)findViewById(R.id.x_course_faculty_details_main_list);
            J_ListMain.setAdapter(new AdapterForCourseFacultyDetails(CourseAndFacultyDetails.this,message,size,parameters,categoriesList,categories,data));
            if(message.equals("course_details")) {
                getSupportActionBar().setTitle("Course Details");
            }else if (message.equals("faculty_details")){
                getSupportActionBar().setTitle("Faculty Details");
            }
        }
    }
}
