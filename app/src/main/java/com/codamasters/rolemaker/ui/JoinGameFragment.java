package com.codamasters.rolemaker.ui;

import android.content.Context;
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
import com.codamasters.rolemaker.controller.GcmJoinGameAsyncTask;
import com.codamasters.rolemaker.controller.GcmShowGamesAsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

import gcm.backend.registration.model.GameRecord;

/**
 * Created by Julio on 10/08/2015.
 */
public class JoinGameFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    // Hashmap for ListView
    private static ArrayList<HashMap<String, String> > resultList;  // Array que almacena los registros de partidas
    // Cada registro de partida está representado por un Hashmap que incluye los distintos campos del mismo.

    private static ArrayList<Boolean> requestSentList, joinedList;
    private static String userID;
    private static ArrayList<String> masterNames;

    private static PostAdapter adapter;
    private ListView lv;

    public static JoinGameFragment newInstance(String param1) {
        JoinGameFragment fragment = new JoinGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public JoinGameFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_join_game, container, false);

        lv = (ListView) rootView.findViewById(R.id.gameList);
        resultList = new ArrayList<HashMap<String, String>>();
        masterNames = new ArrayList<>();


        requestSentList = new ArrayList<>();
        joinedList = new ArrayList<>();

        adapter = new PostAdapter(getActivity());

        lv.setAdapter(adapter);

        userID = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user", "nothing");

        new GcmShowGamesAsyncTask(getActivity(),userID).execute();


        return rootView;
    }

    public static void setGameList(ArrayList<GameRecord> gameList){
        for (int i = 0; i < gameList.size(); i++){
            boolean requestSent = false;
            boolean joined = false;
            HashMap<String, String> aux = new HashMap<>();

            aux.put("gameID", gameList.get(i).getId().toString());
            aux.put("name", gameList.get(i).getName());
            aux.put("master", gameList.get(i).getMaster());
            aux.put("numPlayers", gameList.get(i).getNumPlayers().toString());
            aux.put("maxPlayers", gameList.get(i).getMaxPlayers().toString());
            aux.put("description", gameList.get(i).getDescription());
            aux.put("style", gameList.get(i).getStyle());
            aux.put("pendingPlayers", gameList.get(i).getPendingPlayers());
            aux.put("masterName",masterNames.get(i));

            JSONParser parser=new JSONParser();
            String s = gameList.get(i).getPendingPlayers();

            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                for(int j = 0; j < array.size(); j++){
                    if(array.get(j).toString().equals(userID)){
                        requestSent = true;
                        requestSentList.add(true);
                        break;
                    }
                }
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            s = gameList.get(i).getPlayers();

            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                for(int j = 0; j < array.size(); j++){
                    if(array.get(j).toString().equals(userID)){
                        joined = true;
                        joinedList.add(true);
                        break;
                    }
                }
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            if(!requestSent){
                requestSentList.add(false);
            }

            if(!joined){
                joinedList.add(false);
            }

            resultList.add(aux);

        }

        adapter.notifyDataSetChanged();
    }

    public static void setMasterNames(ArrayList<String> m){
        masterNames = m;
    }

    public void updateList(){

        Fragment frg = null;
        frg = this;
        final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }


    /**
     * @author Alexito
     */
    public class PostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView game_name, master_name, num_players, description, game_style;
            Button joinButton;

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

            Log.d("Tamaño de la lista",resultList.size()+"");

            Log.v(TAG, "in getView for position " + position + ", convertView is "
                    + ((convertView == null) ? "null" : "being recycled"));

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.game_list_item, null);

                holder = new ViewHolder();

                holder.game_name = (TextView) convertView
                        .findViewById(R.id.game_name);
                holder.master_name = (TextView) convertView
                        .findViewById(R.id.master_name);
                holder.num_players = (TextView) convertView
                        .findViewById(R.id.num_players);
                holder.description = (TextView) convertView
                        .findViewById(R.id.description);
                holder.game_style = (TextView) convertView
                        .findViewById(R.id.game_style);
                holder.joinButton = (Button) convertView.findViewById(R.id.joinButton);

                if(requestSentList.get(position)) {
                    holder.joinButton.setText("Join request sent");
                    holder.joinButton.setEnabled(false);

                }

                else if(joinedList.get(position)) {
                    holder.joinButton.setText("Already joined");
                    holder.joinButton.setEnabled(false);

                }

                else if(Integer.parseInt(resultList.get(position).get("numPlayers")) >= Integer.parseInt(resultList.get(position).get("maxPlayers"))){
                    holder.joinButton.setText("Full");
                    holder.joinButton.setEnabled(false);
                }

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();

            // Setting all values in listview
            holder.game_name.setText(resultList.get(position).get("name"));
            holder.master_name.setText(resultList.get(position).get("masterName"));
            holder.num_players.setText(resultList.get(position).get("numPlayers")+
                    "/" + resultList.get(position).get("maxPlayers"));
                holder.game_style.setText(resultList.get(position).get("style"));
                holder.description.setText(resultList.get(position).get("description"));
            if(!requestSentList.get(position)) {
                holder.joinButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new GcmJoinGameAsyncTask(getActivity(), resultList.get(position).get("gameID")).execute();
                        updateList();
                    }
                });
            }


            return convertView;
        }


    }

}
