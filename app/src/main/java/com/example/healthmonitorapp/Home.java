//package com.example.healthmonitorapp;
//
//import android.app.Application;
//import android.content.Intent;
//
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class Home extends Application {
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//        if (firebaseUser != null){
//            //Intent intent = new Intent(Home.this,ProfileActivity.class);
//            //finish();
//
////            Intent intent= new Intent(Home.this, ProfileActivity.class);
////            finish();
////            startActivity(intent);
//
//            startActivity(new Intent(Home.this,ProfileActivity.class));
//        }
////        if (firebaseUser == null){
////            startActivity(new Intent(Home.this,SigninActivity.class));
////
////        }
//    }
//}
