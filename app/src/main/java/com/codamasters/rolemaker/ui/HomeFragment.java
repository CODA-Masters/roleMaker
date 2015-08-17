package com.codamasters.rolemaker.ui;

import android.app.Activity;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codamasters.rolemaker.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    Button bShowUsers,bShowFriends, bShowRequests, bCreateGame, bJoinGame, bMyGames;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public void showUsers (View view){
        getFragmentManager().beginTransaction()
                .addToBackStack("")
                .replace(R.id.container,ShowUsersFragment.newInstance(""))
                .commit();
    }

    public void showFriends (View view){
        getFragmentManager().beginTransaction()
                .addToBackStack("")
                .replace(R.id.container,ShowFriendsFragment.newInstance(""))
                .commit();
    }

    public void showRequests (View view){
        getFragmentManager().beginTransaction()
                .addToBackStack("")
                .replace(R.id.container,ShowFriendRequestsFragment.newInstance(""))
                .commit();
    }

    public void createGame (View view){
        getFragmentManager().beginTransaction()
                .addToBackStack("")
                .replace(R.id.container,CreateGameFragment.newInstance(""))
                .commit();
    }

    public void joinGame (View view){
        getFragmentManager().beginTransaction()
                .addToBackStack("")
                .replace(R.id.container, JoinGameFragment.newInstance(""))
                .commit();
    }

    public void myGames (View view){
        getFragmentManager().beginTransaction()
                .addToBackStack("")
                .replace(R.id.container, MyGamesFragment.newInstance(""))
                .commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_home, container, false);
        bShowUsers=(Button) rootView.findViewById(R.id.bShowUsers);
        bShowFriends=(Button) rootView.findViewById(R.id.bShowFriends);
        bShowRequests=(Button) rootView.findViewById(R.id.bShowRequests);
        bCreateGame=(Button) rootView.findViewById(R.id.bCreateGame);
        bJoinGame=(Button) rootView.findViewById(R.id.bJoinGame);
        bMyGames=(Button) rootView.findViewById(R.id.bMyGames);
        setListeners();
        return rootView;
    }

    private void setListeners(){
        bShowFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFriends(v);
            }
        });
        bShowUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUsers(v);
            }
        });
        bShowRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRequests(v);
            }
        });
        bCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGame(v);
            }
        });
        bJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGame(v);
            }
        });
        bMyGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGames(v);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LoggedActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
