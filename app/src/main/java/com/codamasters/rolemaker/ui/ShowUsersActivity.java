package com.codamasters.rolemaker.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.codamasters.rolemaker.controller.GcmMessageAsyncTask;
import com.codamasters.rolemaker.utils.ObjectSerializer;
import com.google_cloud_app.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gcm.backend.registration.model.UserRecord;

/**
 * Created by Julio on 27/07/2015.
 */
public class ShowUsersActivity extends ActionBarActivity {

    // Hashmap for ListView
    private LinearLayout listContainer;
    private static ArrayList<String> resultList;
    private static ArrayAdapter<String> adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

        listContainer = (LinearLayout) findViewById(R.id.contenedor_lista);
        lv = (ListView) findViewById(R.id.list);
        resultList = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(this, R.layout.user_list_item, R.id.user_item, resultList);

        lv.setAdapter(adapter);

    }

    public static void ListUsers(ArrayList<UserRecord> users){
        ArrayList<String> usernames = new ArrayList<String>();
        for(int i = 0; i < users.size(); i++){
            usernames.add(users.get(i).getName());
        }
        resultList = usernames;
        adapter.clear();
        adapter.addAll(resultList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }


}
