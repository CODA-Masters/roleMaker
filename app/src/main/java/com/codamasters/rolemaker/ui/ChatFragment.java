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

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Juan on 26/07/2015.
 */
public class ChatFragment extends Fragment {

    private static final String ARG_PARAM = "param1";

    // Hashmap for ListView
    private static ArrayList<String> resultList;
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

        resultList = new ArrayList<String>();
        listaMensajes = new ArrayList<>();

        try {
            resultList = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("messages", ObjectSerializer.serialize(new ArrayList<String>())));
            listaMensajes = Parseador.parsearListaMensajes(resultList);
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

        //adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.content, resultList);
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

        resultList.add(message);
        listaMensajes.add(Parseador.parsearMensaje(message));
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
            listaMensajes = Parseador.parsearListaMensajes(resultList);
        } catch (IOException e) {
            e.printStackTrace();
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
            return resultList.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG, "in getItem() for position " + position);
            return resultList.get(position);
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
            Log.v(TAG, "in getView for position " + position + ", convertView is "
                    + ((convertView == null) ? "null" : "being recycled"));
            String username = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("username", "nothing");
            if (convertView == null) {
                if(!username.equals(mc.getUser()))
                    convertView = inflater.inflate(R.layout.list_item, null);
                else
                    convertView = inflater.inflate(R.layout.list_item_me, null);

                holder = new ViewHolder();

                holder.message = (TextView) convertView
                        .findViewById(R.id.content);
                holder.fecha = (TextView) convertView
                        .findViewById(R.id.tvFecha);
                holder.usuario = (TextView) convertView
                        .findViewById(R.id.tvUser);

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();
            // Setting all values in listview
            holder.message.setText(mc.getMensaje());
            if(!username.equals(mc.getUser()))
                holder.usuario.setText(mc.getUser());

            Date date = mc.getFecha();
            String DATE_FORMAT_NOW = "HH:mm:ss dd/mm/yyy";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            holder.fecha.setText(sdf.format(date).toString());

            return convertView;
        }

    }

}
