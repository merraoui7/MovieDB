package com.zeneo.newsapp.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zeneo.newsapp.DATABASE.DataContract;
import com.zeneo.newsapp.DATABASE.DataHelper;
import com.zeneo.newsapp.R;
import com.zeneo.newsapp.util.GetResFromApi;

import static com.zeneo.newsapp.DATABASE.DataContract.FavoriteEntry.COLUMN_NAME_MOV_ID;

public class TVShowsActivity extends AppCompatActivity {

    ImageView bd,pr;
    TextView tt,st,rl,olg,rt,pp,nos,noe,gr,desc;
    String url,url1,url2,url3,url4,url5;
    String id;
    RecyclerView recList,simList,seasList;
    ViewPager vidPager,imgPager;
    FragmentManager fm;
    FragmentTransaction ft;
    Bundle bundle;
    ImageView favor,watchlist;
    GetResFromApi getResFromApi;
    SQLiteDatabase db,db2;
    DataHelper.FavoriteHelper sqlfavorite;
    DataHelper.WatchListHelper sqlwatchlist;
    DataHelper dataHelper;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows);

        id = getIntent().getStringExtra("id");
        url = "https://api.themoviedb.org/3/tv/"+id+"?api_key=5d173b53167711178472dc9d98603e31&language=en-US";
        url1 = "https://api.themoviedb.org/3/tv/"+id+"/recommendations?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1";
        url2 = "https://api.themoviedb.org/3/tv/"+id+"/similar?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1";
        url3 = "https://api.themoviedb.org/3/tv/"+id+"/images?api_key=5d173b53167711178472dc9d98603e31&language=en%2Cnull";
        url4 = "https://api.themoviedb.org/3/tv/"+id+"/videos?api_key=5d173b53167711178472dc9d98603e31&language=en-US";
        url5 = "https://api.themoviedb.org/3/tv/"+id+"";



        linkViews();
        getData();
        setImagesSize();

        sqlfavorite = new DataHelper.FavoriteHelper(this);
        db = sqlfavorite.getWritableDatabase();
        sqlwatchlist = new DataHelper.WatchListHelper(this);
        db2 = sqlwatchlist.getWritableDatabase();



        boolean isExist = sqlfavorite.CheckIsDataAlreadyInDBorNot(DataContract.FavoriteEntry.TABLE_NAME,id);
        if (isExist){
            favor.setImageResource(R.drawable.favorite);
        }else {
            favor.setImageResource(R.drawable.favorite_desactive);
        }

        boolean count3 = sqlwatchlist.CheckIsDataAlreadyInDBorNot(DataContract.WatchListEntry.TABLE_NAME,id);
        if (count3){
            watchlist.setImageResource(R.drawable.watchlist_added);
        }else {
            watchlist.setImageResource(R.drawable.watchlist_add);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();



        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isExist = sqlfavorite.CheckIsDataAlreadyInDBorNot(DataContract.FavoriteEntry.TABLE_NAME,id);
                title = tt.getText().toString();
                String imageurl = getResFromApi.getImage_url();
                if (isExist){
                    sqlfavorite.delete(DataContract.FavoriteEntry.TABLE_NAME,id,"TV");
                    favor.setImageResource(R.drawable.favorite_desactive);
                }else {
                    sqlfavorite.insert(DataContract.FavoriteEntry.TABLE_NAME,title,imageurl,id,"TV");
                    favor.setImageResource(R.drawable.favorite);
                }
            }
        });
        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean count3 = sqlwatchlist.CheckIsDataAlreadyInDBorNot(DataContract.WatchListEntry.TABLE_NAME,id);

                title = tt.getText().toString();
                String imageurl = getResFromApi.getImage_url();
                if (count3){
                    sqlwatchlist.delete(DataContract.WatchListEntry.TABLE_NAME,id,"TV");
                    watchlist.setImageResource(R.drawable.watchlist_add);
                }else {
                    sqlwatchlist.insert(DataContract.WatchListEntry.TABLE_NAME,title,imageurl,id,"TV");
                    watchlist.setImageResource(R.drawable.watchlist_added);
                }
            }
        });

    }

    public void linkViews(){
        bd = (ImageView)findViewById(R.id.tv_backdrop);
        pr = (ImageView)findViewById(R.id.tv_poster);
        tt = (TextView)findViewById(R.id.tv_title);
        st = (TextView)findViewById(R.id.tv_status);
        rl = (TextView)findViewById(R.id.tv_release);
        olg = (TextView)findViewById(R.id.tv_olg);
        rt = (TextView)findViewById(R.id.tv_runtime);
        pp = (TextView)findViewById(R.id.tv_pop);
        nos = (TextView)findViewById(R.id.tv_ns);
        noe = (TextView)findViewById(R.id.tv_ne);
        gr = (TextView)findViewById(R.id.tv_genre);
        desc = (TextView)findViewById(R.id.tv_desc);
        recList = (RecyclerView)findViewById(R.id.tv_rec_list);
        simList = (RecyclerView)findViewById(R.id.tv_sim_list);
        seasList = (RecyclerView)findViewById(R.id.season_list);
        imgPager = (ViewPager)findViewById(R.id.tv_img_slider);
        vidPager = (ViewPager)findViewById(R.id.tv_vid_slider);
        favor = (ImageView)findViewById(R.id.tv_favor);
        watchlist = (ImageView)findViewById(R.id.watch_list_tv);
    }



    public void getData(){
        getResFromApi = new GetResFromApi(TVShowsActivity.this,url,bd,pr,tt,st,rl,olg,rt,pp,nos,noe,gr,desc);
        getResFromApi.getTvData();

        new GetResFromApi(recList,TVShowsActivity.this,url1,"TV","hori",false).getDetails();
        new GetResFromApi(simList,TVShowsActivity.this,url2,"TV","hori",false).getDetails();
        new GetResFromApi(TVShowsActivity.this,imgPager,url3,getWindowManager()).getImagesforSlider();
        new GetResFromApi(TVShowsActivity.this,vidPager,url4,getWindowManager()).getVideosForSliders();
        new GetResFromApi(seasList,TVShowsActivity.this,url,"seasons","hori",false).getDetails();

    }

    public void setImagesSize(){
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size.x;
        bd.setMaxHeight((int) (width*0.7));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int viewPagerWidth = width;
        float viewPagerHeight = (float) (viewPagerWidth * 1.78)/3.1f;
        float viewPagerHeight2 = (float) (viewPagerWidth * 1.78)/3.1f;

        params.width = viewPagerWidth;
        params.height = (int) viewPagerHeight;
        params2.width = viewPagerWidth;
        params2.height = (int) viewPagerHeight2;

        imgPager.setLayoutParams(params);
        vidPager.setLayoutParams(params2);
    }

    public void getEpisodes (int i){
        new GetResFromApi(seasList,TVShowsActivity.this,
                "https://api.themoviedb.org/3/tv/"+id+"/season/"+i+"?api_key=5d173b53167711178472dc9d98603e31&language=en-US"
                ,"episodes","hori",false).getDetails();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        db2.close();
    }
}
