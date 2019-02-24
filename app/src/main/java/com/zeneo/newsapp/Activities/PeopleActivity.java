package com.zeneo.newsapp.Activities;

import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zeneo.newsapp.R;
import com.zeneo.newsapp.util.GetResFromApi;
import com.zeneo.newsapp.util.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class PeopleActivity extends AppCompatActivity {

    HttpHandler sh = new HttpHandler();
    String id;
    String url1,url2,url3,url4;
    ImageView pr;
    TextView nm,beo,kf,gr,bd,bp,pop;
    RecyclerView castmList,crewmList,casttList,crewtList;
    ViewPager imgpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        id = getIntent().getStringExtra("id");
        url1 = "https://api.themoviedb.org/3/person/"+id+"?api_key=5d173b53167711178472dc9d98603e31&language=en-US";
        url2 = "https://api.themoviedb.org/3/person/"+id+"/movie_credits?api_key=5d173b53167711178472dc9d98603e31&language=en-US";
        url3 = "https://api.themoviedb.org/3/person/"+id+"/tv_credits?api_key=5d173b53167711178472dc9d98603e31&language=en-US";
        url4 = "https://api.themoviedb.org/3/person/"+id+"/images?api_key=5d173b53167711178472dc9d98603e31";

        linkViews();
        getDetails(url1);
        setImagesSize();

    }

    public void linkViews(){
        pr = (ImageView)findViewById(R.id.people_poster);
        nm = (TextView)findViewById(R.id.people_name);
        beo = (TextView)findViewById(R.id.people_beo);
        kf = (TextView)findViewById(R.id.people_kf);
        gr = (TextView)findViewById(R.id.people_gr);
        bd = (TextView)findViewById(R.id.people_birthd);
        bp = (TextView)findViewById(R.id.people_birthp);
        pop = (TextView)findViewById(R.id.people_pop);
        castmList = (RecyclerView) findViewById(R.id.castm_list);
        crewmList = (RecyclerView) findViewById(R.id.crewm_list);
        casttList = (RecyclerView) findViewById(R.id.castt_list);
        crewtList = (RecyclerView) findViewById(R.id.crewt_list);
        imgpager = (ViewPager) findViewById(R.id.people_img_slider);
    }


    public void getDetails(String url ){

        new GetResFromApi.getPersonalInfo(pr,nm,beo,kf,gr,bd,bp,pop,getApplicationContext(),url).execute();
        GetResFromApi res1 = new GetResFromApi(castmList,getApplicationContext(),url2,"castm","hori",false);
        res1.getDetails();
        GetResFromApi res2 = new GetResFromApi(casttList,getApplicationContext(),url3,"castt","hori",false);
        res2.getDetails();
        GetResFromApi res3 = new GetResFromApi(crewmList,getApplicationContext(),url2,"crewm","hori",false);
        res3.getDetails();
        GetResFromApi res4 = new GetResFromApi(crewtList,getApplicationContext(),url3,"crewt","hori",false);
        res4.getDetails();
        GetResFromApi res5 = new GetResFromApi(getApplicationContext(),imgpager,url4,getWindowManager());
        res5.setProfile(true);
        res5.getImagesforSlider();

    }

    public void setImagesSize(){
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size.x;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int viewPagerWidth = width;
        float viewPagerHeight = (float) (viewPagerWidth * 1.78)/3.1f;

        params.width = viewPagerWidth;
        params.height = (int) viewPagerHeight;

        imgpager.setLayoutParams(params);

    }

}
