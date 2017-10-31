package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vineet_jain on 8/6/17.
 */

public class BaseClassAllLists extends DoubleNavigationWithoutEditText{

    //these as the objects which hold the reference to all the views in the activity
    private ExpandableListView J_ListMain;
    private Button J_ButtonNext,J_ButtonPrev;
    private RelativeLayout J_SearchTab;
    private RelativeLayout J_ListsController;
    private AutoCompleteTextView J_keywords;
    private EditText J_pageNo,J_liveSearch;
    private TextView J_totalPages,J_marquee;

    private String[] yearList,selectedValues;
    private String message;
    private HashMap<Integer,String>categoryNames;
    private ArrayList<String>categoriesList;
    private jsonClasses executer;
    private String totalPages;
    private int slept=0;

    //these variables are used to keep track of user touch events
    //private int mLastFirstVisibleItem=0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //initialize the activity according to the message
        message=getIntent().getStringExtra("activitySelected");
        if(getIntent().getStringExtra("totalEntries")!=null){

            totalPages=getIntent().getStringExtra("totalEntries");

        }
        getSupportActionBar().hide();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading data entries...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData(message);
                keepACheck();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializer(message);
                    }
                });
                pd.dismiss();
            }
        }).start();
    }

    private void getData(String message) {

        executer=new jsonClasses(message);
        selectedValues=getIntent().getStringArrayExtra("selectedValues");

        if (message.equals("approved_institutes")){

            executer.J_json_hintList.execute();
            executer.J_json_approvedInstitutions.execute(selectedValues);


        }else if (message.equals("closed_courses")){

            executer.J_json_closedCourses.execute(selectedValues);


        }else if (message.equals("closed_institutes")){

            executer.J_json_closedInstitutes.execute(selectedValues);

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

            executer.J_json_NriPioFci.execute(selectedValues);

        }

    }

    void keepACheck(){

        try {
            while (executer.data==null) {

                if (slept < 30000) {

                    Thread.sleep(500);
                    slept=slept+500;
                    if (executer.data != null) {

                        categoryNames = executer.categoriesNames;
                        categoriesList = new ArrayList<String>();
                        for (int i = 0; i < categoryNames.size(); ++i) {

                            categoriesList.add(categoryNames.get(i));

                        }

                    }
                    if (executer.categoriesNames != null) {

                        if (executer.categoriesNames.get(-1) != null)

                            break;

                    }
                    if(executer.response.equals("nothingReceived")) {

                        break;

                    }
                }else {

                    executer.response="nothingReceived";
                    break;

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

    public void initializer(final String message){

        //message is passed by DoubleNavigationDrawerWithoutEditText class when user selects an activity from main navigation drawer
        //actions specific to message are taken
        getSupportActionBar().show();
        setContentView(R.layout.activity_all_lists);

        J_ListMain=(ExpandableListView) findViewById(R.id.x_activity_all_lists_list_main);
        J_ButtonNext=(Button)findViewById(R.id.x_activity_all_lists_next_button);
        J_ButtonPrev=(Button)findViewById(R.id.x_activity_all_lists_prev_button);
        J_SearchTab=(RelativeLayout)findViewById(R.id.x_activity_all_lists_search_tab);
        J_ListsController=(RelativeLayout)findViewById(R.id.x_activity_all_lists_list_controller);
        J_totalPages=(TextView)findViewById(R.id.x_activity_all_lists_total_pages);
        J_pageNo=(EditText)findViewById(R.id.x_activity_all_lists_page_no);
        J_liveSearch=(EditText)findViewById(R.id.x_activity_all_lists_live_search);
        J_keywords=(AutoCompleteTextView)findViewById(R.id.x_activity_all_lists_keywords);
        J_marquee=(TextView)findViewById(R.id.x_activity_all_lists_marquee);

        J_liveSearch.setImeOptions(J_liveSearch.getImeOptions()| EditorInfo.IME_ACTION_DONE);

        if (!executer.response.equals("nothingReceived")) {

            if (message.equals("approved_institutes")) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, executer.hintsList);
                J_keywords.setAdapter(adapter);

            }else {

                ImageView imageButton=(ImageView) findViewById(R.id.imageButton2);
                imageButton.setVisibility(View.INVISIBLE);
                J_keywords.setVisibility(View.INVISIBLE);

            }

            int count = Integer.parseInt(selectedValues[selectedValues.length - 1]);
            if (count == 0) {
                J_ButtonPrev.setVisibility(View.INVISIBLE);
                J_marquee.setSelected(true);
                J_marquee.setText(buildMarqueeText(selectedValues));
            } else {
                J_ButtonPrev.setVisibility(View.VISIBLE);
            }
            if (totalPages != null) {
                if (count + 20 > Integer.parseInt(totalPages)) {
                    J_ButtonNext.setVisibility(View.INVISIBLE);
                } else {
                    J_ButtonNext.setVisibility(View.VISIBLE);
                }
            }
            J_pageNo.setText((count / 20) + "");

            yearList = getIntent().getStringArrayExtra("yearList");

            super.onCreateDrawer(BaseClassAllLists.this, message, yearList);
            if (executer.categoriesNames.get(-1) != null) {
                if (executer.categoriesNames.get(-1).equals("-1")) {
                    RelativeLayout noMoreData = (RelativeLayout) findViewById(R.id.x_noMoreData);
                    noMoreData.setVisibility(View.VISIBLE);
                    J_SearchTab.setVisibility(View.INVISIBLE);
                    J_ListsController.setVisibility(View.INVISIBLE);
                }
            } else {
                RelativeLayout noMoreData = (RelativeLayout) findViewById(R.id.x_noMoreData);
                noMoreData.setVisibility(View.INVISIBLE);
                J_SearchTab.setVisibility(View.VISIBLE);
                J_ListsController.setVisibility(View.VISIBLE);
                J_ListMain.setAdapter(new AdapterForAllLists(BaseClassAllLists.this, message, categoryNames.size(), assignParameters(message), categoriesList, executer.categories, executer.data, selectedValues[0], J_keywords.getText().toString()));
                if (!executer.totalEntries.equals("previousOne")) {
                    J_totalPages.setText("/" + (Integer.parseInt(executer.totalEntries) / 20));
                } else {

                    J_totalPages.setText("/" + (Integer.parseInt(totalPages) / 20));
                }
            }
        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(BaseClassAllLists.this);
            builder.setMessage("Looks like server isn't responding...please check your mobile data")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                            i.putExtra("activitySelected",message);
                            i.putExtra("selectedValues",selectedValues);
                            i.putExtra("yearList",getIntent().getStringArrayExtra("yearList"));
                            startActivity(i);

                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            System.exit(0);

                        }
                    });
            builder.create();
            builder.setCancelable(false);
            builder.show();

        }


        setAllViewListeners();

    }

    private void setAllViewListeners() {

        J_ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(selectedValues[selectedValues.length-1]);
                count+=20;
                selectedValues[selectedValues.length-1]=count+"";
                Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                i.putExtra("activitySelected",message);
                i.putExtra("selectedValues",selectedValues);
                i.putExtra("yearList",yearList);
                if(!executer.totalEntries.equals("previousOne")){

                    i.putExtra("totalEntries",executer.totalEntries);

                }else {

                    i.putExtra("totalEntries",totalPages);

                }
                startActivity(i);
            }
        });
        J_ButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count=Integer.parseInt(selectedValues[selectedValues.length-1]);
                if (count!=0) {
                    count -= 20;
                    selectedValues[selectedValues.length - 1] = count + "";
                    Intent i = new Intent(BaseClassAllLists.this, BaseClassAllLists.class);
                    i.putExtra("activitySelected", message);
                    i.putExtra("selectedValues", selectedValues);
                    i.putExtra("yearList", yearList);
                    if(!executer.totalEntries.equals("previousOne")){

                        i.putExtra("totalEntries",executer.totalEntries);

                    }else {

                        i.putExtra("totalEntries",totalPages);

                    }
                    startActivity(i);
                }

            }
        });
        J_pageNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:

                        int offset=Integer.parseInt(v.getText().toString());
                        selectedValues[selectedValues.length-1]=offset*20+"";
                        Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                        i.putExtra("activitySelected",message);
                        i.putExtra("selectedValues",selectedValues);
                        i.putExtra("yearList",yearList);
                        if(!executer.totalEntries.equals("previousOne")){

                            i.putExtra("totalEntries",executer.totalEntries);

                        }else {

                            i.putExtra("totalEntries",totalPages);

                        }
                        startActivity(i);
                        return true;

                    default:
                        return false;
                }
            }
        });

        J_liveSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_DONE:
                        String query=J_liveSearch.getText().toString().trim();
                        if (message.equals("approved_institutes")){
                            selectedValues[8]="\""+query+"\"";
                            selectedValues[9]=0+"";
                            Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                            i.putExtra("activitySelected",message);
                            i.putExtra("selectedValues",selectedValues);
                            i.putExtra("yearList",yearList);
                            if(!executer.totalEntries.equals("previousOne")){

                                i.putExtra("totalEntries",executer.totalEntries);

                            }else {

                                i.putExtra("totalEntries",totalPages);

                            }
                            startActivity(i);
                            return true;

                        }else if (message.equals("closed_courses")||message.equals("closed_institutes")){

                            selectedValues[1]="\""+query+"\"";
                            selectedValues[3]=0+"";
                            Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                            i.putExtra("activitySelected",message);
                            i.putExtra("selectedValues",selectedValues);
                            i.putExtra("yearList",yearList);
                            if(!executer.totalEntries.equals("previousOne")){

                                i.putExtra("totalEntries",executer.totalEntries);

                            }else {

                                i.putExtra("totalEntries",totalPages);

                            }
                            startActivity(i);
                            return true;

                        }else if (message.equals("nri/pio-fn-ciwg/tp")){

                            selectedValues[2]="\""+query+"\"";
                            selectedValues[4]=0+"";
                            Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                            i.putExtra("activitySelected",message);
                            i.putExtra("selectedValues",selectedValues);
                            i.putExtra("yearList",yearList);
                            if(!executer.totalEntries.equals("previousOne")){

                                i.putExtra("totalEntries",executer.totalEntries);

                            }else {

                                i.putExtra("totalEntries",totalPages);

                            }
                            startActivity(i);
                            return true;

                        }
                    default:
                        return false;
                }
            }
        });

    }

    private String buildMarqueeText(String[] selectedValues) {

        if(message.equals("approved_institutes")){

            String year=selectedValues[0];
            String program=selectedValues[1];
            String level=selectedValues[2];
            String institutionType=selectedValues[3];
            String state=selectedValues[4];

            if(program.equals("[\"1\"]")){

                program="all";

            }
            if(level.equals("[\"1\"]")){

                level="all";

            }
            if (institutionType.equals("[\"1\"]")){

                institutionType="all";

            }
            if (state.equals("[\"1\"]")){

                state="all";

            }
            String marqueeText="Showing approved institutes for "+year.substring(2,year.length()-2)+", "+program+" program(s), "+level+" level(s), "+institutionType+" institution type(s), "+state+" state(s)";
            return marqueeText;

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

            String year=selectedValues[0];
            String type=selectedValues[1];

            String marquee="Showing results for "+year.substring(2,year.length()-2)+", "+type.substring(2,type.length()-2)+" courses";
            return marquee;

        }else if (message.equals("closed_courses")||message.equals("closed_institutes")){

            String year=selectedValues[0];

            String marquee="Showing results for "+year.substring(2,year.length()-2);
            return marquee;

        }
        return null;
    }

    private int assignParameters(String message) {

        if(message.equals("approved_institutes")){

            return 8;

        }else if (message.equals("nri/pio-fn-ciwg/tp")||message.equals("closed_courses")){

            return 11;

        }else if (message.equals("closed_institutes")){

            return 7;

        }else {
            return 0;
        }
    }

}

/*J_ListMain.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                ExpandableListView mainListView = (ExpandableListView)findViewById(R.id.x_activity_all_lists_list_main);
                if (view.getId() == mainListView.getId()) {
                    int currentFirstVisibleItem = mainListView.getFirstVisiblePosition();
                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {

                        J_SearchTab.setVisibility(View.INVISIBLE);
                        J_ListsController.setVisibility(View.VISIBLE);
                        J_marquee.setVisibility(View.VISIBLE);
                        getSupportActionBar().hide();
                        J_ListMain.setPadding(0,0,0,95);

                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {

                        J_SearchTab.setVisibility(View.VISIBLE);
                        J_ListsController.setVisibility(View.INVISIBLE);
                        J_marquee.setVisibility(View.INVISIBLE);
                        getSupportActionBar().show();
                        J_ListMain.setPadding(0,105,0,0);
                    }
                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }
            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/
