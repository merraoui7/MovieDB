package com.zeneo.newsapp.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.zeneo.newsapp.Adapters.PopularListAdapter;
import com.zeneo.newsapp.Adapters.RateListAdapter;
import com.zeneo.newsapp.Models.Movies;
import com.zeneo.newsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class GetResFromApi {



    private List<Movies> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context;
    private String url;
    private String media_type;
    private String listType;
    private LinearLayout layout,layout2;
    private boolean isRefreshed = false;


    public void setRefreshed(boolean refreshed) {
        isRefreshed = refreshed;
    }



    public GetResFromApi(RecyclerView recyclerView, Context context, String url, String media_type, String listType, LinearLayout layout, LinearLayout layout2) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.url = url;
        this.media_type = media_type;
        this.listType = listType;
        this.layout = layout;
        this.layout2 = layout2;
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
                try {

                    JSONObject mainObject = new JSONObject(jsonStr);
                    JSONArray results = mainObject.getJSONArray("results");
                    for (int i = 0 ;i < results.length() ;i++ ){
                        switch (media_type){
                            case "movie":
                                titles = results.getJSONObject(i).getString("title");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id));
                                break;
                            case "TV":
                                titles = results.getJSONObject(i).getString("name");
                                imgurl = "https://image.tmdb.org/t/p/w500" + results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles, imgurl, id));
                                break;
                            case "people":
                                titles = results.getJSONObject(i).getString("name");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("profile_path");
                                id = results.getJSONObject(i).getInt("id");
                                list.add(new Movies(titles,imgurl,id));
                                break;
                            case "topMovies":
                                titles = results.getJSONObject(i).getString("title");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                rating = String.valueOf(results.getJSONObject(i).getDouble("vote_average"));
                                desc = results.getJSONObject(i).getString("overview");
                                list.add(new Movies(titles,imgurl,id,rating+"/10",desc));
                                break;
                            case "toptv":
                                titles = results.getJSONObject(i).getString("name");
                                imgurl ="https://image.tmdb.org/t/p/w500"+ results.getJSONObject(i).getString("poster_path");
                                id = results.getJSONObject(i).getInt("id");
                                rating = String.valueOf(results.getJSONObject(i).getDouble("vote_average"));
                                desc = results.getJSONObject(i).getString("overview");
                                list.add(new Movies(titles,imgurl,id,rating+"/10",desc));
                                break;
                        }




                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isRefreshed){
                layout.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.VISIBLE);
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
                    recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayout.HORIZONTAL,false));
                    recyclerView.setAdapter(adapter1);
                }else if (listType.equals("grid")){
                    recyclerView.setLayoutManager(new GridLayoutManager(context,3));
                    recyclerView.setAdapter(adapter1);
                } else if (listType.equals("grid1")){
                    recyclerView.setLayoutManager(new GridLayoutManager(context,2));
                    recyclerView.setAdapter(adapter2);
                }
                layout.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.INVISIBLE);

            } else {

                recyclerView.setAdapter(adapter1);
                recyclerView.getLayoutManager().scrollToPosition(postitionToScrool);
                adapter1.notifyDataSetChanged();
            }



        }
    }
}
