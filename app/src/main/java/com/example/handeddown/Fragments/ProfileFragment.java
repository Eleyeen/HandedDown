package com.example.handeddown.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.handeddown.Activities.CreateAccountActivity;
import com.example.handeddown.Activities.SignUpActivity;
import com.example.handeddown.Adapters.RecyclerView_Adapter;
import com.example.handeddown.Models.Recycle_View_Model;
import com.example.handeddown.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private android.widget.SearchView searchView;
    RelativeLayout invite_friends;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        Button btn =(Button)view.findViewById(R.id.logout_profilbtn);
        invite_friends = view.findViewById(R.id.relative_invite);

        invite_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);

                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "Enter the Friend number ");
                smsIntent.putExtra("sms_body","http://divergense.com/recipes/public/post/id");
                startActivity(smsIntent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                prefs.edit().putBoolean("locked", false).apply();

                SharedPreferences prefsd = PreferenceManager.getDefaultSharedPreferences(getActivity());
                prefsd.edit().putBoolean("lockedP", false).apply();

                getActivity().finishAffinity();


                Intent intent= new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
customActionBar();
   return view;
    }

    public void customActionBar() {

        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setElevation(0);

        mActionBar.hide();

    }



}
