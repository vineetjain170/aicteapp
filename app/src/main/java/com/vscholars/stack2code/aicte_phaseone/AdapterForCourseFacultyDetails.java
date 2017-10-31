package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vineet_jain on 19/7/17.
 */
public class AdapterForCourseFacultyDetails extends BaseExpandableListAdapter {

    Context context;
    String message;
    int size;
    int parameters;
    ArrayList<String>categoriesList;
    HashMap<String,Integer>categories;
    String[][][]data;

    public AdapterForCourseFacultyDetails(Context context, String message, int size, int parameters, ArrayList<String> categoriesList, HashMap<String, Integer> categories, String[][][] data) {

        this.context=context;
        this.message=message;
        this.size=size;
        this.parameters=parameters;
        this.categoriesList=categoriesList;
        this.categories=categories;
        this.data=data;
    }


    @Override
    public int getGroupCount() {
        return size;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categories.get(categoriesList.get(groupPosition));
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoriesList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String[] parameterValue=new String[this.parameters];
        for (int i=0;i<this.parameters;++i){
            parameterValue[i]=data[groupPosition][childPosition][i];
        }

        return parameterValue;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupName=(String) getGroup(groupPosition);
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.parent_filter_options,null,false);
        TextView parentTextView=(TextView)convertView.findViewById(R.id.x_parent_filter_options_text_view);
        parentTextView.setText(groupName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final String[] values=(String[]) getChild(groupPosition,childPosition);
        if (message.equals("course_details")){
            convertView=infalInflater.inflate(R.layout.label_approved_institutes_course_details,null);

            TextView programme,university,level,course,shift,fullorparttime,intake,enrolment;

            View[] layoutViews= new View[]{convertView.findViewById(R.id.x_label_approved_institutes_course_details_programme),
                    convertView.findViewById(R.id.x_label_approved_institutes_course_details_university),
                    convertView.findViewById(R.id.x_label_approved_institutes_course_details_course_level),
                    convertView.findViewById(R.id.x_label_approved_institutes_course_details_course),
                    convertView.findViewById(R.id.x_label_approved_institutes_course_details_shift),
                    convertView.findViewById(R.id.x_label_approved_institutes_course_details_full_part_time),
                    convertView.findViewById(R.id.x_label_approved_institutes_course_details_intake),
                    convertView.findViewById(R.id.x_label_approved_institutes_course_details_enrolment)};

            programme=(TextView)layoutViews[0];
            university=(TextView)layoutViews[1];
            level=(TextView)layoutViews[2];
            course=(TextView)layoutViews[3];
            shift=(TextView)layoutViews[4];
            fullorparttime=(TextView)layoutViews[5];
            intake=(TextView)layoutViews[6];
            enrolment=(TextView)layoutViews[7];

            programme.setText(values[1]);
            university.setText(values[2]);
            level.setText(values[3]);
            course.setText(values[0]);
            shift.setText(values[4]);
            fullorparttime.setText(values[5]);
            intake.setText(values[6]);
            enrolment.setText(values[7]);

            return convertView;

        }else if(message.equals("faculty_details")){

            convertView=infalInflater.inflate(R.layout.label_approved_institutes_faculty_details,null);

            TextView fid,gender,designation,joiningDate,specialisationArea,appointmentType,name;

            View[] layoutViews= new View[]{convertView.findViewById(R.id.x_label_approved_institutes_faculty_details_fid),
                    convertView.findViewById(R.id.x_label_approved_institutes_faculty_details_gender),
                    convertView.findViewById(R.id.x_label_approved_institutes_faculty_details_designation),
                    convertView.findViewById(R.id.x_label_approved_institutes_faculty_details_joining_date),
                    convertView.findViewById(R.id.x_label_approved_institutes_faculty_details_specialisation_area),
                    convertView.findViewById(R.id.x_label_approved_institutes_faculty_details_appointment_type),
                    convertView.findViewById(R.id.x_label_approved_institutes_faculty_details_name)};

            fid=(TextView)layoutViews[0];
            gender=(TextView)layoutViews[1];
            designation=(TextView)layoutViews[2];
            joiningDate=(TextView)layoutViews[3];
            specialisationArea=(TextView)layoutViews[4];
            appointmentType=(TextView)layoutViews[5];
            name=(TextView)layoutViews[6];

            fid.setText(values[0]);
            gender.setText(values[2]);
            designation.setText(values[3]);
            joiningDate.setText(values[4]);
            specialisationArea.setText(values[5]);
            appointmentType.setText(values[6]);
            name.setText(values[1]);

            return convertView;

        }
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
