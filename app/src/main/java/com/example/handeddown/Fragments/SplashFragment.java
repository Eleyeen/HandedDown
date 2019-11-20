package com.example.handeddown.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.handeddown.Activities.CreateAccountActivity;
import com.example.handeddown.Activities.MyDiscoverRecipesActivity;
import com.example.handeddown.Activities.SignUpActivity;
import com.example.handeddown.R;
import com.example.handeddown.Activities.SliderActivity;


public class SplashFragment extends Fragment {
    View parentView;

    public static boolean splashBoolean = true;
    ProgressBar pbSplashScreen;
    int i = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_splash, container, false);
        pbSplashScreen = parentView.findViewById(R.id.pb_splash);



        new CountDownTimer(500, 100) {

            public void onTick(long millisUntilFinished) {


                i = i+1;
                pbSplashScreen.setProgress(i);

            }
            public void onFinish() {




                SharedPreferences prefsd = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Boolean yourLock = prefsd.getBoolean("lockedP", false);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Boolean yourLocked = prefs.getBoolean("locked", false);
                if(yourLocked){
                    Intent intent = new Intent(getActivity(), MyDiscoverRecipesActivity
                            .class);
                    startActivity(intent);

                }else if(yourLock){
                    Intent intent = new Intent(getActivity(), MyDiscoverRecipesActivity
                            .class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(getActivity(), SliderActivity.class);
                    startActivity(intent);

                }



            }

        }.start();

        return parentView;
    }
}
