package com.codamasters.rolemaker.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.codamasters.rolemaker.utils.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;

import gcm.backend.messaging.Messaging;
import gcm.backend.registration.Registration;
import gcm.backend.registration.model.UserRecord;

/**
 * Created by Juan on 26/07/2015.
 */
public class GcmSendMessageAsyncTask extends AsyncTask<Context, Void, String> {

    private static Messaging mesService = null;
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;
    private String message;
    private String userIds;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Constants.SENDER_ID;

    public GcmSendMessageAsyncTask(Context context, String message, String userIds) {
        this.context = context; this.message = message;
        this.userIds = userIds;
    }

    @Override
    protected String doInBackground(Context... params) {
        if (mesService == null) {
            Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(Constants.SERVER_URL);
            mesService = builder.build();
        }

        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(Constants.SERVER_URL);
            regService = builder.build();
        }

        String msg = "";

        try {

            /*
            gcm = GoogleCloudMessaging.getInstance(context);
            AtomicInteger msgId = new AtomicInteger();
            String id = Integer.toString(msgId.incrementAndGet());
            Bundle data = new Bundle();

            data.putString("message", message);
        // "time to live" parameter
        // This is optional. It specifies a value in seconds up to 24 hours.
            int ttl = 86400; // 24 hours;

            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, ttl, data);
            */

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.

            mesService.sendTextToUsers(message, userIds).execute();


        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
    }
}
