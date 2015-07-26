package com.codamasters.rolemaker.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.codamasters.rolemaker.controller.GcmLoginAsyncTask;
import com.google_cloud_app.R;

/**
 * Created by Julio on 27/07/2015.
 */
public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
    }

    public void loginButton (View view){
        //Gcm Registration

        TextView tv = (TextView) findViewById(R.id.setUserName);
        String regName = tv.getText().toString();

        tv = (TextView) findViewById(R.id.setPass);
        String regPassword = tv.getText().toString();

        new GcmLoginAsyncTask(this, regName, regPassword).execute();
    }
}
