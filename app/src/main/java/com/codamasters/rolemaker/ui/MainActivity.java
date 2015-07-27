package com.codamasters.rolemaker.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google_cloud_app.R;


public class MainActivity extends Activity implements OnClickListener {

    Button btnSignIn;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btnSingIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        TextView tv = (TextView) findViewById(R.id.titleLogin);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/arcader.TTF");
        tv.setTypeface(face);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch(v.getId()){
            case R.id.btnSingIn:
                i = new Intent(this,LoginActivity.class);
                break;
            case R.id.btnSignUp:
                i = new Intent(this,RegisterActivity.class);
                break;
        }
        startActivity(i);
    }



}