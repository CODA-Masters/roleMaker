package cloud_controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import gcm.backend.messaging.Messaging;

/**
 * Created by Juan on 26/07/2015.
 */
public class GcmMessageAsyncTask  extends AsyncTask<Context, Void, String> {

    private static Messaging regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;
    private String message;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "294669629340";

    public GcmMessageAsyncTask(Context context, String message) {
        this.context = context; this.message = message;
    }

    @Override
    protected String doInBackground(Context... params) {
        if (regService == null) {
            Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://role-maker.appspot.com/_ah/api/");
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
            regService.sendText(message).execute();


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
