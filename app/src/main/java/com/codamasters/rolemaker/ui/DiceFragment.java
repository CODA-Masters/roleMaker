package com.codamasters.rolemaker.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.codamasters.rolemaker.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Julio on 16/08/2015.
 */
public class DiceFragment extends Fragment {

    private static final String ARG_PARAM = "param1";
    private Spinner sDiceNumbers;
    private Spinner sDiceFaces;

    private PostAdapter postAdapter;
    private PostAdapter2 postAdapter2;

    private static ArrayList<String> diceNumbers;
    private static ArrayList<Integer> diceFaces;
    private static ArrayList<Integer> bonifications;

    private Button bThrowDice, bPlus2, bPlus4, bMinus2, bMinus4;
    private TextView tvResultDice, tvBonifications;



    public static DiceFragment newInstance(String param1) {
        DiceFragment fragment = new DiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        diceNumbers = new ArrayList<>();
        diceNumbers.add("El broza dado");
        diceNumbers.add("El doble broza");
        diceNumbers.add("El triple broza");

        diceFaces = new ArrayList<Integer>();
        diceFaces.add(5);
        diceFaces.add(6);
        diceFaces.add(7);
        diceFaces.add(9);

        bonifications = new ArrayList<>();
        return fragment;
    }
    public DiceFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_dice, container, false);

        // Tipo de dado
        sDiceNumbers = (Spinner) rootView.findViewById(R.id.sDiceNumber);
        postAdapter = new PostAdapter(getActivity());
        sDiceNumbers.setAdapter(postAdapter);

        // Numero de dados

        sDiceFaces = (Spinner) rootView.findViewById(R.id.sDiceFaces);
        postAdapter2 = new PostAdapter2(getActivity());
        sDiceFaces.setAdapter(postAdapter2);

        // Bonificaciones

        tvBonifications = (TextView) rootView.findViewById(R.id.listBonifications);


        bMinus2 = (Button) rootView.findViewById(R.id.bMinus2);
        bMinus4 = (Button) rootView.findViewById(R.id.bMinus4);

        bPlus2 = (Button) rootView.findViewById(R.id.bPlus2);
        bPlus4 = (Button) rootView.findViewById(R.id.bPlus4);

        bMinus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonifications.add(-2);
                addBonifications("-2");
            }
        });
        bMinus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonifications.add(-4);
                addBonifications("-4");
            }
        });

        bPlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonifications.add(2);
                addBonifications("2");
            }
        });
        bPlus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonifications.add(4);
                addBonifications("4");
            }
        });

        // Boton de lanzar dado y resultado

        bThrowDice = (Button) rootView.findViewById(R.id.bThrowDice);
        tvResultDice = (TextView) rootView.findViewById(R.id.tvResultDice);


        bThrowDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throwDice();
            }
        });


        return rootView;
    }


    public void throwDice(){
        int number_dices = sDiceNumbers.getSelectedItemPosition()+1;
        int number_faces = Integer.parseInt(sDiceFaces.getSelectedItem().toString());

        int res = 0;
        Random rand = new Random();

        for ( int i=0; i<number_dices; i++){
            res += rand.nextInt(number_faces)+1;
        }

        for( int aux : bonifications){
            res += aux;
        }

        tvResultDice.setText(String.valueOf(res));

    }

    public void addBonifications(String bonification){

        String aux = tvBonifications.getText().toString();
        aux = aux.concat(" ").concat(bonification);

        tvBonifications.setText(aux);

    }


    public class PostAdapter implements SpinnerAdapter {

        class ViewHolder {
            TextView diceValue;
        }

        private LayoutInflater inflater = null;

        public PostAdapter(Context c) {

            inflater = LayoutInflater.from(c);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            convertView = inflater.inflate(R.layout.dice_number_item, null);


            holder = new ViewHolder();

            holder.diceValue = (TextView) convertView.findViewById(R.id.tvDiceValue);
            holder.diceValue.setText(diceNumbers.get(position));

            return convertView;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return diceNumbers.size();
        }

        @Override
        public Object getItem(int position) {
            return diceNumbers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            convertView = inflater.inflate(R.layout.dice_number_item, null);


            holder = new ViewHolder();

            holder.diceValue = (TextView) convertView.findViewById(R.id.tvDiceValue);
            holder.diceValue.setText(diceNumbers.get(position).toString());

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    public class PostAdapter2 implements SpinnerAdapter {

        class ViewHolder {
            TextView diceValue;
        }

        private LayoutInflater inflater = null;

        public PostAdapter2(Context c) {

            inflater = LayoutInflater.from(c);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            convertView = inflater.inflate(R.layout.dice_faces_item, null);


            holder = new ViewHolder();

            holder.diceValue = (TextView) convertView.findViewById(R.id.tvDiceValue);
            holder.diceValue.setText(diceFaces.get(position).toString());

            return convertView;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return diceFaces.size();
        }

        @Override
        public Object getItem(int position) {
            return diceFaces.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            convertView = inflater.inflate(R.layout.dice_faces_item, null);

            holder = new ViewHolder();

            holder.diceValue = (TextView) convertView.findViewById(R.id.tvDiceValue);
            holder.diceValue.setText(diceFaces.get(position).toString());

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }


}
