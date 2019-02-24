package com.zeneo.newsapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.zeneo.newsapp.Adapters.ViewPagerAdapter;
import com.zeneo.newsapp.Fragments.FavorFragment;
import com.zeneo.newsapp.Fragments.FavorFragment2;
import com.zeneo.newsapp.R;

public class FavorActivity extends AppCompatActivity {

    String toolbartitle;
    ViewPager pager;
    TabLayout tabLayout;
    ViewPagerAdapter pagerAdapter;
    Bundle bundle1,bundle2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        toolbartitle = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(toolbartitle);
        pager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tablt);

        setupViewPager();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewPager();
    }

    public void setupViewPager(){
        FavorFragment favorFragment = new FavorFragment();
        FavorFragment2 favorFragment2 = new FavorFragment2();
        bundle1 = new Bundle();
        bundle1.putString("type","movie");
        bundle1.putString("database",toolbartitle);
        favorFragment.setArguments(bundle1);
        bundle2 = new Bundle();
        bundle2.putString("type","TV");
        bundle2.putString("database",toolbartitle);
        favorFragment2.setArguments(bundle2);
        Log.i("TAG","resumed");
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFrag(favorFragment,"Movies");
        pagerAdapter.addFrag(favorFragment2,"TV SHOWS");

        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
    }

}
