package com.codamasters.rolemaker.controller;

/**
 * Created by Optimus on 12/27/2014.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.ArrayList;

/**
 * Created by Optimus on 12/26/2014.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    protected static ArrayList<String> auxMessages;

    public GcmBroadcastReceiver(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),GcmIntentService.class.getName());

        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }

    public static ArrayList<String> getAuxMessages() {
        return auxMessages;
    }

    public static void setAuxMessages(ArrayList<String> auxMessages) {
        GcmBroadcastReceiver.auxMessages = auxMessages;
    }

}