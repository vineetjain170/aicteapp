package com.vscholars.stack2code.aicte_phaseone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vineet_jain on 8/6/17.
 */

// This class for double navigation drawer throughout the application for filtering options throughout the application without EditText Views

public class DoubleNavigationWithoutEditText extends ActionBarActivity{

    //All Variables required for construction of Navigation Drawer
    private DrawerLayout J_DrawerLayout;
    private ListView J_DrawerListMainNavigationDrawer;
    private ExpandableListView J_DrawerListFilterOptions;
    private ActionBarDrawerToggle J_DrawerToggle;
    private TextView J_ActionBarTitle;
    private ImageView J_mainDrawerOpen,J_filterDrawerOpen;
    private AutoCompleteTextView J_keywords;

    private String[] J_MainNavigationDrawerOptions,J_yearList;
    private List<String> J_ParentFilterOptions;
    private HashMap<String, List<String>> J_ChildFilterOptions;
    private HashMap<String,Boolean>checkBoxStateAll;
    private String J_message;


    protected void onCreateDrawer(Context context, String message,String[] yearList) {

        //This method is main initializer for all the components

        initializer(context,message,yearList);

        // This code sets the drawer toggle as the DrawerListener

        J_DrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                J_DrawerLayout,        /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close                /* "close drawer" description */
        ) {

            // Called when a drawer has settled in a completely closed state.

            public void onDrawerClosed(View view) {
                /*if(J_DrawerListMainNavigationDrawer.getVisibility()==View.INVISIBLE&& J_DrawerListFilterOptions.getVisibility()==View.INVISIBLE){
                    getSupportActionBar().show();
                    super.onDrawerClosed(view);
                }*/
            }

            // Called when a drawer has settled in a completely open state.

            public void onDrawerOpened(View drawerView) {
                /*if(J_DrawerListMainNavigationDrawer.getVisibility()==View.VISIBLE||J_DrawerListFilterOptions.getVisibility()==View.VISIBLE) {
                    getSupportActionBar().hide();
                    super.onDrawerOpened(drawerView);
                }*/
            }
        };

