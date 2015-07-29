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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmAcceptFriendRequestAsyncTask;
import com.codamasters.rolemaker.controller.GcmDenyFriendRequestAsyncTask;
import com.codamasters.rolemaker.controller.GcmRemoveFriendAsyncTask;
import com.codamasters.rolemaker.controller.GcmShowFriendRequestsAsyncTask;

import java.util.ArrayList;

/**
 * Created by Julio on 29/07/2015.
 */
public class ShowFriendRequestsFragment extends Fragment {

    private static final String ARG_PARAM = "param1";

    // Hashmap for ListView
    private LinearLayout listContainer;
    public static ArrayList<String> sentFriendNamesS, sentFriendIDsS, receivedFriendNamesS, receivedFriendIDsS;


    private static ReceivedPostAdapter rAdapter;
    private static SentPostAdapter sAdapter;
    private ListView sentRequestsListView;
    private ListView receivedRequestsListView;

    public static ShowFriendRequestsFragment newInstance(String param1) {
        ShowFriendRequestsFragment fragment = new ShowFriendRequestsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public ShowFriendRequestsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_show_friend_requests, container, false);

        sentRequestsListView = (ListView) rootView.findViewById(R.id.sentRequestsList);
        receivedRequestsListView = (ListView) rootView.findViewById(R.id.receivedRequestsList);


        sentFriendNamesS = new ArrayList<>();
        sentFriendIDsS = new ArrayList<>();
        receivedFriendNamesS = new ArrayList<>();
        receivedFriendIDsS = new ArrayList<>();

        rAdapter = new ReceivedPostAdapter(getActivity());
        sAdapter = new SentPostAdapter(getActivity());

        sentRequestsListView.setAdapter(sAdapter);
        receivedRequestsListView.setAdapter(rAdapter);

        String myId = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user", "nothing");

        new GcmShowFriendRequestsAsyncTask(getActivity(), myId).execute();

        return rootView;
    }

    public static void ListRequests(ArrayList<String> sentFriendNames, ArrayList<String> sentFriendIDs,
                                    ArrayList<String> receivedFriendNames, ArrayList<String> receivedFriendIDs){

        sentFriendIDsS = sentFriendIDs;
        sentFriendNamesS = sentFriendNames;
        receivedFriendIDsS = receivedFriendIDs;
        receivedFriendNamesS = receivedFriendNames;

        sAdapter.notifyDataSetChanged();
        rAdapter.notifyDataSetChanged();
    }

    private void updateFriends(String friendName, String friendID){
        receivedFriendNamesS.remove(friendName);
        receivedFriendIDsS.remove(friendID);
        rAdapter.notifyDataSetChanged();
    }



    // Adaptador de la lista de recibidos
    public class ReceivedPostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView received_item;
            Button acceptFriendButton;
            Button denyFriendButton;

        }

        private static final String TAG = "CustomAdapter";

        private LayoutInflater inflater = null;

        public ReceivedPostAdapter(Context c) {
            Log.v(TAG, "Constructing CustomAdapter");

            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            Log.v(TAG, "in getCount()");
            return receivedFriendNamesS.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG, "in getItem() for position " + position);
            return receivedFriendNamesS.get(position);
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
                convertView = inflater.inflate(R.layout.received_list_item, null);

                holder = new ViewHolder();

                holder.received_item = (TextView) convertView
                        .findViewById(R.id.received_item);
                holder.acceptFriendButton = (Button) convertView.findViewById(R.id.acceptFriendButton);
                holder.denyFriendButton = (Button) convertView.findViewById(R.id.denyFriendButton);

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();

            // Setting all values in listview
            holder.received_item.setText(receivedFriendNamesS.get(position));

            holder.acceptFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String friendID = receivedFriendIDsS.get(position);
                    new GcmAcceptFriendRequestAsyncTask(getActivity(), friendID).execute();
                    updateFriends(receivedFriendNamesS.get(position), friendID);
                }
            });
            holder.denyFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String friendID = receivedFriendIDsS.get(position);
                    new GcmDenyFriendRequestAsyncTask(getActivity(), friendID).execute();
                    updateFriends(receivedFriendNamesS.get(position), friendID);
                }
            });

            return convertView;
        }


    }



    // Adaptador de la lista de enviados
    public class SentPostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView sent_item;
        }

        private static final String TAG = "CustomAdapter";

        private LayoutInflater inflater = null;

        public SentPostAdapter(Context c) {
            Log.v(TAG, "Constructing CustomAdapter");

            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            Log.v(TAG, "in getCount()");
            return sentFriendNamesS.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG, "in getItem() for position " + position);
            return sentFriendNamesS.get(position);
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
                convertView = inflater.inflate(R.layout.sent_list_item, null);

                holder = new ViewHolder();

                holder.sent_item = (TextView) convertView
                        .findViewById(R.id.sent_item);

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();

            // Setting all values in listview
            holder.sent_item.setText(sentFriendNamesS.get(position));

            return convertView;
        }


    }

}
