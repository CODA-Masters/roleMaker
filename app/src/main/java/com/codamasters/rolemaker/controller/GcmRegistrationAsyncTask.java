package com.codamasters.rolemaker.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.codamasters.rolemaker.utils.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import gcm.backend.registration.Registration;

/**
 * Created by Optimus on 12/27/2014.
 */
public class GcmRegistrationAsyncTask extends AsyncTask<Context, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;
    private String regName, regEmail, regPassword;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Constants.SENDER_ID;

    public GcmRegistrationAsyncTask(Context context, String regName, String regEmail, String regPassword) {
        this.context = context; this.regName = regName; this.regEmail = regEmail; this.regPassword = regPassword;
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
                    .setRootUrl(Constants.SERVER_URL);
            regService = builder.build();
        }

        String msg;
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }


            String regId = gcm.register(SENDER_ID);
            msg = "Usuario '" + regName + "' registrado con éxito.";

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            regService.register(regName, regId, regEmail, regPassword).execute();


        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }
}