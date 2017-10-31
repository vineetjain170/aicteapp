package com.vscholars.stack2code.aicte_phaseone;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vscholars.stack2code.aicte_phaseone.DataItems.DashboardDataItems;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class MainActivity extends DoubleNavigationWithoutEditText{

    String[] parameterValues=null;
    String[]selectedValues;
    TextView J_totalInstitutions,J_faculties,J_enrollment,J_studentPassed,J_totalIntake,J_placements,J_newInstitutes,J_closedInstitutes,J_marquee;
    int slept=0;

    //This is the main activity of AICTE Dashboard to display statistics

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading Dashboard data...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializer();
                        setValue();
                    }
                });
                pd.dismiss();
            }
        }).start();
    }

    private void getData() {
        selectedValues=getIntent().getStringArrayExtra("selectedValues");
        jsonClasses executer=new jsonClasses("dashboard");
        executer.J_json_dashboardData.execute(selectedValues);
        try {
            while (executer.defaultValuesDashboard[5]==null) {
                if(slept<30000) {
                    Thread.sleep(500);
                    if (executer.defaultValuesDashboard[5] != null) {
                        parameterValues = executer.defaultValuesDashboard;
                    }else {

                        slept=slept+500;

                    }
                }else{

                    break;

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setValue() {

        if(parameterValues!=null) {

            new Thread(new Runnable() {
                @Override
                public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ValueAnimator animator1 = ValueAnimator.ofInt(0,Integer.parseInt(parameterValues[0]));
                                animator1.setDuration(2000);
                                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        J_faculties.setText(i + "");
                                    }
                                });

                                ValueAnimator animator2 = ValueAnimator.ofInt(0,Integer.parseInt(parameterValues[1]));
                                animator2.setDuration(2000);
                                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        J_placements.setText(i + "");
                                    }
                                });

                                ValueAnimator animator3 = ValueAnimator.ofInt(0,Integer.parseInt(parameterValues[2]));
                                animator3.setDuration(2000);
                                animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        J_studentPassed.setText(i + "");
                                    }
                                });

                                ValueAnimator animator4 = ValueAnimator.ofInt(0,Integer.parseInt(parameterValues[3]));
                                animator4.setDuration(2000);
                                animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        J_enrollment.setText(i + "");
                                    }
                                });

                                ValueAnimator animator5 = ValueAnimator.ofInt(0,Integer.parseInt(parameterValues[4]));
                                animator5.setDuration(2000);
                                animator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        J_totalIntake.setText(i + "");
                                    }
                                });

                                ValueAnimator animator6 = ValueAnimator.ofInt(0,Integer.parseInt(parameterValues[5]));
                                animator6.setDuration(2000);
                                animator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        J_totalInstitutions.setText(i + "");
                                    }
                                });

                                ValueAnimator animator7 = ValueAnimator.ofInt(0,Integer.parseInt(parameterValues[6]));
                                animator7.setDuration(2000);
                                animator7.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        J_newInstitutes.setText(i + "");
                                    }
                                });

                                ValueAnimator animator8 = ValueAnimator.ofInt(0,Integer.parseInt(parameterValues[7]));
                                animator8.setDuration(2000);
                                animator8.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        J_closedInstitutes.setText(i + "");
                                    }
                                });
                                animator1.start();
                                animator2.start();
                                animator3.start();
                                animator4.start();
                                animator5.start();
                                animator6.start();
                                animator7.start();
                                animator8.start();

                            }
                        });

                }
            }).start();

        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Looks like server isn't responding...please check your mobile data")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("yearList",getIntent().getStringArrayExtra("yearList"));
                            intent.putExtra("selectedValues",selectedValues);
                            startActivity(intent);

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
    }

    private void initializer() {

        getSupportActionBar().show();
        setContentView(R.layout.activity_main);
        J_totalInstitutions=(TextView)findViewById(R.id.x_activity_main_total_institutions);
        J_faculties=(TextView)findViewById(R.id.x_activity_main_faculties);
        J_enrollment=(TextView)findViewById(R.id.x_activity_main_enrollment);
        J_studentPassed=(TextView)findViewById(R.id.x_activity_main_student_passed);
        J_totalIntake=(TextView)findViewById(R.id.x_activity_main_total_intake);
        J_placements=(TextView)findViewById(R.id.x_activity_main_placement);
        J_newInstitutes=(TextView)findViewById(R.id.x_activity_main_new_institutes);
        J_closedInstitutes=(TextView)findViewById(R.id.x_activity_main_closed_institutes);
        J_marquee=(TextView)findViewById(R.id.x_activity_main_marquee);

        J_marquee.setSelected(true);
        J_marquee.setText(buildMarqueeText(selectedValues));

        Window window = MainActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);

        String[] J_yearList=getIntent().getStringArrayExtra("yearList");

        //This message is passed between activities to indicate which element of dashboard is under consideration
        super.onCreateDrawer(MainActivity.this,"dashboard",J_yearList);

    }
    private String buildMarqueeText(String[] selectedValues) {

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
            String marqueeText="Showing AICTE Approved Institutes for "+year.substring(2,year.length()-2)+", "+program+" program(s), "+level+" level(s), "+institutionType+" institution type(s), "+state+" state(s)";
            return marqueeText;

    }
}
