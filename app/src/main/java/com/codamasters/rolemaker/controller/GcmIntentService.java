package com.codamasters.rolemaker.controller;

/**
 * Created by Optimus on 12/27/2014.
 */

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.codamasters.rolemaker.controller.GcmBroadcastReceiver;
import com.codamasters.rolemaker.ui.ChatActivity;
import com.codamasters.rolemaker.utils.ObjectSerializer;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GcmIntentService extends IntentService {

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
                if(ChatActivity.isOpen())
                    ChatActivity.updateMessages(message);
                else{

                    SharedPreferences prefs = getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);

                    try {
                        ArrayList<String> resultList = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("messages", ObjectSerializer.serialize(new ArrayList<String>())));
                        resultList.add(message);

                        SharedPreferences.Editor editor = prefs.edit();
                        try {
                            editor.putString("messages", ObjectSerializer.serialize(resultList));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        editor.commit();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}