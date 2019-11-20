package com.example.handeddown.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.handeddown.R;

public class AddMemberActivity extends AppCompatActivity {
    ImageView btn_backB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        btn_backB=findViewById(R.id.btn_back_newMember);
        btn_backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMemberActivity.this,AddFriendActivity.class);
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
