package com.zeneo.newsapp.Activities;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeneo.newsapp.R;
import com.zeneo.newsapp.util.GetDetailsFromApi;

public class MoviesActivity extends AppCompatActivity {

    ImageView bd,pr;
    TextView tt,st,rl,olg,rt,bg,gr,rv,desc;
    GetDetailsFromApi getDetailsFromApi;
    String id;
    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        id = getIntent().getStringExtra("id");
        url="https://api.themoviedb.org/3/movie/"+id+"?api_key=5d173b53167711178472dc9d98603e31&language=en-US";
        linkViews();
        setImagesSize();


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
        getDetailsFromApi = new GetDetailsFromApi(bd,pr,tt,st,rl,olg,rt,bg,gr,rv);
        getDetailsFromApi.setContext(getApplicationContext());
        getDetailsFromApi.setUrl(url);
        getDetailsFromApi.applique();
    }

    public void setImagesSize(){
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size.x;
        bd.setMaxHeight((int) (width/0.7));

    }

}
