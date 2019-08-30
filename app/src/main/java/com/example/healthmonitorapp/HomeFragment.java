package com.example.healthmonitorapp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthmonitorapp.ui.fragments.DietFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private TextView mSexValue;
    private TextView mNameValue;
    private TextView mAgeValue;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String uid;


    LinearLayout heart,steps,calorie;
    TextView networktext,hearthome,stephome,caloriehome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        networktext = view.findViewById(R.id.networktext);

        if (haveNetwork())
            networktext.setVisibility(View.INVISIBLE);
            //Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
        else
            networktext.setVisibility(View.VISIBLE);
        //Toast.makeText(getActivity(), "Not Connected", Toast.LENGTH_SHORT).show();


//        hearthome = view.findViewById(R.id.currentheartratehome);
//        stephome = view.findViewById(R.id.currentstepshome);
//        caloriehome = view.findViewById(R.id.currentcaloriehome);
//
////////////////heart
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myref = mFirebaseDatabase.getReference();
//        final FirebaseUser user = mAuth.getCurrentUser();
//        uid = user.getUid();
//
//        myref.child("UserHeartRate").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String heartrate = dataSnapshot.child("Last HeartRate").getValue().toString();
//                hearthome.setText(heartrate+" bpm");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        /////////////step
//        myref.child("UserSteps").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String steps = dataSnapshot.child("Step Count").getValue().toString();
//                stephome.setText(steps+" steps");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        ////////////calorie
//        myref.child("UserSteps").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String steps = dataSnapshot.child("Step Count").getValue().toString();
//                int calburn = Integer.valueOf(steps)*40/1000;
//                Log.i("calint",String.valueOf(calburn));
//                Log.i("calStr",steps);
//                caloriehome.setText(String.valueOf(calburn)+" calories burnt");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });




        heart = view.findViewById(R.id.home_heart);
        steps = view.findViewById(R.id.home_steps);
        calorie = view.findViewById(R.id.home_calorie);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "heart", Toast.LENGTH_SHORT).show();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,new HeartrateFragment());
                ft.commit();
            }
        });

        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "steps", Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,new StepsFragment());
                ft.commit();

            }
        });

        calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "cal", Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area,new DietFragment());
                ft.commit();

            }
        });

//        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Home fragmanet", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        ////////////////////////
//
//        mNameValue = (TextView) view.findViewById(R.id.nameValue);
//        mSexValue = (TextView) view.findViewById(R.id.sexValue);
//        mAgeValue = (TextView) view.findViewById(R.id.ageValue);
//
//
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myref = mFirebaseDatabase.getReference();
//        FirebaseUser user = mAuth.getCurrentUser();
//        uid = user.getUid();
//
//        myref.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String Firstname = dataSnapshot.child("First Name").getValue().toString();
//                String Lastname = dataSnapshot.child("Last Name").getValue().toString();
//                String name = Firstname+" "+Lastname;
//                String age = dataSnapshot.child("Age").getValue().toString();
//                String sex = dataSnapshot.child("Sex").getValue().toString();
//                //dataSnapshot.getValue().toString();
//                Log.i("Fname" ,name);
//                Log.i("age" ,age);
//                mNameValue.setText("Name: "+name);
//                mAgeValue.setText("Age: "+age);
//                mSexValue.setText("Sex: "+sex);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });





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
