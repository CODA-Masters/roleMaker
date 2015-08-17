package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.codamasters.rolemaker.ui.JoinGameFragment;
import com.codamasters.rolemaker.utils.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;

import gcm.backend.registration.model.GameRecord;
import gcm.backend.registration.Registration;
import gcm.backend.registration.model.CollectionResponseGameRecord;

/**
 * Created by Julio on 27/07/2015.
 */
public class GcmShowGamesAsyncTask extends AsyncTask<Context, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;
    private String userID;
    private ArrayList<GameRecord> games;
    private ArrayList<String> masterNames;
    private ProgressDialog pDialog;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Constants.SENDER_ID;

    public GcmShowGamesAsyncTask(Context context, String userID) {
        this.context = context;
        this.userID = userID;
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

            CollectionResponseGameRecord gamesCollection = regService.joinGames(userID).execute();
            games = (ArrayList)gamesCollection.getItems();
            masterNames = new ArrayList<>();
            for(GameRecord game : games){
                masterNames.add(regService.findRecord(Long.parseLong(game.getMaster())).execute().getName());
            }

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

        JoinGameFragment.setMasterNames(masterNames);
        JoinGameFragment.setGameList(games);




    }
}