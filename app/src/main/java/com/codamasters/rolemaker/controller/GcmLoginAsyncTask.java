package com.codamasters.rolemaker.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.codamasters.rolemaker.ui.LoggedActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import gcm.backend.registration.Registration;
import gcm.backend.registration.model.UserRecord;


/**
 * Created by Optimus on 12/27/2014.
 */
public class GcmLoginAsyncTask extends AsyncTask<Context, Void, String> {
    private static Registration regService = null;
    private Context context;
    private String regName, regPassword;
    private String error;
    private GoogleCloudMessaging gcm;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "294669629340";

    public GcmLoginAsyncTask(Context context, String regName, String regPassword) {
        this.context = context; this.regName = regName; this.regPassword = regPassword;
    }

    @Override
    protected String doInBackground(Context... params) {
        if (regService == null) {
            /*Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                    // otherwise they can be skipped
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            */// end of optional local run code
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://role-maker.appspot.com/_ah/api/");
            regService = builder.build();
        }

        String msg = "";
        try {
            UserRecord user = regService.login(regName, regPassword).execute();

            if(user != null){
                Intent i = new Intent(this.context, LoggedActivity.class);
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("user", user.getId().toString()).commit();
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("username", user.getName().toString()).commit();

                // Actualizar id de GCM
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                String regId = gcm.register(SENDER_ID);
                regService.updateGCM(user.getId().toString(),regId).execute();

                this.context.startActivity(i);
            }
            else{
                error = "Incorrect user or password";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        if (error != null){
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        }
    }
}