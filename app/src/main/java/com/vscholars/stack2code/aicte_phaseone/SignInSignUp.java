package com.vscholars.stack2code.aicte_phaseone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vineet_jain on 31/7/17.
 */

public class SignInSignUp extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText email,name,phoneNo;
    Button next;
    SignInButton googlePlus;
    jsonClasses executer;
    SharedPreferences sharedPreferences;
    GoogleApiClient googleApiClient;
    RelativeLayout container;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.signin_page);
        container=(RelativeLayout)findViewById(R.id.x_signin_page_container);
        sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getString("token",null)!=null){

            container.setVisibility(View.INVISIBLE);
            String[] params={sharedPreferences.getString("email",null),sharedPreferences.getString("token",null)};
            executer=new jsonClasses("smsLogin");
            executer.J_smsLogin.execute(params);
            final ProgressDialog pd = new ProgressDialog(SignInSignUp.this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Logging you in...");
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
                            }else {

                                break;

                            }
                        } catch (InterruptedException e) {

                        }

                    }
                    pd.dismiss();
                    if (executer.success==1){

                        Handler mHandler=new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message message) {

                                Intent intent=new Intent(SignInSignUp.this,mpin.class);
                                startActivity(intent);
                            }
                        };

                        Message message = mHandler.obtainMessage();
                        message.sendToTarget();

                    }else if (executer.success==0){

                        Handler mHandler=new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message message) {

                                Toast.makeText(SignInSignUp.this,"You have been logged out",Toast.LENGTH_LONG).show();
                                editor.putString("email",null);
                                editor.putString("token",null);
                                editor.commit();
                                container.setVisibility(View.VISIBLE);

                            }
                        };

                        Message message = mHandler.obtainMessage();
                        message.sendToTarget();

                    }else if (executer.success==-1){

                        Handler mHandler=new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message message) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInSignUp.this);
                                builder.setMessage("Looks like server isn't responding...please check your mobile data")
                                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                Intent intent=new Intent(SignInSignUp.this,SignInSignUp.class);
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
                        };
                        Message message = mHandler.obtainMessage();
                        message.sendToTarget();

                    }

                }
            }).start();

        }
        initializer();

    }

    private void initializer() {

        getSupportActionBar().hide();
        name = (EditText) findViewById(R.id.x_signup_screen_name);
        email = (EditText) findViewById(R.id.x_signup_screen_email);
        phoneNo = (EditText) findViewById(R.id.x_signup_screen_phoneno);
        next = (Button) findViewById(R.id.x_signup_screen_submit);
        googlePlus = (SignInButton) findViewById(R.id.sign_in_button);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(SignInSignUp.this).enableAutoManage(SignInSignUp.this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( checkNo(phoneNo.getText().toString()) && checkEmail(email.getText().toString()) ) {
                    String[] values = {name.getText().toString(), email.getText().toString(), phoneNo.getText().toString(), "generate"};
                    executer = new jsonClasses("signUp");
                    executer.J_signUp.execute(values);
                    final ProgressDialog pd = new ProgressDialog(SignInSignUp.this);
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.setMessage("Generating OTP...");
                    pd.setIndeterminate(true);
                    pd.setCancelable(false);
                    pd.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            int slept = 0;
                            while (executer.success == -1) {

                                try {
                                    if (slept < 30000) {
                                        Thread.sleep(100);
                                        slept = slept + 100;
                                    } else {

                                        break;

                                    }
                                } catch (InterruptedException e) {

                                }

                            }
                            pd.dismiss();
                            if (executer.success == 1) {

                                Handler mHandler = new Handler(Looper.getMainLooper()) {
                                    @Override
                                    public void handleMessage(Message message) {

                                        Intent intent = new Intent(SignInSignUp.this, OtpVerify.class);
                                        intent.putExtra("email", email.getText().toString());
                                        intent.putExtra("name", name.getText().toString());
                                        intent.putExtra("phoneno", phoneNo.getText().toString());
                                        startActivity(intent);
                                    }
                                };

                                Message message = mHandler.obtainMessage();
                                message.sendToTarget();

                            } else if (executer.success == 0) {

                                Handler mHandler = new Handler(Looper.getMainLooper()) {
                                    @Override
                                    public void handleMessage(Message message) {

                                        Toast.makeText(SignInSignUp.this, "Please Retry...", Toast.LENGTH_LONG).show();

                                    }
                                };

                                Message message = mHandler.obtainMessage();
                                message.sendToTarget();

                            } else if (executer.success == -1) {

                                Handler mHandler = new Handler(Looper.getMainLooper()) {
                                    @Override
                                    public void handleMessage(Message message) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignInSignUp.this);
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
                }else {

                    if(checkNo(phoneNo.getText().toString())==false) {

                        Toast.makeText(SignInSignUp.this, "Enter a 10 digit phone number", Toast.LENGTH_LONG).show();

                    }
                    if (checkEmail(email.getText().toString())==false){

                        Toast.makeText(SignInSignUp.this, "Please enter a valid email id", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

        googlePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i, 9001);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Data input", "onActivityResult:"+data.getData(),null );
        GoogleSignInResult googleSignInResult=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        handleresult(googleSignInResult);
    }
    private void handleresult(GoogleSignInResult result){
        if(result.isSuccess()==true){

            GoogleSignInAccount googleSignInAccount=result.getSignInAccount();
            final String g_email = googleSignInAccount.getEmail();
            final String g_name=googleSignInAccount.getDisplayName();
            name.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            googlePlus.setVisibility(View.INVISIBLE);
            next.setOnClickListener(null);

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkNo(phoneNo.getText().toString())) {

                        String[] values = {g_name, g_email, phoneNo.getText().toString(), "generate"};
                        executer = new jsonClasses("signUp");
                        executer.J_signUp.execute(values);
                        final ProgressDialog pd = new ProgressDialog(SignInSignUp.this);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.setMessage("Generating OTP...");
                        pd.setIndeterminate(true);
                        pd.setCancelable(false);
                        pd.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                int slept = 0;
                                while (executer.success == -1) {

                                    try {
                                        if (slept < 30000) {
                                            Thread.sleep(100);
                                            slept = slept + 100;
                                        } else {

                                            break;

                                        }
                                    } catch (InterruptedException e) {

                                    }

                                }
                                pd.dismiss();
                                if (executer.success == 1) {

                                    Handler mHandler = new Handler(Looper.getMainLooper()) {
                                        @Override
                                        public void handleMessage(Message message) {

                                            Intent intent = new Intent(SignInSignUp.this, OtpVerify.class);
                                            intent.putExtra("email", g_email);
                                            intent.putExtra("name", g_name);
                                            intent.putExtra("phoneno", phoneNo.getText().toString());
                                            startActivity(intent);
                                        }
                                    };

                                    Message message = mHandler.obtainMessage();
                                    message.sendToTarget();

                                } else if (executer.success == 0) {

                                    Handler mHandler = new Handler(Looper.getMainLooper()) {
                                        @Override
                                        public void handleMessage(Message message) {

                                            Toast.makeText(SignInSignUp.this, "Please Retry...", Toast.LENGTH_LONG).show();

                                        }
                                    };

                                    Message message = mHandler.obtainMessage();
                                    message.sendToTarget();

                                } else if (executer.success == -1) {

                                    Handler mHandler = new Handler(Looper.getMainLooper()) {
                                        @Override
                                        public void handleMessage(Message message) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(SignInSignUp.this);
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
                    }else {

                        Toast.makeText(SignInSignUp.this,"Enter a 10 digit phone number",Toast.LENGTH_LONG).show();

                    }

                }
            });
        }
        else {

            Toast.makeText(SignInSignUp.this,"Data fetch error...",Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(SignInSignUp.this,"Please check your network",Toast.LENGTH_LONG).show();

    }
    public void onBackPressed(){

        moveTaskToBack(true);

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(SignInSignUp.this,"android.permission.RECEIVE_SMS") != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) SignInSignUp.this, "android.permission.RECEIVE_SMS")) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignInSignUp.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write calendar permission is necessary to write event!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity)SignInSignUp.this, new String[]{"android.permission.RECEIVE_SMS"}, 123);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity)SignInSignUp.this, new String[]{"android.permission.RECEIVE_SMS"}, 123);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    boolean checkNo(String phoneno){

        if(phoneno.length()==10){

            return true;

        }
        else {

            return false;

        }

    }
    boolean checkEmail(String email){

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();

    }

}
