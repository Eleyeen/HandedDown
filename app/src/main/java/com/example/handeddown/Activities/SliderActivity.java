package com.example.handeddown.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.example.handeddown.Adapters.ViewPagerAdapter;
import com.example.handeddown.R;

public class SliderActivity extends AppCompatActivity {
    ViewPager viewPager ;
    TabLayout tabLayout;
    private int dotscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        tabLayout = findViewById(R.id.tab_dots);
        viewPager =findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(SliderActivity.this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
        dotscount = viewPagerAdapter.getCount();



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position==viewPager.getAdapter().getCount()-1){
                    Intent reg = new Intent(SliderActivity.this,SignUpActivity.class);
                    startActivity(reg);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}

