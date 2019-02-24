package com.zeneo.newsapp.Activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zeneo.newsapp.Adapters.SearchListAdapter;
import com.zeneo.newsapp.Adapters.ViewPagerAdapter;
import com.zeneo.newsapp.Fragments.CelebsFragment;
import com.zeneo.newsapp.Fragments.HomeFragment;
import com.zeneo.newsapp.Fragments.MoviesFragment;
import com.zeneo.newsapp.Fragments.NewsFragment;
import com.zeneo.newsapp.Fragments.TvFragment;
import com.zeneo.newsapp.Models.Search;
import com.zeneo.newsapp.R;
import com.zeneo.newsapp.util.HttpHandler;
import com.zeneo.newsapp.util.SearchingOP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter fragAdapter;
    LinearLayout sLayout, mLayout;
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    SearchingOP op1, op2, op3;
    String url1 = "https://api.themoviedb.org/3/search/movie?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1&include_adult=false&query=";
    String url2 = "https://api.themoviedb.org/3/search/tv?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1&query=";
    String url3 = "https://api.themoviedb.org/3/search/person?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1&include_adult=false&query=";
    TextView textView, seemore1, seemore2, seemore3;
    List<Search> list = new ArrayList<>();
    private DrawerLayout mDrawerLayout;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        tabLayout = (TabLayout) findViewById(R.id.tablt);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        sLayout = (LinearLayout) findViewById(R.id.search_layout);
        mLayout = (LinearLayout) findViewById(R.id.main_layout);
        recyclerView1 = (RecyclerView) findViewById(R.id.list_mov_search);
        recyclerView2 = (RecyclerView) findViewById(R.id.list_tv_search);
        recyclerView3 = (RecyclerView) findViewById(R.id.list_peo_search);
        textView = (TextView) findViewById(R.id.simpletxtview);
        seemore1 = (TextView) findViewById(R.id.seemoremovie);
        seemore2 = (TextView) findViewById(R.id.seemoretv);
        seemore3 = (TextView) findViewById(R.id.seemorepeople);

        MoviesFragment fragment1 = new MoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url","https://api.themoviedb.org/3/discover/movie?api_key=5d173b53167711178472dc9d98603e31&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=");
        fragment1.setArguments(bundle);


        fragAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragAdapter.addFrag(new HomeFragment(), "Home");
        fragAdapter.addFrag(fragment1, "Movies");
        fragAdapter.addFrag(new TvFragment(), "TV Shows");
        fragAdapter.addFrag(new NewsFragment(), "Trending");
        fragAdapter.addFrag(new CelebsFragment(), "People");

        op1 = new SearchingOP(recyclerView1, url1, getApplicationContext(), "movie", textView, seemore1);
        op2 = new SearchingOP(recyclerView2, url2, getApplicationContext(), "TV", textView, seemore2);
        op3 = new SearchingOP(recyclerView3, url3, getApplicationContext(), "people", textView, seemore3);


        viewPager.setAdapter(fragAdapter);
        tabLayout.setupWithViewPager(viewPager);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int ids = menuItem.getItemId();

                        switch (ids) {
                            case R.id.nav_home:
                                menuItem.setChecked(true);
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.nav_movies:
                                menuItem.setChecked(true);
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.nav_tv:
                                menuItem.setChecked(true);
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.nav_trend:
                                menuItem.setChecked(true);
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.nav_people:
                                menuItem.setChecked(true);
                                viewPager.setCurrentItem(4);
                                break;
                            case R.id.nav_favor:
                                Intent intent = new Intent(HomeActivity.this,FavorActivity.class);
                                intent.putExtra("title","Favorite");
                                startActivity(intent);
                                break;
                            case R.id.nav_list:
                                Intent intent2 = new Intent(HomeActivity.this,FavorActivity.class);
                                intent2.putExtra("title","Watch List");
                                startActivity(intent2);
                                break;
                        }
                        // set item as selected to persist highlight

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setNavItemCheched(position,navigationView);
            }

            @Override
            public void onPageSelected(int position) {
                setNavItemCheched(position,navigationView);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView;
        searchView = (SearchView) searchItem.getActionView();
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                sLayout.setVisibility(View.VISIBLE);
                mLayout.setVisibility(View.INVISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                sLayout.setVisibility(View.INVISIBLE);
                mLayout.setVisibility(View.VISIBLE);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textView.setVisibility(View.GONE);
                try {
                    op1.setUrl(url1 + URLEncoder.encode(query, "UTF-8"));
                    op1.getDetails();
                    op2.setUrl(url2 + URLEncoder.encode(query, "UTF-8"));
                    op2.getDetails();
                    op3.setUrl(url3 + URLEncoder.encode(query, "UTF-8"));
                    op3.getDetails();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    try {
                        op1.setUrl(url1 + URLEncoder.encode(newText, "UTF-8"));
                        op1.getDetails();
                        op2.setUrl(url2 + URLEncoder.encode(newText, "UTF-8"));
                        op2.getDetails();
                        op3.setUrl(url3 + URLEncoder.encode(newText, "UTF-8"));
                        op3.getDetails();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                }

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setNavItemCheched(int position,NavigationView navigationView){
        switch (position){
            case 0:
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case 1:
                navigationView.setCheckedItem(R.id.nav_movies);
                break;
            case 2:
                navigationView.setCheckedItem(R.id.nav_tv);
                break;
            case 3:
                navigationView.setCheckedItem(R.id.nav_trend);
                break;
            case 4:
                navigationView.setCheckedItem(R.id.nav_people);
                break;

        }
    }




}
