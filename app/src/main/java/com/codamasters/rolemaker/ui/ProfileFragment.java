package com.codamasters.rolemaker.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.GcmProfileAsyncTask;

import gcm.backend.registration.model.UserRecord;

/**
 * Created by Julio on 27/07/2015.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    private static TextView tvUserName;
    private static UserRecord user;
    private static String userName = "";


    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public ProfileFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_profile, container, false);

        tvUserName = (TextView) rootView.findViewById(R.id.tvUserName);

        new GcmProfileAsyncTask(getActivity(), userName).execute();
        return rootView;
    }

    public static void LoadProfile(UserRecord u){
        user = u;
        tvUserName.setText(u.getName());
    }

    public static void setUserName(String userName) {
        ProfileFragment.userName = userName;
    }
}
