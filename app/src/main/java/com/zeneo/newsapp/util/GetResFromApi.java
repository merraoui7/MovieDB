package com.zeneo.newsapp.util;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zeneo.newsapp.Adapters.PopularListAdapter;
import com.zeneo.newsapp.Adapters.RateListAdapter;
import com.zeneo.newsapp.Adapters.RecListAdapter;
import com.zeneo.newsapp.Models.Movies;
import com.zeneo.newsapp.Models.Videos;
import com.zeneo.newsapp.UI.ImageSliderAdapter;
import com.zeneo.newsapp.UI.VideoSliderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetResFromApi {



    private List<Movies> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context;
    private String url;
    private String media_type;
    private String listType;
    List<String> img_url = new ArrayList<>();
    private LinearLayout layout,layout2;
    private boolean isRefreshed = false, isOnHome = true;
    private String type;
    String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setRefreshed(boolean refreshed) {
        isRefreshed = refreshed;
    }



    public GetResFromApi(RecyclerView recyclerView, Context context, String url, String media_type, String listType, LinearLayout layout, LinearLayout layout2, String type) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.url = url;
        this.media_type = media_type;
        this.listType = listType;
        this.layout = layout;
        this.layout2 = layout2;
        this.type = type;
    }

    public GetResFromApi(RecyclerView recyclerView, Context context, String url, String media_type, String listType, boolean isOnHome) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.url = url;
        this.media_type = media_type;
        this.listType = listType;
        this.isOnHome = isOnHome;
    }

    public void getDetails (){
        new getMoviewsFromApi().execute();
    }

    public void clearData(){
        list.clear();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public class getMoviewsFromApi extends AsyncTask< Void , Void , Void > {
        HttpHandler sh = new HttpHandler();
        int postitionToScrool;
        // Making a request to url and getting response


        @Override
        protected Void doInBackground(Void... voids) {
            String jsonStr = sh.makeServiceCall(url);


            String titles;
            String imgurl;
            String rating;
            String desc;
            int id;
            if (jsonStr != null){
                switch (media_type){
                    case "movie":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("results");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("title");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id,media_type));
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        break;
                    case "TV":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("results");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("name");
                                imgurl = "https://image.tmdb.org/t/p/w500" + results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles, imgurl, id,media_type));
                            }

                        }

                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    case "people":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("results");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("name");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("profile_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id ,media_type));
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    case "topMovies":
                        try {

                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("results");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("title");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                rating = String.valueOf(results.getJSONObject(i).getDouble("vote_average"));
                                desc = results.getJSONObject(i).getString("overview");
                                list.add(new Movies(titles,imgurl,id,rating+"/10",desc,"movie"));
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    case "toptv":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("results");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("name");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                rating = String.valueOf(results.getJSONObject(i).getDouble("vote_average"));
                                desc = results.getJSONObject(i).getString("overview");
                                list.add(new Movies(titles,imgurl,id,rating+"/10",desc,"TV"));
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    case "movierec":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("results");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("title");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id,media_type));
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    case "TVrec":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("results");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("name");
                                imgurl = "https://image.tmdb.org/t/p/w500" + results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles, imgurl, id,media_type));
                            }

                        }

                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    case "seasons":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("seasons");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("name");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                int num = results.getJSONObject(i).getInt("season_number");
                                list.add(new Movies(titles,imgurl,id,"seasons",num));
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    case "episodes":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            String name = mainObject.getString("name");
                            JSONArray results = mainObject.getJSONArray("episodes");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = name +" : "+ results.getJSONObject(i).getString("name");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("still_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id,"episodes"));
                                Log.i("TAG",imgurl);
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    case "castm":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("cast");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("title");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id,media_type));
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        break;
                    case "castt":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("cast");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("name");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id,media_type));
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        break;
                    case "crewm":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("crew");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("title");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id,media_type));
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        break;
                    case "crewt":
                        try {
                            JSONObject mainObject = new JSONObject(jsonStr);
                            JSONArray results = mainObject.getJSONArray("crew");
                            for (int i = 0 ; i < results.length() ; i++){
                                titles = results.getJSONObject(i).getString("name");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id,media_type));
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        break;
                }






            }



            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isOnHome){
                if (!isRefreshed){
                    layout.setVisibility(View.INVISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                }
            }

            postitionToScrool =list.size()-6;



        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            PopularListAdapter adapter1 = new PopularListAdapter(list,context);
            RateListAdapter adapter2 = new RateListAdapter(list,context);
            if (!isRefreshed){
                if (listType.equals("hori")){
                    if (media_type.equals("movierec")||media_type.equals("TVrec")){
                        RecListAdapter adapter = new RecListAdapter(list,context);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayout.HORIZONTAL,false));
                        recyclerView.setAdapter(adapter);
                    }else {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayout.HORIZONTAL,false));
                        recyclerView.setAdapter(adapter1);
                    }

                }else if (listType.equals("grid")){
                    recyclerView.setLayoutManager(new GridLayoutManager(context,3));
                    recyclerView.setAdapter(adapter1);
                } else if (listType.equals("grid1")){
                    recyclerView.setLayoutManager(new GridLayoutManager(context,2));
                    recyclerView.setAdapter(adapter2);
                }
                if (isOnHome){
                    layout.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.INVISIBLE);
                }


            } else {

                recyclerView.setAdapter(adapter1);
                recyclerView.getLayoutManager().scrollToPosition(postitionToScrool);
                adapter1.notifyDataSetChanged();
            }



        }
    }


    public void getImagesforSlider(){
        new getImages().execute();
    }

    ViewPager viewPager;
    WindowManager windowManager;
    private boolean isProfile = false;

    public void setProfile(boolean profile) {
        isProfile = profile;
    }

    public GetResFromApi(Context context, ViewPager viewPager, String url, WindowManager windowManager) {
        this.context = context;
        this.viewPager = viewPager;
        this.url = url;
        this.windowManager = windowManager;
    }

    public class getImages extends AsyncTask<Void,Void,Void>{
        HttpHandler sh = new HttpHandler();

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i("TAG", String.valueOf(url));
            String jsonStr = sh.makeServiceCall(url);
            try {
                if(!isProfile){
                    JSONObject ob = new JSONObject(jsonStr);
                    JSONArray bd=ob.getJSONArray("backdrops");
                    for (int i=0;i<bd.length();i++){
                        img_url.add("https://image.tmdb.org/t/p/original"+bd.getJSONObject(i).getString("file_path"));
                    }
                    Log.i("TAG", String.valueOf(img_url.size()));
                }else {
                    JSONObject ob = new JSONObject(jsonStr);
                    JSONArray bd=ob.getJSONArray("profiles");
                    for (int i=0;i<bd.length();i++){
                        img_url.add("https://image.tmdb.org/t/p/original"+bd.getJSONObject(i).getString("file_path"));
                    }
                }


            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ImageSliderAdapter adapter = new ImageSliderAdapter(img_url,context,windowManager);
            viewPager.setAdapter(adapter);
        }

    }

    List<Videos> videosList = new ArrayList<>();

    public GetResFromApi(Context context, String url, ViewPager viewPager, WindowManager windowManager) {
        this.context = context;
        this.url = url;
        this.viewPager = viewPager;
        this.windowManager = windowManager;
    }

    public class getVideos extends AsyncTask<Void,Void,Void>{
        HttpHandler sh = new HttpHandler();
        @Override
        protected Void doInBackground(Void... voids) {
            String jsonStr = sh.makeServiceCall(url);
            try {
                JSONObject ob = new JSONObject(jsonStr);
                JSONArray results = ob.getJSONArray("results");

                for (int i = 0 ; i < results.length() ; i++ ){
                    String img_url = "https://img.youtube.com/vi/"+results.getJSONObject(i).getString("key")+"/hqdefault.jpg";
                    String title = results.getJSONObject(i).getString("name");
                    String vid_url = "https://www.youtube.com/watch?v="+results.getJSONObject(i).getString("key");
                    videosList.add(new Videos(results.getJSONObject(i).getString("key"),img_url,title,vid_url));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            VideoSliderAdapter adapter = new VideoSliderAdapter(videosList,context,windowManager);
            viewPager.setAdapter(adapter);
        }
    }

    public void getVideosForSliders() {
        new getVideos().execute();
    }

    ImageView bd,pr;
    TextView tt,st,rl,olg,rt,pp,nos,noe,gr,desc;

    public GetResFromApi(Context context, String url, ImageView bd, ImageView pr, TextView tt, TextView st, TextView rl, TextView olg, TextView rt, TextView pp, TextView nos, TextView noe, TextView gr, TextView desc) {
        this.context = context;
        this.url = url;
        this.bd = bd;
        this.pr = pr;
        this.tt = tt;
        this.st = st;
        this.rl = rl;
        this.olg = olg;
        this.rt = rt;
        this.pp = pp;
        this.nos = nos;
        this.noe = noe;
        this.gr = gr;
        this.desc = desc;
    }

    public class GetTVInfo extends AsyncTask<Void,Void,Void>{
        HttpHandler sh = new HttpHandler();
        String bd,pr,tt,st,rl,olg,rt,pp,nos,noe,gr,desc;

        @Override
        protected Void doInBackground(Void... voids) {
            String jsonStr = sh.makeServiceCall(url);
            try {
                JSONObject ob = new JSONObject(jsonStr);
                bd = "https://image.tmdb.org/t/p/w500"+ ob.getString("backdrop_path");
                image_url = "https://image.tmdb.org/t/p/w500"+ob.getString("poster_path");
                tt = ob.getString("name");
                st = ob.getString("status");
                rl = ob.getString("first_air_date");
                olg = ob.getString("original_language");
                rt = new NumToTime().converter(ob.getJSONArray("episode_run_time").getInt(0));
                pp = String.valueOf(ob.getDouble("popularity"));
                noe = String.valueOf(ob.getInt("number_of_episodes"));
                nos = String.valueOf(ob.getInt("number_of_seasons"));
                gr = ob.getJSONArray("genres").getJSONObject(0).getString("name");
                desc = ob.getString("overview");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("TAG",desc);

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Glide.with(context).load(bd).into(GetResFromApi.this.bd);
            Glide.with(context).load(image_url).into(GetResFromApi.this.pr);
            GetResFromApi.this.tt.setText(GetResFromApi.this.tt.getText()+tt);
            GetResFromApi.this.st.setText(GetResFromApi.this.st.getText()+st);
            GetResFromApi.this.rl.setText(GetResFromApi.this.rl.getText()+rl);
            GetResFromApi.this.olg.setText(GetResFromApi.this.olg.getText()+olg);
            GetResFromApi.this.rt.setText(GetResFromApi.this.rt.getText()+rt);
            GetResFromApi.this.pp.setText(GetResFromApi.this.pp.getText()+pp);
            GetResFromApi.this.nos.setText(GetResFromApi.this.nos.getText()+nos);
            GetResFromApi.this.noe.setText(GetResFromApi.this.noe.getText()+noe);
            GetResFromApi.this.gr.setText(GetResFromApi.this.gr.getText()+gr);
            GetResFromApi.this.desc.setText(GetResFromApi.this.desc.getText()+desc);
        }
    }


    public void getTvData(){
        new GetTVInfo().execute();
    }

    public static class getPersonalInfo extends AsyncTask<Void,Void,Void>{
        ImageView pr;
        TextView nm,beo,kf,gr,bd,bp,pop;
        String tnm,tbeo,tkf,tgr,tbd,tbp,tpop,tpr;
        Context context;
        String url;

        public getPersonalInfo(ImageView pr, TextView nm, TextView beo, TextView kf, TextView gr, TextView bd, TextView bp, TextView pop, Context context, String url) {
            this.pr = pr;
            this.nm = nm;
            this.beo = beo;
            this.kf = kf;
            this.gr = gr;
            this.bd = bd;
            this.bp = bp;
            this.pop = pop;
            this.context = context;
            this.url = url;
        }

        HttpHandler sh = new HttpHandler();
        @Override
        protected Void doInBackground(Void... voids) {
            String jsonStr = sh.makeServiceCall(url);

            try {
                JSONObject ob = new JSONObject(jsonStr);
                tnm = ob.getString("name");
                tbeo = ob.getString("biography");
                tkf = ob.getString("known_for_department");
                if (ob.getInt("gender")==1){
                    tgr = "female";
                }else tgr = "male";
                tbd = ob.getString("birthday");
                tbp = ob.getString("place_of_birth");
                tpop = String.valueOf(ob.getDouble("popularity"));
                tpr = "https://image.tmdb.org/t/p/w500" + ob.getString("profile_path");

            } catch (JSONException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            this.nm.setText(this.nm.getText()+tnm);
            this.beo.setText(this.beo.getText()+tbeo);
            this.kf.setText(this.kf.getText()+tkf);
            this.gr.setText(this.gr.getText()+tgr);
            this.bd.setText(this.bd.getText()+tbd);
            this.bp.setText(this.bp.getText()+tbp);
            this.pop.setText(this.pop.getText()+tpop);
            Glide.with(context).load(tpr).apply(RequestOptions.circleCropTransform()).into(pr);
            Log.i("TAG",tpr);

        }
    }

}
