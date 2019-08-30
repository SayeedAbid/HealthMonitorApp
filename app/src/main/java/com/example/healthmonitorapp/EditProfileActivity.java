package com.example.healthmonitorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText fname,lname,age,height,weight;
    private String sex;
    private Button save;

    private FirebaseAuth mAuth;

    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String usersid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show();

        fname = (EditText) findViewById(R.id.editfirstName);
        lname = (EditText) findViewById(R.id.editlastName);
        age = (EditText) findViewById(R.id.editage);
        height = (EditText) findViewById(R.id.editheight);
        weight = (EditText) findViewById(R.id.editweight);

        mAuth = FirebaseAuth.getInstance();

        Spinner spinner = findViewById(R.id.editspinnerSex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sex,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        ///////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        usersid = user.getUid();

        myref.child("Users").child(usersid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String oFirstname = dataSnapshot.child("First Name").getValue().toString();
                String oLastname = dataSnapshot.child("Last Name").getValue().toString();

                String oage = dataSnapshot.child("Age").getValue().toString();

                String oheight = dataSnapshot.child("Height").getValue().toString();
                String oweight = dataSnapshot.child("Weight").getValue().toString();


                //dataSnapshot.getValue().toString();

                fname.setText(oFirstname);
                lname.setText(oLastname);
                age.setText(oage);

                height.setText(oheight);
                weight.setText(oweight);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /////////////////////////////////////////////

        save = (Button) findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editconditions();
            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String ssex = adapterView.getItemAtPosition(i).toString();
        sex = ssex;



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void editProfile(){
        //Toast.makeText(this, sex, Toast.LENGTH_SHORT).show();

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();

        String editfirstName = fname.getText().toString();
        String editlastName = lname.getText().toString();
        String editage = age.getText().toString();
        //String sex = mSex.getText().toString();
        String editsex = sex;
        String editweight = weight.getText().toString();
        String editheight = height.getText().toString();

        Map newPost = new HashMap();
        newPost.put("First Name",editfirstName);
        newPost.put("Last Name",editlastName);
        newPost.put("Age",editage);
        newPost.put("Sex",editsex);
        newPost.put("Weight",editweight);
        newPost.put("Height",editheight);

        current_user_db.setValue(newPost);

        Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EditProfileActivity.this,ProfileActivity.class);
        finish();
        startActivity(intent);

    }

    public void editconditions(){

        String eage = age.getText().toString();
        //String sex = mSex.getText().toString();
        String eweight =weight.getText().toString();
        String efname = fname.getText().toString();
        String elname = lname.getText().toString();

        String eheight =height.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if(TextUtils.isEmpty(efname)){
            fname.setError(getString(R.string.error_field_required));
            focusView = fname;
            cancel =true;
        }
        if(TextUtils.isEmpty(elname)){
            lname.setError(getString(R.string.error_field_required));
            focusView = lname;
            cancel = true;
        }
        if(TextUtils.isEmpty(eage)){
            age.setError(getString(R.string.error_field_required));
            focusView = age;
            cancel = true;
        }
        if(TextUtils.isEmpty(eweight)){
            weight.setError(getString(R.string.error_field_required));
            focusView =weight;
            cancel = true;
        }
        if(TextUtils.isEmpty(eheight)){
            height.setError(getString(R.string.error_field_required));
            focusView = height;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            editProfile();

        }
    }

}
