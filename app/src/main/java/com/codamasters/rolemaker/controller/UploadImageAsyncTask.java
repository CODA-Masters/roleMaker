package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.codamasters.rolemaker.ui.HomeFragment;
import com.codamasters.rolemaker.utils.Constants;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Julio on 27/07/2015.
 */
public class UploadImageAsyncTask extends AsyncTask<Context, Void, String> {
    private Context context;
    private ProgressDialog pDialog;
    private Bitmap bitmap;
    private String url;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Constants.SENDER_ID;

    public UploadImageAsyncTask(Context context, Bitmap bmp) {
        this.context = context;
        this.bitmap = bmp;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading image");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Context... params) {

        String msg = "";

        // Creates Byte Array from picture
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); // Not sure whether this should be jpeg or png, try both and see which works best
        URL url = null;
        try {
            url = new URL("https://api.imgur.com/3/image");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            msg = "Error: " + e.getMessage();
        }

//encodes picture with Base64 and inserts api key
        String data = null;
        try {
            data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(Base64.encode(baos.toByteArray(), Base64.DEFAULT).toString(), "UTF-8");
            //data += "&" + URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode("0d9f6713d9e784a", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            msg = "Error: " + e.getMessage();
        }

// opens connection and sends data
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer 5d22c21f1e1d83bcc1d0169faf47b4526810f910");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                Log.d("línea:",line);
            }
            wr.close();
            rd.close();

            this.url = url.getPath();
            Log.d("Imagen subida", data);
        } catch (IOException e) {
            e.printStackTrace();
            msg = "Error: " + e.getMessage();
        }

        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        super.onPostExecute(msg);

        if (pDialog.isShowing())
            pDialog.dismiss();

        HomeFragment.setURL(url);

    }
}