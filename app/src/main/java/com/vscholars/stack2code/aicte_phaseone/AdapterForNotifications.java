package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class AdapterForNotifications extends BaseExpandableListAdapter{

    Context context;
    int categories;
    int parameters;
    List<String> categoriesNames;
    String[][][] data;
    HashMap<String,Integer> enteries;

    AdapterForNotifications(Context context,int categories,int parameters, List<String> categoriesNames,HashMap<String,Integer>enteries,String[][][] data){

        //categories is the number of notifications, parameters value would be 3 only because the content of notification is title body and message, categories names is any group header for notification, data is stored in 3-d data, enteries gives number of children for group here it would be 1
        //supply the required parameters and the adapter is good to go
        this.context=context;
        this.categories=categories;
        this.parameters=parameters;
        this.categoriesNames=categoriesNames;
        this.data=data;
        this.enteries=enteries;
    }

    @Override
    public int getGroupCount() {
        return this.categories;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return enteries.get(categoriesNames.get(groupPosition));
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoriesNames.get(groupPosition);
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

            convertView = infalInflater.inflate(R.layout.label_announcements, null);

            final TextView title,body,message;

            View[] layoutViews={convertView.findViewById(R.id.x_label_announcements_title),
                    convertView.findViewById(R.id.x_label_announcements_body),
                    convertView.findViewById(R.id.x_label_announcements_message)};

            title=(TextView)layoutViews[0];
            body=(TextView)layoutViews[1];
            message=(TextView)layoutViews[2];

            title.setText(values[0]);
            body.setText(values[1]);
            message.setText(values[2]);
            return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}