package com.example.handeddown.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.handeddown.Fragments.PrivateFragment;
import com.example.handeddown.R;

public class AddFriendActivity extends AppCompatActivity {
    ImageView btn_Back;
    Button btn_add;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        btn_add=findViewById(R.id.button_add_Friend);
        btn_Back=findViewById(R.id.back_image_addMember);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFriendActivity.this,MyDiscoverRecipesActivity.class);
                startActivity(intent);
//                android.app.FragmentTransaction fr = getFragmentManager().beginTransaction();
//                fr.replace(R.id.container,new MyDiscoverRecipesActivity());
//                fr.commit();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFriendActivity.this,AddMemberActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        this.finish();
        super.onBackPressed();
    }
}
