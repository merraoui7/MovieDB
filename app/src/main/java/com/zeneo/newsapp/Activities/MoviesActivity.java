package com.zeneo.newsapp.Activities;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zeneo.newsapp.DATABASE.DataContract;
import com.zeneo.newsapp.DATABASE.DataHelper;
import com.zeneo.newsapp.R;
import com.zeneo.newsapp.util.GetDetailsFromApi;
import com.zeneo.newsapp.util.GetResFromApi;

import static com.zeneo.newsapp.DATABASE.DataContract.FavoriteEntry.COLUMN_NAME_MOV_ID;

public class MoviesActivity extends Activity {

    ImageView bd,pr;
    TextView tt,st,rl,olg,rt,bg,gr,rv,desc;
    GetDetailsFromApi getDetailsFromApi;
    GetResFromApi getResFromApi,getResFromApi1;
    RecyclerView recList,simList;
    ViewPager viewPager,vidViewPager;
    String id;
    String url1,url2,url3,url4,url5;
    SQLiteDatabase db,db2;
    DataHelper.FavoriteHelper sqlfavorite;
    DataHelper.WatchListHelper sqlwatchlist;
    ImageView favor,watchlist;
    String title,imgurl,type;
    DataHelper dataHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        Activity activity = new MainActivity();

        id = getIntent().getStringExtra("id");
        url1="https://api.themoviedb.org/3/movie/"+id+"?api_key=5d173b53167711178472dc9d98603e31&language=en-US";
        url2="https://api.themoviedb.org/3/movie/"+id+"/recommendations?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1";
        url3="https://api.themoviedb.org/3/movie/"+id+"/similar?api_key=5d173b53167711178472dc9d98603e31&language=en-US&page=1";
        url4="https://api.themoviedb.org/3/movie/"+id+"/images?api_key=5d173b53167711178472dc9d98603e31&language=en-US&include_image_language=null";
        url5="https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=5d173b53167711178472dc9d98603e31&language=en-US";
        linkViews();
        setImagesSize();
        getData();

        sqlfavorite = new DataHelper.FavoriteHelper(this);
        db = sqlfavorite.getWritableDatabase();
        sqlwatchlist = new DataHelper.WatchListHelper(this);
        db2 = sqlwatchlist.getWritableDatabase();





    }

    @Override
    protected void onStart() {
        super.onStart();
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

        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isExist = sqlfavorite.CheckIsDataAlreadyInDBorNot(DataContract.FavoriteEntry.TABLE_NAME,id);
                title = tt.getText().toString();
                imgurl = getDetailsFromApi.getImageurl();
                if (isExist){
                    sqlfavorite.delete(DataContract.FavoriteEntry.TABLE_NAME,id,"movie");
                    favor.setImageResource(R.drawable.favorite_desactive);
                }else {
                    sqlfavorite.insert(DataContract.FavoriteEntry.TABLE_NAME,title,imgurl,id,"movie");
                    favor.setImageResource(R.drawable.favorite);
                }
            }
        });
        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean count3 = sqlwatchlist.CheckIsDataAlreadyInDBorNot(DataContract.WatchListEntry.TABLE_NAME,id);

                title = tt.getText().toString();
                imgurl = getDetailsFromApi.getImageurl();
                if (count3){
                   sqlwatchlist.delete(DataContract.WatchListEntry.TABLE_NAME,id,"movie");
                    watchlist.setImageResource(R.drawable.watchlist_add);
                }else {
                    sqlwatchlist.insert(DataContract.WatchListEntry.TABLE_NAME,title,imgurl,id,"movie");
                    watchlist.setImageResource(R.drawable.watchlist_added);
                }
            }
        });

    }

    public void linkViews(){
        bd = (ImageView)findViewById(R.id.mov_backdrop);
        pr = (ImageView)findViewById(R.id.mov_poster);
        tt = (TextView)findViewById(R.id.title_mov);
        st = (TextView)findViewById(R.id.mov_status);
        rl = (TextView)findViewById(R.id.mov_release);
        olg = (TextView)findViewById(R.id.mov_olg);
        rt = (TextView)findViewById(R.id.mov_runtime);
        bg = (TextView)findViewById(R.id.mov_budget);
        desc = (TextView)findViewById(R.id.mov_desc);
        gr = (TextView)findViewById(R.id.mov_genre);
        rv = (TextView)findViewById(R.id.mov_revenue);
        recList = (RecyclerView)findViewById(R.id.mov_rec_list);
        simList = (RecyclerView)findViewById(R.id.mov_sim_list);
        viewPager = (ViewPager)findViewById(R.id.mov_img_slider);
        vidViewPager = (ViewPager)findViewById(R.id.mov_vid_slider);
        favor = (ImageView)findViewById(R.id.mov_favor);
        watchlist = (ImageView)findViewById(R.id.watch_list);
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

        viewPager.setLayoutParams(params);
        vidViewPager.setLayoutParams(params2);
    }

    public void getData(){
        getDetailsFromApi = new GetDetailsFromApi(bd,pr,tt,st,rl,olg,rt,bg,desc,gr,rv);
        getDetailsFromApi.setContext(MoviesActivity.this);
        getDetailsFromApi.setUrl(url1);
        getDetailsFromApi.applique();
        getResFromApi = new GetResFromApi(recList,MoviesActivity.this,url2,"movierec","hori",false);
        getResFromApi.getDetails();
        getResFromApi1 = new GetResFromApi(simList,MoviesActivity.this,url3,"movierec","hori",false);
        getResFromApi1.getDetails();
        GetResFromApi getResFromApi2 = new GetResFromApi(getApplicationContext(),viewPager,url4,getWindowManager());
        getResFromApi2.getImagesforSlider();
        GetResFromApi getResFromApi3 = new GetResFromApi(getApplicationContext(),vidViewPager,url5,getWindowManager());
        getResFromApi3.getVideosForSliders();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        db2.close();
    }
}
