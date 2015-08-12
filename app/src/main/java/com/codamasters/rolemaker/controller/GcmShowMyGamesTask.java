package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.codamasters.rolemaker.ui.MyGamesFragment;
import com.codamasters.rolemaker.utils.Constants;
import com.google.android.gms.games.Game;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;

import gcm.backend.registration.Registration;
import gcm.backend.registration.model.CollectionResponseGameRecord;
import gcm.backend.registration.model.CollectionResponseUserRecord;
import gcm.backend.registration.model.GameRecord;

/**
 * Created by Julio on 27/07/2015.
 */
public class GcmShowMyGamesTask extends AsyncTask<Context, Void, String> {
    private static Registration regService = null;
    private Context context;
    private ArrayList<GameRecord> games;
    private ProgressDialog pDialog;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Constants.SENDER_ID;

    public GcmShowMyGamesTask(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading games");
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
        games = new ArrayList<>();
        try {
            String userID = PreferenceManager.getDefaultSharedPreferences(context).getString("user", "nothing");

            Log.d("Mi id",userID);
            CollectionResponseGameRecord gamesCollection = regService.listMyGames(userID).execute();
            games = (ArrayList) gamesCollection.getItems();

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

        MyGamesFragment.setGameList(games);

    }
}