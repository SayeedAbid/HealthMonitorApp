package com.example.healthmonitorapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileinfoFragment extends Fragment {

    private TextView mSexValue;
    private TextView mNameValue;
    private TextView mAgeValue;
    private TextView mWeightValue;
    private TextView mHeightValue;
    private TextView mEmailValue,networktext;


    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profileinfo,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        networktext = view.findViewById(R.id.networktext);

        if (haveNetwork())
            networktext.setVisibility(View.GONE);
            //Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
        else
            networktext.setVisibility(View.VISIBLE);
        //Toast.makeText(getActivity(), "Not Connected", Toast.LENGTH_SHORT).show();

        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Toast.makeText(getActivity(), "profile", Toast.LENGTH_SHORT).show();

        /////////////////////////

        mNameValue = (TextView) view.findViewById(R.id.profileNameTextView);
        mSexValue = (TextView) view.findViewById(R.id.profileSexTextView);
        mAgeValue = (TextView) view.findViewById(R.id.profileAgeTextView);
        mHeightValue = (TextView) view.findViewById(R.id.profileHeightTextView);
        mWeightValue = (TextView) view.findViewById(R.id.profileWeightTextView);
        mEmailValue = (TextView) view.findViewById(R.id.profileEmailTextView);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        myref.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Firstname = dataSnapshot.child("First Name").getValue().toString();
                String Lastname = dataSnapshot.child("Last Name").getValue().toString();
                String name = Firstname+" "+Lastname;
                String age = dataSnapshot.child("Age").getValue().toString();
                String sex = dataSnapshot.child("Sex").getValue().toString();
                String height = dataSnapshot.child("Height").getValue().toString();
                String weight = dataSnapshot.child("Weight").getValue().toString();

                String email = user.getEmail();
                //dataSnapshot.getValue().toString();
                Log.i("Fname" ,name);
                Log.i("age" ,age);
                mNameValue.setText(name);
                mAgeValue.setText("Age: "+age);
                mSexValue.setText("Sex: "+sex);
                mHeightValue.setText("Height: "+height+" cm");
                mWeightValue.setText("Weight: "+weight+" kg");

                mEmailValue.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //////////////////////////

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.editProfileFloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean haveNetwork(){
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos){
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected())
                    have_WIFI = true;
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    have_MobileData=true;
        }
        return have_MobileData||have_WIFI;
    }


}