        J_DrawerLayout.setDrawerListener(J_DrawerToggle);
    }

    public void initializer(final Context context, String message, final String[] yearList) {
        /* DrawerLayout object */
        //initialize list view of navigation drawer
        J_DrawerLayout = (DrawerLayout) findViewById(R.id.x_double_navigation_drawer_layout_drawer_layout);
        J_DrawerListMainNavigationDrawer=(ListView)findViewById(R.id.x_double_navigation_drawer_layout_main_drawer);
        J_DrawerListFilterOptions=(ExpandableListView) findViewById(R.id.x_double_navigation_drawer_layout_filter_drawer);
        J_message=message;
        J_yearList=yearList;

        //this method changes the title of action bar according to current activity
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ActionBarTitleChange(message);

        J_mainDrawerOpen=(ImageView)findViewById(R.id.x_custom_action_bar_layout_main_drawer);
        J_filterDrawerOpen=(ImageView)findViewById(R.id.x_custom_action_bar_layout_filter_drawer);

        J_mainDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(J_DrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    J_DrawerLayout.closeDrawer(Gravity.LEFT);
                }else if(J_DrawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    J_DrawerLayout.closeDrawer(Gravity.RIGHT);
                    J_DrawerLayout.openDrawer(Gravity.LEFT);
                }else {
                    J_DrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        J_filterDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                J_DrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        //provide list items for main navigation drawer and set its adapter
        J_MainNavigationDrawerOptions= new String[]{"AICTE Home", "Dashboard", "Approved Institutes", "NRI/PIO-FN-CIWG/TP","Closed Courses","Closed Institutes","Announcements"};
        int[] icons={R.drawable.ic_home_nav,R.drawable.ic_dashboard_nav,R.drawable.ic_institute_nav,R.drawable.ic_nri_nav,R.drawable.ic_closed_courses_nav,R.drawable.ic_closed_institutes_nav,R.drawable.icon_notification};
        J_DrawerListMainNavigationDrawer.setAdapter(new AdapterForMainNavigationList(context,J_MainNavigationDrawerOptions,icons));

        //initialize filter options according to message or current activity and assign an adapter to it
        FilterOptionsInitializer(message,yearList);
        checkBoxStateAll=new HashMap<>();
        for (int i=0;i<J_ParentFilterOptions.size();++i){
            for (int j=0;j<J_ChildFilterOptions.get(J_ParentFilterOptions.get(i)).size();++j){
                String key=((i+"")+(j+""));
                checkBoxStateAll.put(key,false);
            }
        }
        J_DrawerListFilterOptions.setAdapter(new AdapterForFilterOptions(context,J_ParentFilterOptions,J_ChildFilterOptions,checkBoxStateAll));

        LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        J_DrawerListFilterOptions.addFooterView((layoutInflater.inflate(R.layout.filter_option_submit_button,null)));

        //this code adds listeners to expandable list view for filtering options i.e., actions taken when group item is clicked and actions taken when child item is clicked
        J_DrawerListFilterOptions.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                for (int i=0;i<J_ParentFilterOptions.size();++i){
                    if(i!=groupPosition) {
                        J_DrawerListFilterOptions.collapseGroup(i);
                    }
                    if (i==groupPosition){
                        if (J_DrawerListFilterOptions.isGroupExpanded(groupPosition)){
                            J_DrawerListFilterOptions.collapseGroup(groupPosition);
                        }else{
                            J_DrawerListFilterOptions.expandGroup(groupPosition);
                        }
                    }
                }
                return true;
            }
        });
        J_DrawerListFilterOptions.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //on each child click the child is added to selected options hash map
                if (J_ParentFilterOptions.get(groupPosition).equals("Year")||J_ParentFilterOptions.get(groupPosition).equals("Women")||J_ParentFilterOptions.get(groupPosition).equals("Minority")||J_ParentFilterOptions.get(groupPosition).equals("Course Type")){
                    for (int i=0;i<J_ChildFilterOptions.get(J_ParentFilterOptions.get(groupPosition)).size();++i){
                        if (i!=childPosition) {
                            if (checkBoxStateAll.get(((groupPosition + "") + (i + ""))) == true) {
                                checkBoxStateAll.put(((groupPosition + "") + (i + "")), false);
                                ((CheckBox) findViewById(Integer.parseInt(((groupPosition + "") + (i + ""))))).setChecked(false);
                            }
                        }
                    }
                    if (checkBoxStateAll.get((groupPosition+"")+(childPosition+""))==false) {
                        ((CheckBox) findViewById(Integer.parseInt(((groupPosition + "") + (childPosition + ""))))).setChecked(true);
                        checkBoxStateAll.put(((groupPosition + "") + (childPosition + "")), true);
                    }else if (checkBoxStateAll.get((groupPosition+"")+(childPosition+""))==true) {
                        ((CheckBox) findViewById(Integer.parseInt(((groupPosition + "") + (childPosition + ""))))).setChecked(false);
                        checkBoxStateAll.put(((groupPosition + "") + (childPosition + "")), false);
                    }
                }else {
                    String idStateChanged=((groupPosition+"")+(childPosition+""));
                    if(childPosition==0 && J_ChildFilterOptions.get(J_ParentFilterOptions.get(groupPosition)).get(childPosition).equals("--All--")){
                        for(int i=1;i<J_ChildFilterOptions.get(J_ParentFilterOptions.get(groupPosition)).size();++i){
                            checkBoxStateAll.put(((groupPosition+"")+(i+"")),false);
                        }
                        if (checkBoxStateAll.get((groupPosition+"")+(childPosition+""))==false) {
                            checkBoxStateAll.put(idStateChanged, true);
                        }else {
                            checkBoxStateAll.put(idStateChanged, false);
                        }
                    }else {
                        if(J_ChildFilterOptions.get(J_ParentFilterOptions.get(groupPosition)).get(0).equals("--All--")) {
                            checkBoxStateAll.put(((groupPosition + "") + (0 + "")), false);
                        }
                        if(checkBoxStateAll.get(idStateChanged)==false) {
                            checkBoxStateAll.put(idStateChanged, true);
                        }else if (checkBoxStateAll.get(idStateChanged)==true){
                            checkBoxStateAll.put(idStateChanged,false);
                        }
                    }
                    for (int i=0;i<J_ChildFilterOptions.get(J_ParentFilterOptions.get(groupPosition)).size();++i) {
                        if (findViewById(Integer.parseInt((groupPosition+"")+(i+"")))!=null) {
                            ((CheckBox) findViewById(Integer.parseInt((groupPosition + "") + (i + "")))).setChecked(checkBoxStateAll.get((groupPosition + "") + (i + "")));
                        }
                    }
                }
                return true;
            }
        });

        //this code sets listener to main navigation drawer list and sends message to BaseClassAllLists depending on user choice
        J_DrawerListMainNavigationDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                if (position==0){
                    String url = "http://www.aicte-india.org/";
                    i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else if (position==1){
                    String[] values={"[\"2016-2017\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]"};
                    i=new Intent(context,MainActivity.class);
                    i.putExtra("yearList",yearList);
                    i.putExtra("selectedValues",values);
                    startActivity(i);

                } else if (position==2){
                    String[] selectedValues={"[\"2016-2017\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","\"\"","\"\"","0"};
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","approved_institutes");
                    i.putExtra("selectedValues",selectedValues);
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                } else if (position==3){
                    String[] selectedValues={"[\"2016-2017\"]","[\"NRI\"]","\"\"","\"\"","0"};
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","nri/pio-fn-ciwg/tp");
                    i.putExtra("selectedValues",selectedValues);
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                } else if (position==4){
                    String[] values={"[\"2016-2017\"]","\"\"","\"\"","0"};
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","closed_courses");
                    i.putExtra("selectedValues",values);
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                }else if (position==5){
                    String[] values={"[\"2016-2017\"]","\"\"","\"\"","0"};
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","closed_institutes");
                    i.putExtra("selectedValues",values);
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                }else if (position==6){

                    Intent intent=new Intent(DoubleNavigationWithoutEditText.this,Announcements.class);
                    startActivity(intent);

                }
            }
        });

    }

    public void FilterOptionsInitializer(String message,String[] yearList){

        //this code initializes filter options according to the current activity or received message
        J_ParentFilterOptions=new ArrayList<String>();
        J_ChildFilterOptions=new HashMap<String, List<String>>();
        List<String>temp;

        if (message.equals("approved_institutes")){

            //initialize parent options for filter list according to the message
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.filterOptionsParentApprovedInstitutes))J_ParentFilterOptions.add(value);

            temp=new ArrayList<String>();
            for (String year:yearList)temp.add(year);
            J_ChildFilterOptions.put("Year",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.programOptions))temp.add(value);
            J_ChildFilterOptions.put("Select Program",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.levelOptions))temp.add(value);
            J_ChildFilterOptions.put("Select Level",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.institutionType))temp.add(value);
            J_ChildFilterOptions.put("Institution Type",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.stateOptions))temp.add(value);
            J_ChildFilterOptions.put("Select State",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.minorityOptions))temp.add(value);
            J_ChildFilterOptions.put("Minority",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.womenOptions))temp.add(value);
            J_ChildFilterOptions.put("Women",temp);

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

            //initialize parent options for filter list according to the message
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.filterOptionsParentNriPioFnCiwgTp))J_ParentFilterOptions.add(value);

            temp=new ArrayList<String>();
            for (String year:yearList)temp.add(year);
            J_ChildFilterOptions.put("Year",temp);
            temp=new ArrayList<String>();
            for(String value:getResources().getStringArray(R.array.courseType))temp.add(value);
            J_ChildFilterOptions.put("Course Type",temp);

        }else if (message.equals("closed_courses")||message.equals("closed_institutes")){

            //initialize parent options for filter list according to the message
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.filterOptionsParentClosedCoursesOrInstitute))J_ParentFilterOptions.add(value);

            temp=new ArrayList<String>();
            for (String year:yearList)temp.add(year);
            J_ChildFilterOptions.put("Year",temp);

        }else if (message.equals("dashboard")){

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.parentOptionsDashboard))J_ParentFilterOptions.add(value);

            temp=new ArrayList<String>();
            for (String year:yearList)temp.add(year);
            J_ChildFilterOptions.put("Year",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.programOptionDashboard))temp.add(value);
            J_ChildFilterOptions.put("Select Program",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.levelOptions))temp.add(value);
            J_ChildFilterOptions.put("Select Level",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.institutionTypeDashboard))temp.add(value);
            J_ChildFilterOptions.put("Institution Type",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.stateOptionDashboard))temp.add(value);
            J_ChildFilterOptions.put("Select State",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.minorityOptions))temp.add(value);
            J_ChildFilterOptions.put("Minority",temp);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.womenOptions))temp.add(value);
            J_ChildFilterOptions.put("Women",temp);
        }
    }

    public void filterList(View v){

        //on click of filter button the selected option is submitted to server through this module
        String[] values=new String[J_ParentFilterOptions.size()+3];
        for(int i=0;i<J_ParentFilterOptions.size();++i){
            String sudoArray="";
            for(int j=0;j<J_ChildFilterOptions.get(J_ParentFilterOptions.get(i)).size();++j){
                if(checkBoxStateAll.get((i+"")+(j+""))==true){
                    sudoArray=sudoArray+"\""+J_ChildFilterOptions.get(J_ParentFilterOptions.get(i)).get(j)+"\""+",";
                }
            }
            if(J_ParentFilterOptions.get(i).equals("Year")&&sudoArray.equals("")){
                sudoArray="[\""+"2016-2017"+"\"]";
            }else if(J_ParentFilterOptions.get(i).equals("Course Type")&&sudoArray.equals("")){
                sudoArray="[\"NRI\"]";
            }else if(sudoArray.equals("")||sudoArray.equals("\"--All--\",")){
                sudoArray="[\"1\"]";
            }else {
                sudoArray = sudoArray.substring(0, sudoArray.length() - 1);
                sudoArray = "[" + sudoArray + "]";
            }
            values[i]=sudoArray;
        }

        if(J_message.equals("dashboard")){
            Intent intent=new Intent(DoubleNavigationWithoutEditText.this,MainActivity.class);
            intent.putExtra("selectedValues",values);
            intent.putExtra("yearList",J_yearList);
            startActivity(intent);

        }else if (J_message.equals("approved_institutes")){
            J_keywords=(AutoCompleteTextView)findViewById(R.id.x_activity_all_lists_keywords);
            if(J_keywords.getText().toString()==null) {
                values[7] = "";
            }else {
                values[7] = "\""+J_keywords.getText().toString()+"\"";
            }
            values[8]="\"\"";
            values[9]=0+"";
            Intent intent=new Intent(DoubleNavigationWithoutEditText.this,BaseClassAllLists.class);
            intent.putExtra("selectedValues",values);
            intent.putExtra("activitySelected",J_message);
            intent.putExtra("yearList",J_yearList);
            startActivity(intent);

        }else if (J_message.equals("closed_courses")||J_message.equals("closed_institutes")){
            values[1]="\"\"";
            values[3]=0+"";
            Intent intent=new Intent(DoubleNavigationWithoutEditText.this,BaseClassAllLists.class);
            intent.putExtra("selectedValues",values);
            intent.putExtra("activitySelected",J_message);
            intent.putExtra("yearList",J_yearList);
            startActivity(intent);

        }else if (J_message.equals("nri/pio-fn-ciwg/tp")){
            values[2]="\"\"";
            values[4]=0+"";
            Intent intent=new Intent(DoubleNavigationWithoutEditText.this,BaseClassAllLists.class);
            intent.putExtra("selectedValues",values);
            intent.putExtra("activitySelected",J_message);
            intent.putExtra("yearList",J_yearList);
            startActivity(intent);

        }
    }

    private void ActionBarTitleChange(String message) {

        J_ActionBarTitle=(TextView)findViewById(R.id.x_custom_action_bar_layout_title);
        if(message.equals("approved_institutes")){

            J_ActionBarTitle.setText("Approved Institutes");

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

            J_ActionBarTitle.setText("NRI/PIO-FN-CIWG/TP");

        }else if (message.equals("closed_courses")){

            J_ActionBarTitle.setText("Closed Courses");

        }else if (message.equals("closed_institutes")){

            J_ActionBarTitle.setText("Closed Institutes");

        }else if (message=="dashboard"){
            J_ActionBarTitle.setText("AICTE Dashboard");
        }

    }
    public void onBackPressed(){

        if(J_DrawerLayout.isDrawerOpen(Gravity.RIGHT)){

            J_DrawerLayout.closeDrawer(Gravity.RIGHT);

        }else if(J_DrawerLayout.isDrawerOpen(Gravity.LEFT)){

            J_DrawerLayout.closeDrawer(Gravity.LEFT);

        }else if(J_message.equals("approved_institutes")||J_message.equals("nri/pio-fn-ciwg/tp")||J_message.equals("closed_courses")||J_message.equals("closed_institutes")){

            String[] values={"[\"2016-2017\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]"};
            Intent i=new Intent(DoubleNavigationWithoutEditText.this,MainActivity.class);
            i.putExtra("yearList",J_yearList);
            i.putExtra("selectedValues",values);
            startActivity(i);

        }else{

            moveTaskToBack(true);

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id==R.id.exit){

            moveTaskToBack(true);

        }else if (id==R.id.settings){

            Intent intent=new Intent(DoubleNavigationWithoutEditText.this,Settings.class);
            intent.putExtra("yearList",J_yearList);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}
