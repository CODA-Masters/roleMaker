package com.codamasters.rolemaker.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmMessageAsyncTask;
import com.codamasters.rolemaker.utils.MensajeChat;
import com.codamasters.rolemaker.utils.ObjectSerializer;
import com.codamasters.rolemaker.utils.Parseador;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Juan on 26/07/2015.
 */
public class ChatFragment extends Fragment {

    private static final String ARG_PARAM = "param1";

    // Hashmap for ListView
    private static ArrayList<MensajeChat> listaMensajes;
    private static PostAdapter adapter;
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

        lv = (ListView) rootView.findViewById(R.id.list);

        //      load tasks from preference
        prefs = getActivity().getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("messages", "");
        if(json == ""){
            listaMensajes = new ArrayList<MensajeChat>();
        }
        else{
            Type type = new TypeToken<ArrayList<MensajeChat>>(){}.getType();
            listaMensajes = (ArrayList<MensajeChat>) gson.fromJson(json, type);
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

        adapter = new PostAdapter(getActivity());


        lv.setAdapter(adapter);

        open=true;
        return rootView;
    }

    public void sendButton (View view) throws JSONException, IOException {
        String message = tv.getText().toString();
        String username = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("username", "nothing");

        String DATE_FORMAT_NOW = "HH:mm:ss dd/mm/yyy";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String stringDate = sdf.format(date );


        JSONObject jsonObj = new JSONObject();
        jsonObj.put("message",message);
        jsonObj.put("username", username);
        jsonObj.put("date",stringDate.toString());
        StringWriter out = new StringWriter();
        jsonObj.writeJSONString(out);
        String jsonText = out.toString();

        new GcmMessageAsyncTask(getActivity(), jsonText).execute();
        tv.setText("");
    }

    public static void updateMessages(String message) {

        listaMensajes.add(Parseador.parsearMensaje(message));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onPause(){
        super.onPause();

        // Guardamos los datos del chat

        prefs = getActivity().getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(listaMensajes);

        Log.d("Mensajes serializados",json+"");
        prefs.edit().putString("messages", json).apply();

        open=false;
    }

    @Override
    public void onResume(){
        super.onResume();

        prefs = getActivity().getSharedPreferences("SHARED_MESSAGES", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("messages", "");
        if(listaMensajes.size() > 0) {
            Type type = new TypeToken<ArrayList<MensajeChat>>(){}.getType();
            listaMensajes = (ArrayList<MensajeChat>) gson.fromJson(json, type);
        }

        adapter.notifyDataSetChanged();
        open=true;

    }

    public static boolean isOpen() {
        return open;
    }
    /**
     * @author Alexito
     */
    public class PostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView message,fecha,usuario;
        }

        private static final String TAG = "CustomAdapter";

        private LayoutInflater inflater = null;

        public PostAdapter(Context c) {
            Log.v(TAG, "Constructing CustomAdapter");

            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            Log.v(TAG, "in getCount()");
            return listaMensajes.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG, "in getItem() for position " + position);
            return listaMensajes.get(position);
        }

        @Override
        public long getItemId(int position) {
            Log.v(TAG, "in getItemId() for position " + position);
            return position;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            MensajeChat mc= listaMensajes.get(position);
            
            String username = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("username", "nothing");
                if(username.equals(mc.getUser()))
                    convertView = inflater.inflate(R.layout.list_item_me, null);
                else
                    convertView = inflater.inflate(R.layout.list_item, null);

                holder = new ViewHolder();

                holder.message = (TextView) convertView
                        .findViewById(R.id.content);
                holder.fecha = (TextView) convertView
                        .findViewById(R.id.tvFecha);
                holder.usuario = (TextView) convertView
                        .findViewById(R.id.tvUser);


            // Setting all values in listview
            holder.message.setText(mc.getMensaje());
            if(!username.equals(mc.getUser()))
                holder.usuario.setText(mc.getUser());
            else
                holder.usuario.setText(getString(R.string.me));
            Date date = mc.getFecha();
            String DATE_FORMAT_NOW = "HH:mm:ss dd/mm/yyy";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            holder.fecha.setText(sdf.format(date).toString());

            return convertView;
        }

    }

}
