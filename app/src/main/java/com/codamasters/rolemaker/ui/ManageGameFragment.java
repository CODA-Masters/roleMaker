package com.codamasters.rolemaker.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmDeleteGameAsyncTask;
import com.codamasters.rolemaker.controller.GcmIntentService;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Julio on 27/07/2015.
 */
public class ManageGameFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    private static TextView game_name, master_name, num_players, description, game_style;
    private static Button bManagePlayers, bDeleteGame, bOpenChat;
    private static HashMap<String, String> game;
    private static String userName = "";
    private static ArrayList<String> participantIDs;
    PopupWindow popupMessage;
    LinearLayout layoutOfPopup;
    Button insidePopupButton, cancelButton;
    TextView popupText;


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
        bOpenChat = (Button) rootView.findViewById(R.id.bOpenChat);

        initPopUp();
        loadGame();
        return rootView;
    }

    public void loadGame(){
        game_name.setText(game.get("name"));
        master_name.setText(game.get("master"));
        num_players.setText(game.get("numPlayers") + "/" + game.get("maxPlayers"));
        description.setText(game.get("description"));
        game_style.setText(game.get("style"));

        participantIDs = new ArrayList<>();
        participantIDs.add(game.get("master"));

        JSONParser parser=new JSONParser();
        String s = game.get("players");
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            for(int i = 0; i < array.size(); i++){
                participantIDs.add(array.get(i).toString());
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }


        bOpenChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GcmIntentService.setGameID(game.get("gameID"));

                getFragmentManager().beginTransaction()
                        .addToBackStack("")
                        .replace(R.id.container, ChatFragment2.newInstance("", participantIDs, game.get("gameID")))
                        .commit();
            }
        });

        bManagePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .addToBackStack("")
                        .replace(R.id.container, ManagePlayersFragment.newInstance("", game))
                        .commit();
            }
        });
        bDeleteGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!popupMessage.isShowing()) {
                    popupMessage.showAsDropDown(bDeleteGame, 0, 0);
                }
                else{
                    popupMessage.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMessage.dismiss();
            }
        });
        insidePopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GcmDeleteGameAsyncTask(getActivity(), game.get("gameID")).execute();
                popupMessage.dismiss();
                updateList();
                getFragmentManager().popBackStack();
            }
        });
    }

    private void updateList(){
        Fragment frg = null;
        frg = this;
        final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    private void initPopUp(){
        popupText = new TextView(getActivity());
        insidePopupButton = new Button(getActivity());
        cancelButton = new Button(getActivity());
        layoutOfPopup = new LinearLayout(getActivity());
        insidePopupButton.setText("YES");
        cancelButton.setText("NO");
        popupText.setText("Are you sure you want to delete this game?");
        popupText.setPadding(0, 0, 0, 20); layoutOfPopup.setOrientation(LinearLayout.VERTICAL);
        layoutOfPopup.addView(popupText);
        layoutOfPopup.addView(insidePopupButton);
        layoutOfPopup.addView(cancelButton);

        popupMessage = new PopupWindow(layoutOfPopup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupMessage.setContentView(layoutOfPopup);

    }

}
