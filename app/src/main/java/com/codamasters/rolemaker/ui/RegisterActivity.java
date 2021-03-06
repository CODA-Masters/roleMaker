package com.codamasters.rolemaker.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.codamasters.rolemaker.controller.GcmRegistrationAsyncTask;
import com.codamasters.rolemaker.R;

import java.util.concurrent.ExecutionException;


public class RegisterActivity extends ActionBarActivity {

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

        try {
            String msg=new GcmRegistrationAsyncTask(this, regName,  regEmail, regPassword).execute().get();
            if(msg!=null)
                finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
