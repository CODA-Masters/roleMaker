package com.codamasters.rolemaker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.codamasters.rolemaker.controller.GcmRegistrationAsyncTask;
import com.google_cloud_app.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        //Servlet Call
        //new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Ajay Ramesh"));

        //Endpoint call
        //new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Ajay Ramesh "));

    }

    public void registerButton (View view){
        //Gcm Registration

        TextView tv = (TextView) findViewById(R.id.regName);
        String regName = tv.getText().toString();

        tv = (TextView) findViewById(R.id.regEmail);
        String regEmail = tv.getText().toString();

        tv = (TextView) findViewById(R.id.regPassword);
        String regPassword = tv.getText().toString();

        new GcmRegistrationAsyncTask(this, regName,  regEmail, regPassword).execute();
    }

    public void openChat (View view){
        Intent i = new Intent(this, ChatActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
