package com.vscholars.stack2code.aicte_phaseone;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vineet_jain on 8/6/17.
 */

public class AdapterForFilterOptions extends BaseExpandableListAdapter {

    private List<String> J_filterOptionsParent;
    private HashMap<String,List<String>> J_filterOptionsChild;
    private Context context;
    private HashMap<String,Boolean>checkBoxStates;

    AdapterForFilterOptions(Context context,List<String> J_filterOptionsParent,HashMap<String,List<String>> J_filterOptionsChild,HashMap<String,Boolean>checkBoxStates){
        this.context=context;
        this.J_filterOptionsChild=J_filterOptionsChild;
        this.J_filterOptionsParent=J_filterOptionsParent;
        this.checkBoxStates=checkBoxStates;
    }
    @Override
    public int getGroupCount() {
        return this.J_filterOptionsParent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.J_filterOptionsChild.get(this.J_filterOptionsParent.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.J_filterOptionsParent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.J_filterOptionsChild.get(this.J_filterOptionsParent.get(groupPosition)).get(childPosition);
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
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.parent_filter_options, null);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.x_parent_filter_options_text_view);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        RelativeLayout childOptions=new RelativeLayout(context);
        CheckBox checkBox=new CheckBox(context);
        checkBox.setText((String)getChild(groupPosition, childPosition));
        String grpId=groupPosition+"";
        String childId=childPosition+"";
        String id=(grpId+"")+(childId+"");
        checkBox.setId(Integer.parseInt(id));
        checkBox.setFocusable(false);
        checkBox.setClickable(false);
        checkBox.setChecked(checkBoxStates.get((groupPosition+"")+(childPosition+"")));
        childOptions.addView(checkBox);
        return childOptions;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
