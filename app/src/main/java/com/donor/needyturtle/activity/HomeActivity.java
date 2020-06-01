package com.donor.needyturtle.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.donor.needyturtle.R;
import com.donor.needyturtle.utils.BannerAdapter;
import com.rd.PageIndicatorView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private PageIndicatorView pageIndicatorView;

    private ArrayList<Integer> listBanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager viewPager = findViewById(R.id.viewpager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        RelativeLayout rel_register = findViewById(R.id.rel_register);
        RelativeLayout rel_search_donor = findViewById(R.id.rel_search_donor);
        TextView tv_terms_condition = findViewById(R.id.tv_terms_condition);


        listBanner = new ArrayList<>();
        listBanner.add(R.mipmap.banner1);
        listBanner.add(R.mipmap.banner2);
        listBanner.add(R.mipmap.banner3);

        pageIndicatorView.setCount(listBanner.size());

        BannerAdapter bannerAdapter = new BannerAdapter(HomeActivity.this, listBanner);
        viewPager.setAdapter(bannerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rel_register.setOnClickListener(v -> {


        });

        rel_search_donor.setOnClickListener(v -> {



        });

        tv_terms_condition.setOnClickListener(v -> {


        });



    }
}