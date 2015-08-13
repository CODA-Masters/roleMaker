package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.codamasters.rolemaker.ui.ManagePlayersFragment;
import com.codamasters.rolemaker.ui.ShowFriendRequestsFragment;
import com.codamasters.rolemaker.utils.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;

import gcm.backend.registration.Registration;
import gcm.backend.registration.model.GameRecord;
import gcm.backend.registration.model.UserRecord;

/**
 * Created by Julio on 27/07/2015.
 */
public class GcmShowPlayersAsyncTask extends AsyncTask<Context, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;
    private ProgressDialog pDialog;
    private String gameID;
    private  ArrayList<String> pendingPlayerNames, playerNames;
    private  ArrayList<String> pendingPlayerIDs, playerIDs;


    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Constants.SENDER_ID;

    public GcmShowPlayersAsyncTask(Context context, String gameID) {
        this.context = context;
        this.gameID = gameID;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading players");
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
                    .setRootUrl(Constants.SERVER_URL);
            regService = builder.build();
        }

        String msg = "";
        try {
            GameRecord game = regService.findPartida(gameID).execute();
            pendingPlayerNames = new ArrayList();
            playerNames = new ArrayList();
            pendingPlayerIDs = new ArrayList();
            playerIDs = new ArrayList();

            JSONParser parser=new JSONParser();
            String s = game.getPendingPlayers();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                UserRecord friend;
                Long id;
                for(int i = 0; i < array.size(); i++){
                    id = Long.parseLong((String) array.get(i));
                    friend = regService.findRecord(id).execute();
                    pendingPlayerNames.add(friend.getName());
                    pendingPlayerIDs.add(friend.getId().toString());
                }
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            s = game.getPlayers();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                UserRecord friend;
                Long id;
                for(int i = 0; i < array.size(); i++){
                    id = Long.parseLong((String) array.get(i));
                    friend = regService.findRecord(id).execute();
                    playerNames.add(friend.getName());
                    playerIDs.add(friend.getId().toString());
                }
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

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

        ManagePlayersFragment.loadPlayers(playerNames, playerIDs, pendingPlayerNames, pendingPlayerIDs);

        regService = null;


    }
}