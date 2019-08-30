package com.example.healthmonitorapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MedicineFragment extends Fragment {

    ListView listView;
    DatabaseHelper myDb;

    ArrayList<Medicine> medicinelist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medicine,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingAddMedicineButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Add medicine", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),PillReminderActivity.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "Add medicine", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getActivity(), "Medicine", Toast.LENGTH_LONG).show();

        listView = view.findViewById(R.id.listView);
        myDb = new DatabaseHelper(getActivity().getApplicationContext());
        medicinelist = new ArrayList<>();
        Cursor data = myDb.getAllData();
        int numRows = data.getCount();

        if(numRows == 0)
            Toast.makeText(getActivity(), "Empty db", Toast.LENGTH_SHORT).show();
        else
        {
            while (data.moveToNext()){
                int id = data.getInt(0);
                String name = data.getString(1);
                String unit = data.getString(2);
                String time = data.getString(3);
                String note = data.getString(4);

                Medicine medicine = new Medicine(id,name,unit,time,note);
                //medicine = new Medicine(data.getString(1),data.getString(2),data.getString(3),data.getString(4));

                medicinelist.add(medicine);
            }
            MyAdapter adapter = new MyAdapter(getActivity(),R.layout.mycustomlistview, medicinelist);
            listView.setAdapter(adapter);
        }
    }

    public void addMedicine(){


    }
}
