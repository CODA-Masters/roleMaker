package com.codamasters.rolemaker.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.codamasters.rolemaker.controller.GcmShowUsersAsyncTask;
import com.google_cloud_app.R;

import java.util.ArrayList;

import gcm.backend.registration.model.UserRecord;

/**
 * Created by Julio on 27/07/2015.
 */
public class ShowUsersFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    // Hashmap for ListView
    private LinearLayout listContainer;
    private static ArrayList<String> resultList;
    private static ArrayList<String> requestList;

    private static ArrayAdapter<String> adapter;
    private ListView searchListView;
    private ListView requestListView;

    public static ShowUsersFragment newInstance(String param1) {
        ShowUsersFragment fragment = new ShowUsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public ShowUsersFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_show_users, container, false);

        listContainer = (LinearLayout) rootView.findViewById(R.id.contenedor_lista);
        searchListView = (ListView) rootView.findViewById(R.id.searchList);
        requestListView = (ListView)rootView.findViewById(R.id.requestList);
        resultList = new ArrayList<String>();
        requestList = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(getActivity(), R.layout.user_list_item, R.id.user_item, resultList);

        searchListView.setAdapter(adapter);

        new GcmShowUsersAsyncTask(getActivity()).execute();
        return rootView;
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

}
