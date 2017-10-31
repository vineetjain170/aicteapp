package com.vscholars.stack2code.aicte_phaseone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity{
    ListView mainList;
    TextView J_ActionBarTitle;
    ImageView actionBarIcon;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        mainList=(ListView)findViewById(R.id.x_settings_main_list);
        String[] options={"FeedBack","Delete my Account"};
        int[] icons={R.drawable.settings_feedback,R.drawable.settings_signout};
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        J_ActionBarTitle=(TextView)findViewById(R.id.x_custom_action_bar_layout_title);
        J_ActionBarTitle.setText("Settings");
        actionBarIcon=(ImageView)findViewById(R.id.x_custom_action_bar_layout_main_drawer);
        actionBarIcon.setVisibility(View.INVISIBLE);
        mainList.setAdapter(new AdapterForMainNavigationList(Settings.this,options,icons));
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "feedback@aicte.org", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject Here");
                    Settings.this.startActivity(Intent.createChooser(emailIntent, null));

                }else if (position==1){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setMessage("Are you sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    final ProgressDialog pd = new ProgressDialog(Settings.this);
                                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    pd.setMessage("Signing out...");
                                    pd.setIndeterminate(true);
                                    pd.setCancelable(false);
                                    pd.show();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {

                                            final jsonClasses executer=new jsonClasses("logOut");
                                            SharedPreferences sharedPreferences;
                                            sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
                                            String[] params={sharedPreferences.getString("email",null),sharedPreferences.getString("token",null)};
                                            executer.J_jsonLogOut.execute(params);
                                            while (executer.success==-1){

                                                int slept=0;
                                                try {
                                                    if (slept<30000) {
                                                        Thread.sleep(100);
                                                        slept=slept+100;
                                                    }else {

                                                        break;

                                                    }
                                                } catch (InterruptedException e) {

                                                }

                                            }
                                            if (executer.success==1){

                                                Intent intent=new Intent(Settings.this,SignInSignUp.class);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("email",null);
                                                editor.putString("token",null);
                                                editor.putString("mpin",null);
                                                editor.commit();
                                                startActivity(intent);

                                            }else if (executer.success==0){

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Settings.this,executer.registrationStatus,Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                            }else if (executer.success==-1){

                                                Handler mHandler=new Handler(Looper.getMainLooper()) {
                                                    @Override
                                                    public void handleMessage(Message message) {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                                                        builder.setMessage("Looks like server isn't responding...please check your mobile data")
                                                                .setPositiveButton("Abort", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int id) {
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
                                                };
                                                Message message = mHandler.obtainMessage();
                                                message.sendToTarget();

                                            }

                                        }
                                    }).start();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent intent=new Intent(Settings.this,Settings.class);
                                    startActivity(intent);

                                }
                            });
                    builder.create();
                    builder.setCancelable(true);
                    builder.show();

                }
            }
        });

    }
    public void onBackPressed(){

        String[] values={"[\"2016-2017\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]"};
        Intent i=new Intent(Settings.this,MainActivity.class);
        i.putExtra("yearList",getIntent().getStringArrayExtra("yearList"));
        i.putExtra("selectedValues",values);
        startActivity(i);

    }

}