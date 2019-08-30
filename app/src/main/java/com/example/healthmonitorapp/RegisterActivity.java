package com.example.healthmonitorapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mUserEmail;
    private EditText mUserPassword;
    private EditText mConfirmPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAge;
    private EditText mSex;
    private EditText mWeight;
    private EditText mHeight;

    private FirebaseAuth mAuth;

    private String mSsex;

    //private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    //private DatabaseReference mDatabaseReference = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        mUserEmail = (EditText) findViewById(R.id.resetEmail);
//        mUserPassword = (EditText) findViewById(R.id.userPassword);
//        mConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        mFirstName = (EditText) findViewById(R.id.editfirstName);
        mLastName = (EditText) findViewById(R.id.editlastName);
        mAge = (EditText) findViewById(R.id.editage);
        //mSex = (EditText) findViewById(R.id.sex);
        mWeight = (EditText) findViewById(R.id.editweight);
        mHeight = (EditText) findViewById(R.id.editheight);

        mAuth = FirebaseAuth.getInstance();

        Spinner spinner = findViewById(R.id.editspinnerSex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sex,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        Log.i("Sex",mSsex);
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
//        mUserEmail.setError(null);
//        mUserPassword.setError(null);

        // Store values at the time of the login attempt.
//        String email = mUserEmail.getText().toString();
//        String password = mUserPassword.getText().toString();
        String age = mAge.getText().toString();
        //String sex = mSex.getText().toString();
        String weight = mWeight.getText().toString();
        String fname = mFirstName.getText().toString();
        String lname = mLastName.getText().toString();

        String height = mHeight.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
//            mUserPassword.setError(getString(R.string.error_invalid_password));
//            focusView = mUserPassword;
//            cancel = true;
//        }

        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mUserEmail.setError(getString(R.string.error_field_required));
//            focusView = mUserEmail;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            mUserEmail.setError(getString(R.string.error_invalid_email));
//            focusView = mUserEmail;
//            cancel = true;
//        }
        if(TextUtils.isEmpty(fname)){
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel =true;
        }
        if(TextUtils.isEmpty(lname)){
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        }
        if(TextUtils.isEmpty(age)){
            mAge.setError(getString(R.string.error_field_required));
            focusView = mAge;
            cancel = true;
        }
        if(TextUtils.isEmpty(weight)){
            mWeight.setError(getString(R.string.error_field_required));
            focusView = mWeight;
            cancel = true;
        }
        if(TextUtils.isEmpty(height)){
            mHeight.setError(getString(R.string.error_field_required));
            focusView = mHeight;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();

        }
    }


//    private boolean isEmailValid(String email){
//
//        return email.contains("@");
//    }
//
//    private boolean isPasswordValid(String password){
//        String confirmPassword = mConfirmPassword.getText().toString();
//
//        return confirmPassword.equals(password) && password.length() > 6;
//    }

    private void createFirebaseUser(){
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //mDatabaseReference = mDatabase.getReference().child("Users").child(userId);

        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String age = mAge.getText().toString();
        //String sex = mSex.getText().toString();
        String sex = mSsex;
        String weight = mWeight.getText().toString();
        String height = mHeight.getText().toString();

        Map newPost = new HashMap();
        newPost.put("First Name",firstName);
        newPost.put("Last Name",lastName);
        newPost.put("Age",age);
        newPost.put("Sex",sex);
        newPost.put("Weight",weight);
        newPost.put("Height",height);

        current_user_db.setValue(newPost);

        DatabaseReference current_user_db2 = FirebaseDatabase.getInstance().getReference().child("UserHeartRate").child(userId);
        String hrt = "0";
        Map newPost2 = new HashMap();
        newPost2.put("Last HeartRate",hrt);
        current_user_db2.setValue(newPost2);

        DatabaseReference current_user_db3 = FirebaseDatabase.getInstance().getReference().child("UserSteps").child(userId);
        String stps = "0";
        Map newPost3 = new HashMap();
        newPost3.put("Step Count",stps);
        current_user_db3.setValue(newPost3);

        Log.i("Sex",mSsex);


        Log.d("Flashchat", "create USer success");
        Toast.makeText(this, "USer Data Added", Toast.LENGTH_SHORT).show();
        finish();
        //showSucessDialog("Registration Complete");
    }




    private void showErrorDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


//    private void showSucessDialog(String message){
//        new AlertDialog.Builder(this)
//                .setTitle("Congratulation")
//                .setMessage(message)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(RegisterActivity.this, LoggedInActivity.class);
//                        finish();
//                        startActivity(intent);
//
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//
//    }

//    public void userSignIn(View view){
//        Intent intent = new Intent( RegisterActivity.this, LoggedInActivity.class);
//        finish();
//        startActivity(intent);
//
////        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
////        startActivity(intent);
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String sex = adapterView.getItemAtPosition(i).toString();
        mSsex = sex;
        //Toast.makeText(adapterView.getContext(), sex, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}




