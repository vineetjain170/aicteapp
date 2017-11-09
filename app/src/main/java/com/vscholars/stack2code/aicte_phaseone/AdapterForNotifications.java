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
    String[] titlesArray;
    String[] bodyArray;
    String[] messageArray;

    AdapterForNotifications(Context context,String[] titlesArray,String[] bodyArray,String[] messageArray){
        this.context=context;
        this.titlesArray=titlesArray;
        this.bodyArray=bodyArray;
        this.messageArray=messageArray;
    }

    @Override
    public int getGroupCount() {
        return titlesArray.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return titlesArray[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        String[] parameterValue=new String[2];
        parameterValue[0]=bodyArray[groupPosition];
        parameterValue[1]=messageArray[groupPosition];
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
        View[] layoutViews={convertView.findViewById(R.id.x_label_announcements_body),
                convertView.findViewById(R.id.x_label_announcements_message)};
        body=(TextView)layoutViews[0];
        message=(TextView)layoutViews[1];
        body.setText(values[0]);
        message.setText(values[1]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}