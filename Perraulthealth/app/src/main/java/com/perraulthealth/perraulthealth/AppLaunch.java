package com.perraulthealth.perraulthealth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.app.ProgressDialog;

public class AppLaunch extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //progressDialog= new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launch);

        //progressDialog.setMessage("Loading data, please wait...");
       // progressDialog.show();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(AppLaunch.this, ConsumerSignupActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}

