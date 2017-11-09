package com.vscholars.stack2code.aicte_phaseone;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Announcements extends AppCompatActivity{

    ExpandableListView mainList;
    SharedPreferences sharedPreferences;
    TextView J_ActionBarTitle;
    ImageView actionBarIcon;
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcements);
        mainList=(ExpandableListView)findViewById(R.id.announcements_listview);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        J_ActionBarTitle=(TextView)findViewById(R.id.x_custom_action_bar_layout_title);
        J_ActionBarTitle.setText("AICTE Announcements");
        actionBarIcon=(ImageView)findViewById(R.id.x_custom_action_bar_layout_main_drawer);
        actionBarIcon.setVisibility(View.INVISIBLE);

        sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
        String notificationTitles=sharedPreferences.getString("notification_title","null");
        String notificationBody=sharedPreferences.getString("notification_body","null");
        String notificationMessages=sharedPreferences.getString("notification_message","null");
        int notificationCount=sharedPreferences.getInt("notification_count",-1);
        if(notificationCount!=-1){

            String[] notificationTitlesArray=notificationTitles.split("\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)",-1);
            String[] notificationBodyArray=notificationBody.split("\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)",-1);
            String[] notificationMessagesArray=notificationMessages.split("\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)",-1);

            mainList.setAdapter(new AdapterForNotifications(Announcements.this,notificationTitlesArray,notificationBodyArray,notificationMessagesArray));

        }else {

            setContentView(R.layout.no_data_entries);
            TextView textView=(TextView)findViewById(R.id.textView4);
            textView.setText("Sorry no Announcements. You will receive Notification in case of new announcements.");

        }

    }

}