//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//
//    private EditText mUserEmail;
//    private EditText mUserPassword;
//    private EditText mConfirmPassword;
//    private EditText mFirstName;
//    private EditText mLastName;
//    private EditText mAge;
//    private EditText mSex;
//    private EditText mWeight;
//    private EditText mHeight;
//
//    private FirebaseAuth mAuth;
//
//    private String mSsex;
//
//    //private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
//    //private DatabaseReference mDatabaseReference = mDatabase.getReference();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        mUserEmail = (EditText) findViewById(R.id.resetEmail);
//        mUserPassword = (EditText) findViewById(R.id.userPassword);
//        mConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
//        mFirstName = (EditText) findViewById(R.id.editfirstName);
//        mLastName = (EditText) findViewById(R.id.editlastName);
//        mAge = (EditText) findViewById(R.id.editage);
//        //mSex = (EditText) findViewById(R.id.sex);
//        mWeight = (EditText) findViewById(R.id.editweight);
//        mHeight = (EditText) findViewById(R.id.editheight);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        Spinner spinner = findViewById(R.id.editspinnerSex);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sex,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//
//    }
//
//    // Executed when Sign Up button is pressed.
//    public void signUp(View v) {
//        Log.i("Sex",mSsex);
//        attemptRegistration();
//    }
//
//    private void attemptRegistration() {
//
//        // Reset errors displayed in the form.
//        mUserEmail.setError(null);
//        mUserPassword.setError(null);
//
//        // Store values at the time of the login attempt.
//        String email = mUserEmail.getText().toString();
//        String password = mUserPassword.getText().toString();
//        String age = mAge.getText().toString();
//        //String sex = mSex.getText().toString();
//        String weight = mWeight.getText().toString();
//        String fname = mFirstName.getText().toString();
//        String lname = mLastName.getText().toString();
//
//        String height = mHeight.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
//            mUserPassword.setError(getString(R.string.error_invalid_password));
//            focusView = mUserPassword;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mUserEmail.setError(getString(R.string.error_field_required));
//            focusView = mUserEmail;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            mUserEmail.setError(getString(R.string.error_invalid_email));
//            focusView = mUserEmail;
//            cancel = true;
//        }
//        if(TextUtils.isEmpty(fname)){
//            mFirstName.setError(getString(R.string.error_field_required));
//            focusView = mFirstName;
//            cancel =true;
//        }
//        if(TextUtils.isEmpty(lname)){
//            mLastName.setError(getString(R.string.error_field_required));
//            focusView = mLastName;
//            cancel = true;
//        }
//        if(TextUtils.isEmpty(age)){
//            mAge.setError(getString(R.string.error_field_required));
//            focusView = mAge;
//            cancel = true;
//        }
//        if(TextUtils.isEmpty(weight)){
//            mWeight.setError(getString(R.string.error_field_required));
//            focusView = mWeight;
//            cancel = true;
//        }
//        if(TextUtils.isEmpty(height)){
//            mHeight.setError(getString(R.string.error_field_required));
//            focusView = mHeight;
//            cancel = true;
//        }
//
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            // TODO: Call create FirebaseUser() here
//            createFirebaseUser();
//
//        }
//    }
//
//
//    private boolean isEmailValid(String email){
//
//        return email.contains("@");
//    }
//
//    private boolean isPasswordValid(String password){
//        String confirmPassword = mConfirmPassword.getText().toString();
//
//        return confirmPassword.equals(password) && password.length() > 6;
//    }
//
//    private void createFirebaseUser(){
//        String email = mUserEmail.getText().toString();
//        String password = mUserPassword.getText().toString();
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Log.d("Flashchat", "createUSer onComplete: " + task.isSuccessful());
//
//                if(task.isSuccessful()){
//
//                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//
//                                String userId = mAuth.getCurrentUser().getUid();
//                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
//
//                                //mDatabaseReference = mDatabase.getReference().child("Users").child(userId);
//
//                                String firstName = mFirstName.getText().toString();
//                                String lastName = mLastName.getText().toString();
//                                String age = mAge.getText().toString();
//                                //String sex = mSex.getText().toString();
//                                String sex = mSsex;
//                                String weight = mWeight.getText().toString();
//                                String height = mHeight.getText().toString();
//
//                                Map newPost = new HashMap();
//                                newPost.put("First Name",firstName);
//                                newPost.put("Last Name",lastName);
//                                newPost.put("Age",age);
//                                newPost.put("Sex",sex);
//                                newPost.put("Weight",weight);
//                                newPost.put("Height",height);
//
//                                current_user_db.setValue(newPost);
//
//                                Log.i("Sex",mSsex);
//
//
//                                Log.d("Flashchat", "create USer success");
//                                showSucessDialog("Registration Complete Verify Email");
////                    Intent intent = new Intent(RegisterActivity.this, SigninActivity.class);
////                    finish();
////                    startActivity(intent);
//
//                            }
//                            else {
//                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//
//                }
//                if(!task.isSuccessful()){
//                    Log.d("Flashchat", "create USer failed");
//                    showErrorDialog("Registration failed");
//                }
//            }
//        });
//    }
//
//
//
//
//    private void showErrorDialog(String message){
//        new AlertDialog.Builder(this)
//                .setTitle("Oops")
//                .setMessage(message)
//                .setPositiveButton(android.R.string.ok,null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//
//    }
//
//
//    private void showSucessDialog(String message){
//        new AlertDialog.Builder(this)
//                .setTitle("Congratulation")
//                .setMessage(message)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(RegisterActivity.this, SigninActivity.class);
//                        finish();
//                        startActivity(intent);
//
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//
//    }
//
//    public void userSignIn(View view){
//        Intent intent = new Intent( RegisterActivity.this, SigninActivity.class);
//        finish();
//        startActivity(intent);
//
////        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
////        startActivity(intent);
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String sex = adapterView.getItemAtPosition(i).toString();
//        mSsex = sex;
//        //Toast.makeText(adapterView.getContext(), sex, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
//
//
//}
