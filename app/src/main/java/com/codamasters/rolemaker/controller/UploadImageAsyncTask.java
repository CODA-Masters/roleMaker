package com.codamasters.rolemaker.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.codamasters.rolemaker.ui.HomeFragment;
import com.codamasters.rolemaker.utils.Constants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        pDialog.setMessage("Uploading image");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Context... params) {

        String IMGUR_POST_URI = "https://api.imgur.com/3/upload";
        String IMGUR_API_KEY = "0d9f6713d9e784a";

        String msg = "";

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.out.println("Writing image...");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            URL url = new URL(IMGUR_POST_URI);
            System.out.println("Encoding...");
            String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
            data += "&" + URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(IMGUR_API_KEY, "UTF-8");

            System.out.println("Connecting...");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Authorization", "Client-ID " + IMGUR_API_KEY);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            System.out.println("Sending data...");
            wr.write(data);
            wr.flush();

            System.out.println("Finished.");

            //just display the raw response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line, response;
            response = "";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                response = line;
            }
            // Leemos la respuesta, una línea codificada en JSON:
            JSONParser parser=new JSONParser();
            try {
                Object obj = parser.parse(response);
                JSONObject jsonObj = (JSONObject) obj;
                JSONObject jData = (JSONObject)jsonObj.get("data");
                this.url = (String)jData.get("link");
                Log.d("Enlace obtenido",this.url);
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            in.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            msg = e.getMessage();
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