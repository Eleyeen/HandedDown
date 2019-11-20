package com.example.handeddown.Activities;

import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.handeddown.Adapters.RecyclerView_Adapter;
import com.example.handeddown.Fragments.PrivateFragment;
import com.example.handeddown.Fragments.ProfileFragment;
import com.example.handeddown.Fragments.PublicFragment;
import com.example.handeddown.Fragments.UpdateFragment;
import com.example.handeddown.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class MyDiscoverRecipesActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivProfile,ivPublicRecipe , ivPrivate , ivUpload;
    RecyclerView recyclerView;
    android.support.v7.widget.SearchView searchView2;
    RecyclerView_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_discover_recipes);
        ivProfile = findViewById(R.id.iv_profile);
        ivPublicRecipe =findViewById(R.id.iv_publi_recipe);
        ivPrivate = findViewById(R.id.iv_privates_recipie);
        ivUpload = findViewById(R.id.iv_upload);

        ivPublicRecipe.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        ivPrivate.setOnClickListener(this);
        ivUpload.setOnClickListener(this);
        setFragment(new PublicFragment());


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

    private void  setFragment (Fragment fragment){


        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.iv_publi_recipe:
        ivPublicRecipe.setImageResource(R.mipmap.publichdpi);
        ivProfile.setImageResource(R.mipmap.profileicon);
        ivPrivate.setImageResource(R.mipmap.myrecipes);
        ivUpload.setImageResource(R.mipmap.updateicon);

        Fragment fragment1 = new PublicFragment();
        setFragment(fragment1);
        break;


        case R.id.iv_upload:
        ivUpload.setImageResource(R.mipmap.upload);
        ivPublicRecipe.setImageResource(R.mipmap.groupicon);
        ivProfile.setImageResource(R.mipmap.profileicon);
        ivPrivate.setImageResource(R.mipmap.myrecipes);
        Fragment fragment4 = new UpdateFragment();
        setFragment(fragment4);
        break;


        case R.id.iv_profile:

        ivProfile.setImageResource(R.mipmap.profile);
        ivPublicRecipe.setImageResource(R.mipmap.groupicon);
        ivPrivate.setImageResource(R.mipmap.myrecipes);
        ivUpload.setImageResource(R.mipmap.updateicon);


        Fragment fragment = new ProfileFragment();
        setFragment(fragment);
        break;

        case R.id.iv_privates_recipie:

        ivPrivate.setImageResource(R.mipmap.privateroup);
        ivPublicRecipe.setImageResource(R.mipmap.groupicon);
        ivProfile.setImageResource(R.mipmap.profileicon);
        ivUpload.setImageResource(R.mipmap.updateicon);
            Fragment fragmentw = new PrivateFragment();
            setFragment(fragmentw);


            break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
