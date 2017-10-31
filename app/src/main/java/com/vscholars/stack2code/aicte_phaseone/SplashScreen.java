package com.vscholars.stack2code.aicte_phaseone;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.vscholars.stack2code.aicte_phaseone.DataItems.DashboardDataItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by shaggy on 12-06-2017.
 */

public class SplashScreen extends AppCompatActivity{
    private String[] j_yearList;
    private ProgressBar J_DataLoading;
    private SharedPreferences sharedPreferences;

    @Override
        public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initializer();

        new json_yearFetch().execute("");

    }

    private void initializer() {

        setContentView(R.layout.splash_screen);

        Window window = SplashScreen.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);

        J_DataLoading=(ProgressBar)findViewById(R.id.x_splash_screen_progress_bar);

        //actionbar hide
        ActionBar ac = getSupportActionBar();
        ac.hide();

    }
    //JSON code to fetch year
    protected class json_yearFetch extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonA =JSONParser.makeHttpRequest("http://anurag.webutu.com/year_select.php", "POST", params);

                int success=jsonA.getInt("success");
                if (success == 1) {
                    int count=jsonA.getInt("count");

                    j_yearList=new String[count];
                    JSONArray jsona= jsonA.getJSONArray("ayear");

                    for(int i=1;i<=count;i++){

                        JSONObject temp =jsona.getJSONObject(i-1);
                        j_yearList[i-1]=temp.getString(""+i);

                    }


                } else {

                    Toast.makeText(SplashScreen.this,"Error Occured Please try after sometime",Toast.LENGTH_LONG).show();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            if(j_yearList==null){

                J_DataLoading.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                builder.setMessage("No active mobile data")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                new json_yearFetch().execute();

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

            }else{

                SharedPreferences sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
                jsonClasses executer=new jsonClasses("notification");
                String[] params = {sharedPreferences.getString("email",null),sharedPreferences.getString("firebaseToken",null)};

                String refreshedToken = " "+ FirebaseInstanceId.getInstance().getToken();
                Log.e(" ", "SplashToken: " + refreshedToken);

                executer.J_Notification.execute(params);
                SplashScreen.this.finish();
                String[] defaultValues={"[\"2016-2017\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]","[\"1\"]"};
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.putExtra("yearList",j_yearList);
                intent.putExtra("selectedValues",defaultValues);
                startActivity(intent);

            }

        }

    }
}
