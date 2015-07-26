package com.google_cloud_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;



import cloud_controller.GcmMessageAsyncTask;

/**
 * Created by Juan on 26/07/2015.
 */
public class ChatActivity extends ActionBarActivity {

    // Hashmap for ListView
    private LinearLayout listContainer;
    private static ArrayList<String> resultList;
    private static ArrayAdapter<String> adapter;
    private ListView lv;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listContainer = (LinearLayout) findViewById(R.id.contenedor_lista);
        lv = (ListView) findViewById(R.id.list);

        //      load tasks from preference
        SharedPreferences prefs = getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);

        try {
            resultList = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("messages", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.content, resultList);

        lv.setAdapter(adapter);

    }

    public void sendButton (View view){

        TextView tv = (TextView) findViewById(R.id.textMessage);
        String message = tv.getText().toString();

        new GcmMessageAsyncTask(this, message).execute();

    }

    public static void updateMessages(String message) {
        resultList.add(message);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onPause(){
        super.onPause();

        // Guardamos los datos del chat

        SharedPreferences prefs = getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("messages", ObjectSerializer.serialize(resultList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();

    }


}
