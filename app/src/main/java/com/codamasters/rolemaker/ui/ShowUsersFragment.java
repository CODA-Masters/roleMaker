package com.codamasters.rolemaker.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.codamasters.rolemaker.controller.GcmAddFriendAsyncTask;
import com.codamasters.rolemaker.controller.GcmShowUsersAsyncTask;
import com.codamasters.rolemaker.R;

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
    private static ArrayList<UserRecord> userList;

    private static ArrayAdapter<String> adapter;
    private ListView searchListView;
    private ListView requestListView;
    private Button addFriendButton;

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

        addFriendButton=(Button) rootView.findViewById(R.id.addFriendButton);

        listContainer = (LinearLayout) rootView.findViewById(R.id.contenedor_lista);
        searchListView = (ListView) rootView.findViewById(R.id.searchList);
        requestListView = (ListView)rootView.findViewById(R.id.requestList);
        resultList = new ArrayList<String>();
        requestList = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(getActivity(), R.layout.user_list_item, R.id.user_item, resultList);

        searchListView.setAdapter(adapter);

        new GcmShowUsersAsyncTask(getActivity()).execute();
        setListeners();
        return rootView;
    }

    public void setListeners(){
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend(v);
            }
        });
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

    public void addFriend(View view){
        String friendID = "";
        String friendName = ((TextView)getActivity().findViewById(R.id.user_item)).getText().toString();
        Log.d("mensaje", friendName);
        for(UserRecord user: userList){
            if(user.getName() == friendName){
                friendID = user.getId().toString();
                break;
            }
        }
        new GcmAddFriendAsyncTask(getActivity(),friendID);
    }

}
