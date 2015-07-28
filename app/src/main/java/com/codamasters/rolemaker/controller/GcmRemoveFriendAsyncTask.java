package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import gcm.backend.registration.Registration;

/**
 * Created by Julio on 27/07/2015.
 */
public class GcmRemoveFriendAsyncTask extends AsyncTask<Context, Void, String> {
    private static Registration regService = null;
    private Context context;
    private String removeFriendID;
    private ProgressDialog pDialog;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "294669629340";

    public GcmRemoveFriendAsyncTask(Context context, String removeFriendID) {
        this.context = context;
        this.removeFriendID = removeFriendID;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Removing friend");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Context... params) {
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://role-maker.appspot.com/_ah/api/");
            regService = builder.build();
        }

        String msg = "";
        try {
            String removeUserID = PreferenceManager.getDefaultSharedPreferences(context).getString("user", "nothing");

            Log.d("Colega a borrar:", removeFriendID);

            regService.removeFriend(removeUserID, removeFriendID).execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        super.onPostExecute(msg);

        if (pDialog.isShowing())
            pDialog.dismiss();

    }
}