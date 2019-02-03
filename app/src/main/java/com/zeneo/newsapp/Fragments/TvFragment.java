package com.zeneo.newsapp.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zeneo.newsapp.Adapters.PopularListAdapter;
import com.zeneo.newsapp.Models.Movies;
import com.zeneo.newsapp.R;
import com.zeneo.newsapp.util.GetResFromApi;
import com.zeneo.newsapp.util.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TvFragment extends Fragment {


    RecyclerView recyclerView;
    List<Movies> list = new ArrayList<>();
    PopularListAdapter adapter;
    LinearLayout layout,layout1;

    GetResFromApi res;
    String url= "https://api.themoviedb.org/3/discover/tv?api_key=5d173b53167711178472dc9d98603e31&language=en-US&sort_by=popularity.desc&timezone=America%2FNew_York&include_null_first_air_dates=false&page=";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        final int[] page = {1};
        recyclerView =(RecyclerView)view.findViewById(R.id.tvList);
        layout = (LinearLayout)view.findViewById(R.id.tv_content);
        layout1 = (LinearLayout)view.findViewById(R.id.tv_load);
        res = new GetResFromApi(recyclerView,getContext(), url+ page[0],"TV","grid",layout,layout1);
        res.clearData();
        res.getDetails();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && !recyclerView.canScrollVertically(View.FOCUS_DOWN)){
                    page[0]++;
                    res.setUrl(url+ page[0]);
                    res.setRefreshed(true);
                    res.getDetails();
                }
            }
        });
        return view;
    }

}
