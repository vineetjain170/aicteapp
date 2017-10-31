package com.vscholars.stack2code.aicte_phaseone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneVerification extends AppCompatActivity{
    EditText phoneNo;
    Button submit;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.verification);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(0.8*width),(int)(0.3*height));
        phoneNo=(EditText)findViewById(R.id.verification_phoneno);
        submit=(Button)findViewById(R.id.verification_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input=phoneNo.getText().toString();
                sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
                if (input.equals(sharedPreferences.getString("phoneno",null))){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("mpin",null);
                    editor.commit();
                    Toast.makeText(PhoneVerification.this,"Please proceed to change your MPIN",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(PhoneVerification.this,mpin.class);
                    startActivity(intent);

                }else {

                    Toast.makeText(PhoneVerification.this,"Please try again phone number didn't matched",Toast.LENGTH_LONG).show();

                }

            }
        });


    }

}