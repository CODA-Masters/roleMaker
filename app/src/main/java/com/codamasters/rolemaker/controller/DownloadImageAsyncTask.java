package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.codamasters.rolemaker.ui.HomeFragment;
import com.codamasters.rolemaker.ui.ProfileFragment;
import com.codamasters.rolemaker.utils.Constants;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import gcm.backend.registration.Registration;

/**
 * Created by Julio on 27/07/2015.
 */
public class DownloadImageAsyncTask extends AsyncTask<Context, Void, String> {
    private Context context;
    private Bitmap bmp;
    private String url;
    private int callerID; // Identificador de clase que lo llama. Según el cual llamará a un método u otro al terminar

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Constants.SENDER_ID;

    public DownloadImageAsyncTask(Context context, String url, int callerID) {
        this.context = context;
        this.url = url;
        this.callerID = callerID;
    }

    @Override
    protected String doInBackground(Context... params) {

        String msg = "";
        URL url = null;
        try {
            url = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            msg = "Error: " + e.getMessage();
        }

        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            msg = "Error: " + e.getMessage();
        }

        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        super.onPostExecute(msg);

        switch (callerID){
            case 0: HomeFragment.setImage(bmp); break;
            case 1: ProfileFragment.setImage(bmp);
        }

    }
}