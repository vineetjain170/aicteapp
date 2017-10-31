package com.vscholars.stack2code.aicte_phaseone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class mpin extends AppCompatActivity{

    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button0,buttondel,buttonproceed;
    ImageView imageView1,imageView2,imageView3,imageView4;
    TextView status;
    SharedPreferences sharedPreferences;
    String password="",prev="";
    int length=0;
    TextView forgotMpin;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpin_page);
        getSupportActionBar().hide();
        initializer();
        sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getString("mpin",null)==null){

            Toast.makeText(mpin.this,"Please set a MPIN for your phone",Toast.LENGTH_LONG).show();

            buttonproceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(status.getText().equals("Enter MPIN")){

                        status.setText("Retype MPIN");
                        prev=password;
                        password="";
                        length=0;
                        imageView1.setVisibility(View.INVISIBLE);
                        imageView2.setVisibility(View.INVISIBLE);
                        imageView3.setVisibility(View.INVISIBLE);
                        imageView4.setVisibility(View.INVISIBLE);

                    }else if(status.getText().equals("Retype MPIN")) {

                        if(prev.equals(password)){

                            editor.putString("mpin",password);
                            editor.commit();
                            finish();
                            Intent intent=new Intent(mpin.this,SplashScreen.class);
                            startActivity(intent);

                        }else {

                            password="";
                            length=0;
                            imageView1.setVisibility(View.INVISIBLE);
                            imageView2.setVisibility(View.INVISIBLE);
                            imageView3.setVisibility(View.INVISIBLE);
                            imageView4.setVisibility(View.INVISIBLE);
                            Toast.makeText(mpin.this,"MPIN didn't matched...Renter",Toast.LENGTH_LONG).show();

                        }

                    }

                }
            });

        }else{

            buttonproceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(password.equals(sharedPreferences.getString("mpin",null))){

                        finish();
                        Intent intent=new Intent(mpin.this,SplashScreen.class);
                        startActivity(intent);

                    }else {

                        password="";
                        length=0;
                        imageView1.setVisibility(View.INVISIBLE);
                        imageView2.setVisibility(View.INVISIBLE);
                        imageView3.setVisibility(View.INVISIBLE);
                        imageView4.setVisibility(View.INVISIBLE);
                        Toast.makeText(mpin.this,"Incorrect MPIN...Renter",Toast.LENGTH_LONG).show();

                    }

                }
            });

        }

    }

    private void initializer() {

        status=(TextView)findViewById(R.id.x_mpin_status);
        forgotMpin=(TextView)findViewById(R.id.x_mpin_page_forgot_mpin);
        imageView1=(ImageView)findViewById(R.id.imageview_circle1);
        imageView2=(ImageView)findViewById(R.id.imageview_circle2);
        imageView3=(ImageView)findViewById(R.id.imageview_circle3);
        imageView4=(ImageView)findViewById(R.id.imageview_circle4);
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        button0=(Button)findViewById(R.id.x_mpin_button0);
        button1=(Button)findViewById(R.id.x_mpin_button1);
        button2=(Button)findViewById(R.id.x_mpin_button2);
        button3=(Button)findViewById(R.id.x_mpin_button3);
        button4=(Button)findViewById(R.id.x_mpin_button4);
        button5=(Button)findViewById(R.id.x_mpin_button5);
        button6=(Button)findViewById(R.id.x_mpin_button6);
        button7=(Button)findViewById(R.id.x_mpin_button7);
        button8=(Button)findViewById(R.id.x_mpin_button8);
        button9=(Button)findViewById(R.id.x_mpin_button9);
        buttonproceed=(Button)findViewById(R.id.x_mpin_proceed);
        buttondel=(Button)findViewById(R.id.x_mpin_buttondel);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"0";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"1";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"2";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"3";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"4";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"5";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"6";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"7";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"8";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()<4){

                    password=password+"9";
                    ++length;
                    if(length==1){

                        imageView1.setVisibility(View.VISIBLE);

                    }else if (length==2){

                        imageView2.setVisibility(View.VISIBLE);

                    }else if (length==3){

                        imageView3.setVisibility(View.VISIBLE);

                    }else if (length==4){

                        imageView4.setVisibility(View.VISIBLE);

                    }

                }

            }
        });
        buttondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.length()>0){

                    password=password.substring(0,password.length()-1);
                    --length;
                    if(length==0){

                        imageView1.setVisibility(View.INVISIBLE);

                    }else if (length==1){

                        imageView2.setVisibility(View.INVISIBLE);

                    }else if (length==2){

                        imageView3.setVisibility(View.INVISIBLE);

                    }else if (length==3){

                        imageView4.setVisibility(View.INVISIBLE);

                    }

                }

            }
        });
        forgotMpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sharedPreferences.getString("mpin",null)==null){

                    Toast.makeText(mpin.this,"You don't have any MPIN yet",Toast.LENGTH_LONG).show();

                }else {

                    Intent intent=new Intent(mpin.this,PhoneVerification.class);
                    startActivity(intent);

                }

            }
        });
    }
    public void onBackPressed(){

        moveTaskToBack(true);

    }
}