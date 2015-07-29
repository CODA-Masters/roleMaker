package com.codamasters.rolemaker.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmMessageAsyncTask;
import com.codamasters.rolemaker.utils.ObjectSerializer;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

import gcm.backend.registration.Registration;
import gcm.backend.registration.model.UserRecord;

/**
 * Created by Juan on 26/07/2015.
 */
public class ChatFragment extends Fragment {

    private static final String ARG_PARAM = "param1";

    // Hashmap for ListView
    private LinearLayout listContainer;
    private static ArrayList<String> resultList;
    private static ArrayAdapter<String> adapter;
    private ListView lv;
    private Button bSend;
    private TextView tv;
    private SharedPreferences prefs;

    private static boolean open;

    public static ChatFragment newInstance(String param1) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public ChatFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_chat, container, false);

        listContainer = (LinearLayout) rootView.findViewById(R.id.contenedor_lista);
        lv = (ListView) rootView.findViewById(R.id.list);

        //      load tasks from preference
        prefs = getActivity().getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);

        resultList = new ArrayList<String>();

        try {
            resultList = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("messages", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv = (TextView) rootView.findViewById(R.id.textMessage);
        bSend= (Button)rootView.findViewById(R.id.sendButton);
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendButton(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.content, resultList);

        lv.setAdapter(adapter);

        open=true;
        return rootView;
    }

    public void sendButton (View view) throws JSONException, IOException {
        String message = tv.getText().toString();
        String username = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("username", "nothing");

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("message",message);
        jsonObj.put("username", username);
        jsonObj.put("date",new Date());
        StringWriter out = new StringWriter();
        jsonObj.writeJSONString(out);
        String jsonText = out.toString();

        new GcmMessageAsyncTask(getActivity(), jsonText).execute();

    }

    public static void updateMessages(String message) {

        resultList.add(message);
        adapter.add(message);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onPause(){
        super.onPause();

        // Guardamos los datos del chat

        prefs = getActivity().getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("messages", ObjectSerializer.serialize(resultList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();

        open=false;
    }

    @Override
    public void onResume(){
        super.onResume();

        prefs = getActivity().getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);

        try {
            resultList = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("messages", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
        open=true;

    }

    public static boolean isOpen() {
        return open;
    }


}
