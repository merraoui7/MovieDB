package com.zeneo.newsapp.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zeneo.newsapp.Adapters.ViewPagerAdapter;
import com.zeneo.newsapp.Fragments.CelebsFragment;
import com.zeneo.newsapp.Fragments.HomeFragment;
import com.zeneo.newsapp.Fragments.MoviesFragment;
import com.zeneo.newsapp.Fragments.NewsFragment;
import com.zeneo.newsapp.Fragments.TvFragment;
import com.zeneo.newsapp.R;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter fragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.tablt);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        fragAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragAdapter.addFrag(new HomeFragment(),"Home");
        fragAdapter.addFrag(new MoviesFragment(),"Movies");
        fragAdapter.addFrag(new TvFragment(),"TV Shows");
        fragAdapter.addFrag(new NewsFragment(),"Trending");
        fragAdapter.addFrag(new CelebsFragment(),"People");


        viewPager.setAdapter(fragAdapter);
        tabLayout.setupWithViewPager(viewPager);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("scrolled",String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) HomeActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(HomeActivity.this.getComponentName()));

        }
        return super.onCreateOptionsMenu(menu);
    }
    public void setCurrentPage(int i){
        viewPager.setCurrentItem(i);
    }
}
