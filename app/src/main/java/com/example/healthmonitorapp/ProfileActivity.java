package com.example.healthmonitorapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private GoogleApiClient mGoogleApiClient;

    private TextView mSexValue;
    private TextView mNameValue;
    private TextView mAgeValue;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myref;
    private String uid;


    private TextView userName;
    private TextView userEmail;

    private String mName;

    private String uemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //userName = (TextView) findViewById(R.id.navUserName);
        //userEmail = (TextView) findViewById(R.id.navUserEmail);

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        //userName.setText("Towsif");


//        ///////////////////////////////////////////
//        mNameValue = (TextView) findViewById(R.id.nameValue);
//        mSexValue = (TextView) findViewById(R.id.sexValue);
//        mAgeValue = (TextView) findViewById(R.id.ageValue);
//
//
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        uemail=user.getEmail();
        uid = user.getUid();

        myref.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Firstname = dataSnapshot.child("First Name").getValue().toString();
                String Lastname = dataSnapshot.child("Last Name").getValue().toString();
                String name = Firstname+" "+Lastname;



                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView navUseremail = (TextView) headerView.findViewById(R.id.navUserEmail);
                TextView navUsername = (TextView) headerView.findViewById(R.id.navUserName);
                navUsername.setText(name);
                navUseremail.setText(uemail);
                Log.i("finalname","Name: "+name);



//                String age = dataSnapshot.child("Age").getValue().toString();
//                String sex = dataSnapshot.child("Sex").getValue().toString();
//                //dataSnapshot.getValue().toString();
//                Log.i("Fname" ,name);
//                Log.i("age" ,age);
//                mNameValue.setText("Name: "+name);
//                mAgeValue.setText("Age: "+age);
//                mSexValue.setText("Sex: "+sex);
                //userName.setText(name);
                //userEmail.setText(Firstname);
            }
//
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        //String age = myref.child("Users").child(uid).child("Age").getValue();
//        Log.i("uid" ,uid);



        //////////////////////////////////////////

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
/////////////////////////////////////

///////////////////////////////////////
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        Fragment fragment = new HomeFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content, fragment)
//                .commit();

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.screen_area,fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //finishAffinity();
            //startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
//            mAuth.getInstance().signOut();
//            Intent intent = new Intent(ProfileActivity.this,SigninActivity.class);
//            finish();
//            startActivity(intent);

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // ...
                            mAuth.getInstance().signOut();
                            Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),SigninActivity.class);
                            finish();
                            startActivity(i);
                        }
                    });


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new HomeFragment();

        } else if (id == R.id.nav_heartrate) {

            fragment = new HeartrateFragment();

        } else if (id == R.id.nav_steps) {
            fragment = new StepsFragment();

        } else if (id == R.id.nav_diet) {
            fragment = new DietFragment();

        }else if (id == R.id.nav_goal) {
            fragment = new GoalsFragment();

        }else if (id == R.id.nav_medicine) {
            fragment = new MedicineFragment();

        }else if (id == R.id.nav_profileinfo) {
            fragment = new ProfileinfoFragment();

        }else if (id == R.id.nav_faq) {
            fragment = new FAQFragment();

        } else if (id == R.id.nav_aboutus) {
            fragment = new AboutUsFragment();

        }

        if(fragment !=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area,fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }


}
