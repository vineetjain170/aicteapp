package com.vscholars.stack2code.aicte_phaseone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OtpVerify extends AppCompatActivity{
    static EditText otp;
    Button submit,resend;
    jsonClasses executer;
    SharedPreferences sharedPreferences;
    IncomingSms incomingSms=new IncomingSms();

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.otp_page);
        otp=(EditText)findViewById(R.id.x_otp_verify_otp_input);
        resend=(Button)findViewById(R.id.x_otp_resend_otp);
        submit=(Button)findViewById(R.id.x_otp_verify_submit);
        final String email=getIntent().getStringExtra("email");
        final String name=getIntent().getStringExtra("name");
        final String phoneno=getIntent().getStringExtra("phoneno");
        registerReceiver(incomingSms,new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

        new Thread(new Runnable() {
            @Override
            public void run() {

                resend.setOnClickListener(null);
                resend.setBackgroundColor(Color.GRAY);
                final int[] slept = {0};
                while(slept[0] <300000) {
                    try {

                        Thread.sleep(100);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                slept[0] = slept[0] + 100;
                                resend.setText("Resend OTP in "+((300000 - slept[0]) / 1000) + " seconds...");

                            }
                        });
                    } catch (InterruptedException e) {


                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        resend.setBackgroundColor(Color.RED);
                        resend.setText("RESEND OTP");
                        submit.setOnClickListener(null);
                        submit.setBackgroundColor(Color.GRAY);
                        resend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                executer=new jsonClasses("otpResend");
                                String[] params={email,"resend"};
                                executer.J_otpResend.execute(params);
                                final ProgressDialog pd = new ProgressDialog(OtpVerify.this);
                                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                pd.setMessage("Fetching another OTP...");
                                pd.setIndeterminate(true);
                                pd.setCancelable(false);
                                pd.show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        int slept=0;
                                        while(executer.success==-1){

                                            try {
                                                if(slept<30000) {
                                                    Thread.sleep(100);
                                                    slept=slept+100;
                                                }else {

                                                    break;

                                                }
                                            } catch (InterruptedException e) {

                                            }

                                        }
                                        pd.dismiss();
                                        if (executer.success==1){

                                            Intent intent=new Intent(OtpVerify.this,OtpVerify.class);
                                            intent.putExtra("email",email);
                                            startActivity(intent);

                                        }else if (executer.success==0){

                                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                                @Override
                                                public void handleMessage(Message message) {
                                                    Toast.makeText(OtpVerify.this,"Some error occured...",Toast.LENGTH_LONG).show();
                                                }
                                            };

                                            Message message = mHandler.obtainMessage();
                                            message.sendToTarget();

                                        }else if (executer.success==-1){

                                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                                @Override
                                                public void handleMessage(Message message) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(OtpVerify.this);
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
                        });

                    }
                });

            }
        }).start();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otpInput=otp.getText().toString();
                String[] params={email,otpInput,"validate"};
                executer=new jsonClasses("otpVerify");
                executer.J_otpVerify.execute(params);
                final ProgressDialog pd = new ProgressDialog(OtpVerify.this);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Verifying OTP...");
                pd.setIndeterminate(true);
                pd.setCancelable(false);
                pd.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int slept=0;
                        while(executer.success==-1){

                            try {
                                if (slept<30000) {
                                    Thread.sleep(100);
                                    slept=slept+100;
                                }
                            } catch (InterruptedException e) {

                            }

                        }
                        pd.dismiss();
                        if (executer.success==1){

                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                @Override
                                public void handleMessage(Message message) {
                                    sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
                                    final SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email",email);
                                    editor.putString("name",name);
                                    editor.putString("phoneno",phoneno);
                                    editor.putString("token",executer.registrationStatus);
                                    editor.commit();
                                    Intent intent=new Intent(OtpVerify.this,mpin.class);
                                    startActivity(intent);
                                }
                            };

                            Message message = mHandler.obtainMessage();
                            message.sendToTarget();

                        }else if (executer.success==0){

                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                @Override
                                public void handleMessage(Message message) {
                                    Toast.makeText(OtpVerify.this,"Wrong OTP",Toast.LENGTH_LONG).show();
                                }
                            };

                            Message message = mHandler.obtainMessage();
                            message.sendToTarget();

                        }else if (executer.success==-1){

                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                @Override
                                public void handleMessage(Message message) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(OtpVerify.this);
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
        });

    }
    public void onBackPressed(){

        moveTaskToBack(true);

    }
    public void recivedSms(String message)
    {
        try
        {
            otp.setText(message.substring(24,message.length()-19));
        }
        catch (Exception e)
        {
        }
    }

}