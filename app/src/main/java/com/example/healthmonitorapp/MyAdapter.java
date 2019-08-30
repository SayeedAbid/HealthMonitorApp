package com.example.healthmonitorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Medicine> {

    private LayoutInflater mInflater;
    private ArrayList<Medicine> medicines;
    private int mViewResourceid;



    public MyAdapter(Context context, int mViewResourceid, ArrayList<Medicine> medicines){
        super(context, mViewResourceid, medicines);
        this.medicines = medicines;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mViewResourceid = mViewResourceid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(mViewResourceid, null);
        Medicine medicine = medicines.get(position);
        if(medicine != null){


            TextView mname = (TextView)convertView.findViewById(R.id.medname);
            TextView mnotes = (TextView)convertView.findViewById(R.id.mednotes);
            TextView munit = (TextView)convertView.findViewById(R.id.medunit);
            TextView mtime = (TextView)convertView.findViewById(R.id.medtime);

            mname.setText(medicine.getName());
            munit.setText(medicine.getUnit());
            mtime.setText(medicine.getTime());
            mnotes.setText(medicine.getNote());

        }
        return convertView;
    }




}
