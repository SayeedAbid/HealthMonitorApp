package com.example.healthmonitorapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FAQFragment extends Fragment {

    TextView FAQ,faq,questions;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_faq,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FAQ = view.findViewById(R.id.FAQ);
        questions = view.findViewById(R.id.faq);
        questions.setText("1.What do I need to have to setup my account? \n\n" +
                "-There is an menu in the appBar Profile,after logging in, you can setup your profile or edit your information there. \n\n" +
                "2.What is the difference between the Mobile App and the Mobile Web App? \n\n" +
                "-A native mobile app is one that is installed directly on the smartphone and can work, in most cases, with no internet connectivity depending on the nature of the app. A web app works via a web browser on the smartphone but requires either a cell signal or wi-fi to function and can access specific hardware like GPS.\n\n" +
                "3.Is my phone supported?\n\n" +
                "- This app is supported by Android mobile phones.\n\n" +
                "4.Is the Mobile App secure?\n\n" +
                "- Yes, it is.\n\n" +
                "5.What is the basic function of this Mobile App?\n\n" +
                "- This app takes data from the user, and with that it suggests diet and exercise suggestions for a healthy living, keeps notified about the health.\n\n" +
                "6.Does it cost anything to use the Mobile App?\n\n" +
                "- No,it's free of charge till now.\n\n" +
                "7.How current is the account information I see in the Mobile App?\n\n" +
                "- It saves the information that you have provided and based on that it provides plans. If you change the infos, the plans will change too.\n\n" +
                "8.How do I find your offices and payment locations?\n\n" +
                "- Find us in the 'contact us' menu.\n\n" +
                "9. Will this app work in my iPhone?\n\n" +
                "- No, it's an android app.\n\n" +
                "10.Can I link all my accounts together?\n\n" +
                "- No.\n");
        ;

    }
}
