package com.codamasters.rolemaker.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmShowFriendsAsyncTask;

import java.util.ArrayList;

/**
 * Created by Julio on 27/07/2015.
 */
public class ShowFriendsFragment extends Fragment {

    private static final String ARG_PARAM = "param1";

    // Hashmap for ListView
    private LinearLayout listContainer;
    private static ArrayList<String> resultList;

    private static ArrayAdapter<String> adapter;
    private ListView friendsListView;

    public static ShowFriendsFragment newInstance(String param1) {
        ShowFriendsFragment fragment = new ShowFriendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public ShowFriendsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_show_friends, container, false);

        listContainer = (LinearLayout) rootView.findViewById(R.id.contenedor_lista);
        friendsListView = (ListView) rootView.findViewById(R.id.friendsList);
        resultList = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(getActivity(), R.layout.friend_list_item, R.id.friend_item, resultList);

        friendsListView.setAdapter(adapter);

        String myId = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user", "nothing");


        new GcmShowFriendsAsyncTask(getActivity(), myId).execute();
        return rootView;
    }

    public static void ListUsers(ArrayList<String> users){

        resultList = users;
        adapter.clear();
        adapter.addAll(resultList);
        adapter.notifyDataSetChanged();
    }

}
