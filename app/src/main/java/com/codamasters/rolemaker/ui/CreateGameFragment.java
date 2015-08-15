package com.codamasters.rolemaker.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmCreateGameAsyncTask;
import com.codamasters.rolemaker.controller.GcmRemoveFriendAsyncTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Julio on 27/07/2015.
 */
public class CreateGameFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    private Button bCreate;
    private EditText etGameName, etDescription, etMaxPlayers;
    private RadioGroup rgStyle;
    private RadioButton rbStyle;

    public static CreateGameFragment newInstance(String param1) {
        CreateGameFragment fragment = new CreateGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public CreateGameFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_create_game, container, false);

        etGameName = (EditText) rootView.findViewById(R.id.etGameName);
        etDescription = (EditText) rootView.findViewById(R.id.etDescription);
        etMaxPlayers = (EditText) rootView.findViewById(R.id.etMaxPlayers);
        rgStyle = (RadioGroup) rootView.findViewById(R.id.rgStyle);

        bCreate = (Button) rootView.findViewById(R.id.bCreate);

        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etGameName.getText().toString().matches("") || etMaxPlayers.getText().toString().matches("")
                        || etDescription.getText().toString().matches("")){
                    Toast.makeText(getActivity(),"Please, complete all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // get selected radio button from radioGroup
                int selectedId = rgStyle.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                rbStyle = (RadioButton) rootView.findViewById(selectedId);

                new GcmCreateGameAsyncTask(getActivity(),etGameName.getText().toString(),Integer.parseInt(etMaxPlayers.getText().toString()),
                        etDescription.getText().toString(), rbStyle.getText().toString()).execute();

                getFragmentManager().popBackStack();
            }
        });

        return rootView;
    }
}
