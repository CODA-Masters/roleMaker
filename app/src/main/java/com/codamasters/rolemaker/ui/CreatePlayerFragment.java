package com.codamasters.rolemaker.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.codamasters.rolemaker.R;

import java.util.ArrayList;

/**
 * Created by Julio on 09/08/2015.
 */
public class CreatePlayerFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    private ListView lv;
    private static ArrayList<Pair<String, String> > attributeList;
    private static PostAdapter adapter;
    private Button bAddAttribute;

    public static CreatePlayerFragment newInstance(String param1) {
        CreatePlayerFragment fragment = new CreatePlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public CreatePlayerFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_create_player, container, false);

        lv = (ListView) rootView.findViewById(R.id.attributeList);
        bAddAttribute = (Button) rootView.findViewById(R.id.bAddAttribute);
        attributeList = new ArrayList<>();
        attributeList.add(Pair.create("Level","Numeric"));
        attributeList.add(Pair.create("Mana","Numeric"));
        attributeList.add(Pair.create("Strength","Numeric"));
        attributeList.add(Pair.create("Defense","Numeric"));

        adapter = new PostAdapter(getActivity());

        lv.setAdapter(adapter);
        bAddAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Gracias a este coso se puede hacer scroll a la listview dentro de un scrollview
        lv.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        return rootView;
    }

    public class PostAdapter extends BaseAdapter {

        class ViewHolder {
            TextView tvName, tvValue;
            Button bEditAttribute;
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
            return attributeList.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG, "in getItem() for position " + position);
            return attributeList.get(position);
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
                convertView = inflater.inflate(R.layout.attribute_list_item, null);

                holder = new ViewHolder();

                holder.tvName = (TextView) convertView
                        .findViewById(R.id.tvName);
                holder.tvValue = (TextView) convertView
                        .findViewById(R.id.tvValue);
                holder.bEditAttribute = (Button) convertView.findViewById(R.id.bEditAttribute);

                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();

            // Setting all values in listview
            holder.tvName.setText(attributeList.get(position).first);
            holder.tvValue.setText(attributeList.get(position).second);
            holder.bEditAttribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return convertView;
        }
    }
}
