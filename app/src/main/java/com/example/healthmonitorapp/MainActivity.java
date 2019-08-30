package com.example.healthmonitorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView mSexValue;
    private TextView mNameValue;
    private TextView mAgeValue;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String uid;
// ...
    //mDatabase = FirebaseDatabase.getInstance().getReference();NewPostActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameValue = (TextView) findViewById(R.id.nameValue);
        mSexValue = (TextView) findViewById(R.id.sexValue);
        mAgeValue = (TextView) findViewById(R.id.ageValue);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();

//        DatabaseReference databaseReference = mFirebaseDatabase.getReference(mAuth.getUid());
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        //String userId = mAuth.getCurrentUser().getUid();
        //DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //mNameValue.setText(userId.toString());


        //String uid = mAuth.getInstance().getCurrentUser().getUid();

//        mFirebaseDatabase.getInstance().getReference(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String name = dataSnapshot.child("First Name").getValue().toString();
//
//                //mNameValue.setText(name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        myref.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Firstname = dataSnapshot.child("First Name").getValue().toString();
                String Lastname = dataSnapshot.child("Last Name").getValue().toString();
                String name = Firstname+" "+Lastname;
                String age = dataSnapshot.child("Age").getValue().toString();
                String sex = dataSnapshot.child("Sex").getValue().toString();
                //dataSnapshot.getValue().toString();
                Log.i("Fname" ,name);
                Log.i("age" ,age);
                mNameValue.setText("Name: "+name);
                mAgeValue.setText("Age: "+age);
                mSexValue.setText("Sex: "+sex);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //String age = myref.child("Users").child(uid).child("Age").getValue();
        Log.i("uid" ,uid);


    }

    public void profileActivity(View view){
        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
        finish();
        startActivity(intent);
    }
}
