package com.codamasters.rolemaker.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codamasters.rolemaker.R;

import java.util.HashMap;


/**
 * Created by Julio on 27/07/2015.
 */
public class ManageGameFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    private static TextView game_name, master_name, num_players, description, game_style;
    private static Button bManagePlayers, bDeleteGame;
    private static HashMap<String, String> game;
    private static String userName = "";


    public static ManageGameFragment newInstance(String param1, HashMap<String, String> g) {
        ManageGameFragment fragment = new ManageGameFragment();
        Bundle args = new Bundle();
        game = g;
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public ManageGameFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_manage_game, container, false);

        game_name = (TextView) rootView.findViewById(R.id.game_name);
        master_name = (TextView) rootView.findViewById(R.id.master_name);
        num_players = (TextView) rootView.findViewById(R.id.num_players);
        description = (TextView) rootView.findViewById(R.id.description);
        game_style = (TextView) rootView.findViewById(R.id.game_style);
        bManagePlayers = (Button) rootView.findViewById(R.id.bManagePlayers);
        bDeleteGame = (Button) rootView.findViewById(R.id.bDeleteGame);

        loadGame();
        return rootView;
    }

    public void loadGame(){
        game_name.setText(game.get("name"));
        master_name.setText(game.get("master"));
        num_players.setText(game.get("numPlayers") + "/" + game.get("maxPlayers"));
        description.setText(game.get("description"));
        game_style.setText(game.get("style"));

        bManagePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .addToBackStack("")
                        .replace(R.id.container, ManagePlayersFragment.newInstance("",game))
                        .commit();
            }
        });
        bDeleteGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
