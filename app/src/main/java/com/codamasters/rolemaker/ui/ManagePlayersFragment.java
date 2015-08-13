package com.codamasters.rolemaker.ui;

import android.content.Context;
import android.os.Bundle;
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
import com.codamasters.rolemaker.controller.GcmAcceptFriendRequestAsyncTask;
import com.codamasters.rolemaker.controller.GcmDenyFriendRequestAsyncTask;
import com.codamasters.rolemaker.controller.GcmShowPlayersAsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Julio on 27/07/2015.
 */
public class ManagePlayersFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    private static TextView tvNumPlayers;
    private static Button bInvitePlayer;
    private static HashMap<String, String> game;
    public static ArrayList<String> players, pendingPlayers;
    public static ArrayList<String> playerIDs, pendingPlayerIDs;
    private static PendingPostAdapter adapter1;
    private static PlayerPostAdapter adapter2;
    private static ListView lv1,lv2;


    public static ManagePlayersFragment newInstance(String param1, HashMap<String, String> g) {
        ManagePlayersFragment fragment = new ManagePlayersFragment();
        Bundle args = new Bundle();
        game = g;
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public ManagePlayersFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_manage_players, container, false);
        lv1 = (ListView) rootView.findViewById(R.id.pendingPlayersList);
        lv2 = (ListView) rootView.findViewById(R.id.playersList);

        players = new ArrayList<>();
        pendingPlayers = new ArrayList<>();
        playerIDs = new ArrayList<>();
        pendingPlayerIDs = new ArrayList<>();

        adapter1 = new PendingPostAdapter(getActivity());
        adapter2 = new PlayerPostAdapter(getActivity());

        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);

        new GcmShowPlayersAsyncTask(getActivity(),game.get("gameID")).execute();

        tvNumPlayers = (TextView) rootView.findViewById(R.id.tvNumPlayers);
        bInvitePlayer = (Button) rootView.findViewById(R.id.bInvitePlayer);

        loadGame();
        return rootView;
    }

    public void loadGame(){

        tvNumPlayers.setText("Players: " + game.get("numPlayers") + "/" + game.get("maxPlayers"));

        bInvitePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void loadPlayers(ArrayList<String> pNames, ArrayList<String> pIDs, ArrayList<String> ppNames, ArrayList<String> ppIDs){
        players = pNames;
        pendingPlayers = ppNames;
        playerIDs = pIDs;
        pendingPlayerIDs = ppIDs;
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
    }


    public class PendingPostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView user_item;
            Button showProfile;
            Button acceptPlayerButton;
            Button denyPlayerButton;

        }

        private static final String TAG = "CustomAdapter";

        private LayoutInflater inflater = null;

        public PendingPostAdapter(Context c) {
            Log.v(TAG, "Constructing CustomAdapter");

            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            Log.v(TAG, "in getCount()");
            return pendingPlayers.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG, "in getItem() for position " + position);
            return pendingPlayers.get(position);
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

            Log.v(TAG, "in getView for position " + position + ", convertView is "
                    + ((convertView == null) ? "null" : "being recycled"));

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.pending_player_list_item, null);

                holder = new ViewHolder();

                holder.user_item = (TextView) convertView.findViewById(R.id.user_item);
                holder.showProfile = (Button) convertView.findViewById(R.id.showProfile);
                holder.acceptPlayerButton = (Button) convertView.findViewById(R.id.acceptPlayerButton);
                holder.denyPlayerButton = (Button) convertView.findViewById(R.id.denyPlayerButton);

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();

            // Setting all values in listview
            holder.user_item.setText(pendingPlayers.get(position));
            holder.showProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileFragment.setUserName(pendingPlayers.get(position));
                    getFragmentManager().beginTransaction()
                            .addToBackStack("")
                            .replace(R.id.container, ProfileFragment.newInstance(""))
                            .commit();
                }
            });

            holder.acceptPlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerID = pendingPlayerIDs.get(position);
                    //new GcmAcceptPlayerRequestAsyncTask(getActivity(), friendID).execute();
                }
            });
            holder.denyPlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerID = pendingPlayerIDs.get(position);
                    //new GcmDenyPlayerRequestAsyncTask(getActivity(), friendID).execute();
                }
            });

            return convertView;
        }
    }

    public class PlayerPostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView user_item;
            Button showProfile;
            Button kick;

        }

        private static final String TAG = "CustomAdapter";

        private LayoutInflater inflater = null;

        public PlayerPostAdapter(Context c) {
            Log.v(TAG, "Constructing CustomAdapter");

            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            Log.v(TAG, "in getCount()");
            return players.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG, "in getItem() for position " + position);
            return players.get(position);
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

            Log.v(TAG, "in getView for position " + position + ", convertView is "
                    + ((convertView == null) ? "null" : "being recycled"));

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.player_list_item, null);

                holder = new ViewHolder();

                holder.user_item = (TextView) convertView.findViewById(R.id.user_item);
                holder.showProfile = (Button) convertView.findViewById(R.id.showProfile);
                holder.kick = (Button) convertView.findViewById(R.id.kickPlayerButton);

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();

            // Setting all values in listview
            holder.user_item.setText(players.get(position));
            holder.showProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileFragment.setUserName(players.get(position));
                    getFragmentManager().beginTransaction()
                            .addToBackStack("")
                            .replace(R.id.container, ProfileFragment.newInstance(""))
                            .commit();
                }
            });

            holder.kick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerID = playerIDs.get(position);
                    //new GcmDenyPlayerRequestAsyncTask(getActivity(), friendID).execute();
                }
            });

            return convertView;
        }
    }
}
