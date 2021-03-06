package com.codamasters.rolemaker.controller;

/**
 * Created by Optimus on 12/27/2014.
 */

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.ui.ChatFragment;
import com.codamasters.rolemaker.ui.LoggedActivity;
import com.codamasters.rolemaker.utils.MensajeChat;
import com.codamasters.rolemaker.utils.Parseador;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GcmIntentService extends IntentService {

    private static int num_messages=0;
    private static String gameID = "";


    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                addText(extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void addText(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Log.d("Mensaje recibido", message);

                JSONParser parser=new JSONParser();
                String userID = "";
                String receivedMessage = "";
                try {
                    Object obj = parser.parse(message);
                    JSONArray array = (JSONArray) obj;
                    userID = array.get(0).toString();
                    receivedMessage = array.get(1).toString();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }

                if(userID.equals(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("user", "nothing"))){

                    if(ChatFragment.isOpen()) {
                        ChatFragment.updateMessages(receivedMessage);
                    }
                    else{

                        SharedPreferences prefs = getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);

                        Gson gson = new Gson();

                        String json = prefs.getString("messages"+gameID, "");
                        ArrayList<MensajeChat> resultList = (ArrayList<MensajeChat>) gson.fromJson(json, ArrayList.class);
                        MensajeChat aux = Parseador.parsearMensaje(receivedMessage);
                        resultList.add(aux);
                        num_messages++;
                        Notify("Role Maker", num_messages+" New Messages");


                        json = gson.toJson(resultList);

                        prefs.edit().putString("messages"+gameID, json).apply();

                    }
                }
            }
        });
    }

    private void Notify(String notificationTitle, String notificationMessage){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")

        Notification notification = new Notification(R.drawable.abc_ab_share_pack_holo_dark,"Role Maker", System.currentTimeMillis());
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Intent notificationIntent = new Intent(this,LoggedActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setLatestEventInfo(GcmIntentService.this, notificationTitle,notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
    }

    public static void setNum_messages(int num_messages) {
        GcmIntentService.num_messages = num_messages;
    }

    public static void setGameID(String gameID) {
        GcmIntentService.gameID = gameID;
    }


}