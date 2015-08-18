package com.codamasters.rolemaker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.codamasters.rolemaker.R;
import com.codamasters.rolemaker.controller.DownloadImageAsyncTask;
import com.codamasters.rolemaker.controller.UploadImageAsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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
    private static final int REQUEST_CODE = 1;
    Button bShowUsers,bShowFriends, bShowRequests, bCreateGame, bJoinGame, bMyGames, bUploadPhoto;
    private static ImageView imageView;
    private static Bitmap image;
    private static String url;
    private static Context context;


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
        bUploadPhoto=(Button) rootView.findViewById(R.id.bUploadPhoto);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
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
        bUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_CODE);


            }
        });
    }

    public static void setImage(Bitmap bmp){
        imageView.setImageBitmap(bmp);
    }

    public static void setURL(String u){
        url = u;
        new DownloadImageAsyncTask(context, url,0).execute();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        context = this.getActivity();
            try {
                // We need to recyle unused bitmaps
                if (image != null) {
                    image.recycle();
                }
                InputStream stream = getActivity().getContentResolver().openInputStream(
                        data.getData());
                image = BitmapFactory.decodeStream(stream);
                stream.close();

                new UploadImageAsyncTask(getActivity(),image).execute();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        super.onActivityResult(requestCode, resultCode, data);
    }

}
