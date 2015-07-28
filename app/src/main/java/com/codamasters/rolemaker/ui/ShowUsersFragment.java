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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmAddFriendAsyncTask;
import com.codamasters.rolemaker.controller.GcmShowUsersAsyncTask;

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

    private static PostAdapter adapter;
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


        adapter = new PostAdapter(getActivity());

        searchListView.setAdapter(adapter);

        new GcmShowUsersAsyncTask(getActivity()).execute();
        return rootView;
    }


    public static void ListUsers(ArrayList<UserRecord> users){
        userList = users;
        ArrayList<String> usernames = new ArrayList<String>();
        for(int i = 0; i < users.size(); i++){
            usernames.add(users.get(i).getName());
        }
        resultList = usernames;

        adapter.notifyDataSetChanged();
    }

    public static ArrayList<UserRecord> getUserList() {
        return userList;
    }

    public class PostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView user_item;
            Button addFriend;

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

            Log.v(TAG, "in getView for position " + position + ", convertView is "
                    + ((convertView == null) ? "null" : "being recycled"));

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.user_list_item, null);

                holder = new ViewHolder();

                holder.user_item = (TextView) convertView
                        .findViewById(R.id.user_item);
                holder.addFriend = (Button) convertView.findViewById(R.id.addFriendButton);

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();

            // Setting all values in listview
            holder.user_item.setText(resultList.get(position));
            holder.addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<UserRecord> userList = ShowUsersFragment.getUserList();
                    String friendID = userList.get(position).getId() + "";
                    new GcmAddFriendAsyncTask(getActivity(), friendID).execute();
                }
            });

            return convertView;
        }


    }

}
