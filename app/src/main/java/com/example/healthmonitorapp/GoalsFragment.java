package com.example.healthmonitorapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoalsFragment extends Fragment {

    Button btn;
    TextView userheight,userweight,userbmi,food,exercise,userbmiinfo,networktext;
    float h ,w;
    //Float calbmi;


    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myref;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goals,null);
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

        //Toast.makeText(getActivity(), "Goals", Toast.LENGTH_LONG).show();
        userheight = view.findViewById(R.id.userHeight);
        userweight = view.findViewById(R.id.userWeight);
        userbmi = view.findViewById(R.id.userbmi);
        userbmiinfo = view.findViewById(R.id.userbmiinfo);

        food = view.findViewById(R.id.foodsuggeston);
        exercise = view.findViewById(R.id.exerciseSuggestion);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        myref.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String age = dataSnapshot.child("Age").getValue().toString();
                String sex = dataSnapshot.child("Sex").getValue().toString();
                String height = dataSnapshot.child("Height").getValue().toString();
                String weight = dataSnapshot.child("Weight").getValue().toString();

                userheight.setText("Height: "+height+" cm");
                userweight.setText("Weight: "+weight+" kg");

                h = Float.parseFloat(height);
                w = Float.parseFloat(weight);

                Log.i("hw1",h+" "+w);

                float calbmi = (w/((h*h)/10000));
                userbmi.setText("BMI: "+String.format("%.1f",calbmi));
                bmiSugestion(calbmi);
                Log.i("hw",h+" "+w);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        float calbmi = (w/((h*h)/10000));
