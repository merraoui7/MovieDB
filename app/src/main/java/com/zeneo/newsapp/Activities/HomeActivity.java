package com.zeneo.newsapp.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
    LinearLayout sLayout,mLayout;
    RecyclerView recyclerView1 ,recyclerView2 ,recyclerView3 ;
    SearchingOP op1,op2,op3;
    String url1 = "https://api.themoviedb.org/3/search/movie?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1&include_adult=false&query=";
    String url2 = "https://api.themoviedb.org/3/search/tv?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1&query=";
    String url3 = "https://api.themoviedb.org/3/search/person?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1&include_adult=false&query=";
    TextView textView,seemore1,seemore2,seemore3;
    List<Search> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.tablt);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        sLayout = (LinearLayout)findViewById(R.id.search_layout);
        mLayout = (LinearLayout)findViewById(R.id.main_layout);
        recyclerView1 = (RecyclerView)findViewById(R.id.list_mov_search);
        recyclerView2 = (RecyclerView)findViewById(R.id.list_tv_search);
        recyclerView3 = (RecyclerView)findViewById(R.id.list_peo_search);
        textView =(TextView)findViewById(R.id.simpletxtview);
        seemore1 = (TextView)findViewById(R.id.seemoremovie);
        seemore2 = (TextView)findViewById(R.id.seemoretv);
        seemore3 = (TextView)findViewById(R.id.seemorepeople);


        fragAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragAdapter.addFrag(new HomeFragment(),"Home");
        fragAdapter.addFrag(new MoviesFragment(),"Movies");
        fragAdapter.addFrag(new TvFragment(),"TV Shows");
        fragAdapter.addFrag(new NewsFragment(),"Trending");
        fragAdapter.addFrag(new CelebsFragment(),"People");

        op1 = new SearchingOP(recyclerView1,url1,getApplicationContext(),"movie",textView,seemore1);
        op2 = new SearchingOP(recyclerView2,url2,getApplicationContext(),"TV",textView,seemore2);
        op3 = new SearchingOP(recyclerView3,url3,getApplicationContext(),"people",textView,seemore3);


        viewPager.setAdapter(fragAdapter);
        tabLayout.setupWithViewPager(viewPager);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView;
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
                    op1.setUrl(url1+URLEncoder.encode(query, "UTF-8"));
                    op1.getDetails();
                    op2.setUrl(url2+URLEncoder.encode(query, "UTF-8"));
                    op2.getDetails();
                    op3.setUrl(url3+URLEncoder.encode(query, "UTF-8"));
                    op3.getDetails();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null){
                    try {
                        op1.setUrl(url1+URLEncoder.encode(newText, "UTF-8"));
                        op1.getDetails();
                        op2.setUrl(url2+URLEncoder.encode(newText, "UTF-8"));
                        op2.getDetails();
                        op3.setUrl(url3+URLEncoder.encode(newText, "UTF-8"));
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
    public ViewPager getViewPager(){
        return viewPager;
    }


}
