package com.example.healthmonitorapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton gSignIn;
    private GoogleApiClient mGoogleApiClient;


    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        gSignIn = findViewById(R.id.sign_in_button_google);


        mAuth = FirebaseAuth.getInstance();

        configureSignIn();

        final FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            finish();
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }else {

            gSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(SigninActivity.this,"Loging in...",Toast.LENGTH_SHORT).show();
                    signIn();
                }
            });
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // This method configures Google SignIn
    public void configureSignIn(){
// Configure sign-in to request the user’s basic profile like name and email
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
// Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                AuthCredential credential = GoogleAuthProvider.getCredential(result.getSignInAccount().getIdToken(), null);
                firebaseAuthWithGoogle(credential);
                Toast.makeText(SigninActivity.this,"Loging in...",Toast.LENGTH_SHORT).show();
            } else {
                // Google Sign In failed, update UI appropriately


                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential){

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SigninActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else {

                            mAuth = FirebaseAuth.getInstance();
                            mFirebaseDatabase = FirebaseDatabase.getInstance();
                            myref = mFirebaseDatabase.getReference();
                            final FirebaseUser user = mAuth.getCurrentUser();
                            uid = user.getUid();

                            myref.child("Users").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(uid).exists()) {
                                        //Toast.makeText(SigninActivity.this, "User exists", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SigninActivity.this,ProfileActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }


                                    else {
                                        Toast.makeText(SigninActivity.this, "User not exist", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SigninActivity.this,RegisterActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //Toast.makeText(MainActivity.this, uid, Toast.LENGTH_SHORT).show();

//                            Intent intent = new Intent(MainActivity.this,LoggedInActivity.class);
//                            finish();
//                            startActivity(intent);

                        }
                    }
                });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
//////////////////////////////////////////////////




//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//
//
//public class SigninActivity extends AppCompatActivity {
//
//    private FirebaseAuth mAuth;
//
//    private EditText mEmail;
//    private EditText mPassword;
//    private Button mSignIn;
//
//    public void userRegistration(View view){
//        Intent intent = new Intent(SigninActivity.this, RegisterActivity.class);
//        finish();
//        startActivity(intent);
//
////        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
////        startActivity(intent);
//    }
//
//    public void passwordReset(View view){
//        Intent intent = new Intent(SigninActivity.this, ResetPasswordActivity.class);
//        finish();
//        startActivity(intent);
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signin);
//
//        mEmail = (EditText) findViewById(R.id.resetEmail);
//        mPassword = (EditText) findViewById(R.id.userPassword);
//        mSignIn = (Button) findViewById(R.id.signinButton);
//
//
//
//        // TODO: Grab an instance of FirebaseAuth
//        mAuth = FirebaseAuth.getInstance();
//    }
//
//    // Executed when Sign in button pressed
//    public void signInExistingUser(View v)   {
//        // TODO: Call attemptLogin() here
//        boolean cancel = false;
//        View focusView = null;
//
//        String email = mEmail.getText().toString();
//        String password = mPassword.getText().toString();
//
//        if (TextUtils.isEmpty(email)) {
//            mEmail.setError(getString(R.string.error_field_required));
//            focusView = mEmail;
//            cancel = true;
//        } else if(TextUtils.isEmpty(password)){
//            mPassword.setError(getString(R.string.error_field_required));
//            focusView = mPassword;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            attemptLogin();
//
//        }
//
//
//    }
//
////    // Executed when Register button pressed
////    public void registerNewUser(View v) {
////        Intent intent = new Intent(this, RegisterActivity.class);
////        finish();
////        startActivity(intent);
////    }
//
//    // TODO: Complete the attemptLogin() method
//    private void attemptLogin() {
//
//        String email = mEmail.getText().toString();
//        String password = mPassword.getText().toString();
//
//        if(email.equals("") || password.equals("")) return;
//        Toast.makeText(this,"Loging in...",Toast.LENGTH_SHORT).show();
//
//        // TODO: Use FirebaseAuth to sign in with email & password
//        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Log.d("FlashChat", "email signin" + task.isSuccessful());
//                if(!task.isSuccessful()){
//                    Log.d("FlashChat","Problem signing in" + task.getException());
//                    showErrorDialog("There was a problem");
//                }else{
//                    //Intent intent = new Intent(SigninActivity.this, MainActivity.class);
//                    if (mAuth.getCurrentUser().isEmailVerified()){
//                        Intent intent = new Intent(SigninActivity.this,ProfileActivity.class);
//                        finish();
//                        startActivity(intent);
//                    }
//                    else{
//                        Toast.makeText(SigninActivity.this, "Verify email please", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//
//
//    }
//
//    // TODO: Show error on screen with an alert dialog
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
//}
