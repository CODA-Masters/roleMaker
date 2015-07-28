package com.codamasters.rolemaker.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmRemoveFriendAsyncTask;
import com.codamasters.rolemaker.controller.GcmShowFriendsAsyncTask;

import java.util.ArrayList;

import gcm.backend.registration.model.UserRecord;

/**
 * Created by Julio on 27/07/2015.
 */
public class ShowFriendsFragment extends Fragment {

    private static final String ARG_PARAM = "param1";

    // Hashmap for ListView
    private LinearLayout listContainer;
    private static ArrayList<String> resultList;

    private static PostAdapter adapter;
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


        adapter = new PostAdapter(getActivity());

        friendsListView.setAdapter(adapter);

        String myId = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user", "nothing");

        Log.d("Yo soy", myId);

        new GcmShowFriendsAsyncTask(getActivity(), myId).execute();
        return rootView;
    }

    public static void ListUsers(ArrayList<String> users){

        resultList = users;
        adapter.notifyDataSetChanged();
    }

    // Adaptador de la lista
    public class PostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView friend_item;
            Button removeFriend;

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
                convertView = inflater.inflate(R.layout.friend_list_item, null);

                holder = new ViewHolder();

                holder.friend_item = (TextView) convertView
                        .findViewById(R.id.friend_item);
                holder.removeFriend = (Button) convertView.findViewById(R.id.removeFriendButton);

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();

            // Setting all values in listview
            holder.friend_item.setText(resultList.get(position));
            holder.removeFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String friendID = resultList.get(position);
                    new GcmRemoveFriendAsyncTask(getActivity(), friendID).execute();
                }
            });

            return convertView;
        }


    }

}
