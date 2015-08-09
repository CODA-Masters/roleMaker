package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.codamasters.rolemaker.utils.Constants;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import gcm.backend.registration.Registration;

/**
 * Created by Julio on 27/07/2015.
 */
public class GcmCreateGameAsyncTask extends AsyncTask<Context, Void, String> {
    private static Registration regService = null;
    private Context context;
    private String name, description, style;
    private int maxPlayers;
    private ProgressDialog pDialog;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Constants.SENDER_ID;

    public GcmCreateGameAsyncTask(Context context, String name, int maxPlayers, String description, String style) {
        this.context = context;
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.description = description;
        this.style = style;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Creating game");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Context... params) {
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(Constants.SERVER_URL);
            regService = builder.build();
        }

        String msg = "";
        try {
            String master = PreferenceManager.getDefaultSharedPreferences(context).getString("user", "nothing");

            regService.createGame(name, master, maxPlayers, description, style).execute();

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