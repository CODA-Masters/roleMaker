package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.codamasters.rolemaker.ui.ShowFriendsFragment;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;

import gcm.backend.registration.Registration;
import gcm.backend.registration.model.CollectionResponseUserRecord;
import gcm.backend.registration.model.UserRecord;

/**
 * Created by Julio on 27/07/2015.
 */
public class GcmShowFriendsAsyncTask extends AsyncTask<Context, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;
    private ArrayList<UserRecord> users;
    private ProgressDialog pDialog;
    private String id;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "294669629340";

    public GcmShowFriendsAsyncTask(Context context, String id) {
        this.context = context;
        this.id = id;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading friends");
        pDialog.setCancelable(false);
        pDialog.show();
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
            CollectionResponseUserRecord usersCollection = regService.listFriends(id).execute();
            users = (ArrayList)usersCollection.getItems();

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {

        if (pDialog.isShowing())
            pDialog.dismiss();

        if(users != null) {
            ShowFriendsFragment.ListUsers(users);
        }


    }
}