//
//        //Log.i("bmi",calbmi.);
//
//        bmiSugestion(calbmi);
//        Log.i("hw",h+" "+w);




    }

    public void bmiSugestion(Float bmi){

        //String bmi = String.format("%.2f",bbmi);
        int state =0 ;

        if(bmi < 18.5){
            userbmiinfo.setText("You are underweight.");
            state =1;
        }
        else if(bmi > 18.4 && bmi <=25) {
            userbmiinfo.setText("Your weight is normal. Keep it up!");
            state =2;
        }
        else if(bmi > 25 && bmi <=30) {
            userbmiinfo.setText("You are overweight.");
            state =3;
        }
        else if(bmi > 30 && bmi <=35) {
            userbmiinfo.setText("You are obese.");
            state =4;
        }
        else if(bmi > 35 && bmi <=40) {
            userbmiinfo.setText("You are very obese.");
            state =5;
        }
        else if(bmi > 40) {
            userbmiinfo.setText("You are extremely obese.");
            state =6;
        }

        if (state == 1){
            food.setText("Try almonds, sunflower seeds, fruit, or whole-grain, wheat toast. \n" +
                    "-Go nutrient dense. Instead of eating empty\n" +
                    "calories and junk food, eat foods that are rich in nutrients.\n" +
                    "- Consider high-protein meats, which can help you to build muscle.\n" +
                    "-Try smoothies and shakes. \n" +
                    "-Watch when you drink. \n" +
                    "-Make every bite count.\n" +
                    "-Have an occasional treat.");
            exercise.setText("While too much aerobic exercise will burn calories and work against your weight goal, strength training can help.\n" +
                    "-Weightlifting or yoga. You can gain weight by building muscle.");
        }
        else if (state == 2){
            food.setText("Since your BMI is normal, but you have to maintain this. A healthy, nutritionally balanced diet which includes lean " +
                    "meat or protein sources,fish, vegetables, salad, wholegrains, reduced fat dairy products and fresh fruit will do.");
            exercise.setText("Your health may still be at risk if you are not getting regular physical activity and practicing healthy eating.\n" +
                    "You can try -\n" +
                    "\n" +
                    "-Walking or Jogging\n" +
                    "-Weight Training\n" +
                    "-Yoga");
        }
        else if (state == 3){
            food.setText("Choose minimally processed, whole foods:\n" +
                    "-Whole grains (whole wheat, steel cut oats, brown rice, quinoa)\n" +
                    "-Vegetables (a colorful variety-not potatoes)\n" +
                    "-Whole fruits (not fruit juices)\n" +
                    "-Nuts, seeds, beans, and other healthful sources of protein (fish and poultry)\n" +
                    "-Plant oils (olive and other vegetable oils)");
            exercise.setText("-Aerobic Workouts to Lose Weight\n" +
                    "-Running is cardiovascular activity. It will help. \n" +
                    "-Swimming\n" +
                    "-Riding a bike \n" +
                    "-Brisk wlking. \n" +
                    "-Weight lifting ; makes your heart rae increase and makes you breathe deep.");
        }
        else if (state == 4){
            food.setText("Choose minimally processed, whole foods:\n" +
                    "-Whole grains (whole wheat, steel cut oats, brown rice, quinoa)\n" +
                    "-Vegetables (a colorful variety-not potatoes)\n" +
                    "-Whole fruits (not fruit juices)\n" +
                    "-Nuts, seeds, beans, and other healthful sources of protein (fish and poultry)\n" +
                    "-Plant oils (olive and other vegetable oils)");
            exercise.setText("-Aerobic Workouts to Lose Weight\n" +
                    "-Running is cardiovascular activity. It will help. \n" +
                    "-Swimming\n" +
                    "-Riding a bike \n" +
                    "-Brisk wlking. \n" +
                    "-Weight lifting ; makes your heart rae increase and makes you breathe deep.");
        }
        else if (state == 5){
            food.setText("-Choose minimally processed, whole foods:\n" +
                    "-Whole grains (whole wheat, steel cut oats, brown rice, quinoa)\n" +
                    "-Vegetables (a colorful variety-not potatoes)\n" +
                    "-Whole fruits (not fruit juices)\n" +
                    "-Nuts, seeds, beans, and other healthful sources of protein (fish and poultry)\n" +
                    "-Plant oils (olive and other vegetable oils)\n" +
                    "\n" +
                    "Try to eat more in the morning and less in afternoon and evening. Try to burn the calories by doing physical activities\n" +
                    "or exercises.");
            exercise.setText("You should do ≥5 days per week of aerobic exercises to maximise caloric expenditure. Try :\n" +
                    "\n" +
                    "-Aerobic Workouts to Lose Weight\n" +
                    "-Running is cardiovascular activity. It will help. \n" +
                    "-Swimming\n" +
                    "-Riding a bike \n" +
                    "-Brisk walking. \n" +
                    "-Weight lifting ; makes your heart rae increase and makes you breathe deep.\n" +
                    "\n" +
                    "Make a seven days plan and follow the suggestions mentioned above.");
        }
        else if (state == 6){
            food.setText("Change your diet.\n" +
                    "“You have to become a good record keeper,” Dr. Eckel said. \n" +
                    "-Reduce calories by 500 calories per day to lose about a oe pound a week, or cut 1,000 calories a day to lose about \n" +
                    "two pounds a week. \n" +
                    "\n" +
                    "-Eat a high-protein breakfast.\n" +
                    "-Avoid sugary drinks and fruit juice. \n" +
                    "-Drink water a half hour before meals.\n" +
                    "-Choose weight loss-friendly foods.\n" +
                    "-Eat soluble fiber.\n" +
                    "-Drink coffee or tea.\n" +
                    "-Eat mostly whole, unprocessed foods.\n" +
                    "-Eat your food slowly.\n" +
                    "\n" +
                    "Try to eat more in the morning and less in afternoon and evening. Try to burn the calories by doing physical activities\n" +
                    "or exercises.");
            exercise.setText("Consider adding physical activity after reaching a minimum of 10 percent weight-loss goal. What you can try in the first \n" +
                    "place: \n" +
                    "\n" +
                    "-Aerobic Workouts to Lose Weight\n" +
                    "-Running is cardiovascular activity. It will help. \n" +
                    "-Swimming\n" +
                    "-Riding a bike \n" +
                    "-Brisk walking. \n" +
                    "-Weight lifting ; makes your heart rae increase and makes you breathe deep.\n" +
                    "\n" +
                    "Make a seven day plan and follow the suggestions mentioned above.");
        }




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
