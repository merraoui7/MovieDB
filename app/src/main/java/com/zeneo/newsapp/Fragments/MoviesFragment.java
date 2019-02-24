package com.zeneo.newsapp.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    RecyclerView recyclerView;
    List<Movies> list = new ArrayList<>();
    PopularListAdapter adapter;
    LinearLayout layout,layout1;
    GetResFromApi res;
    String url;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle bundle = getArguments();
        try {
            url = bundle.getString("url");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment]
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        final int[] page = {1};

        Log.i("TAGURL",url);

        recyclerView = (RecyclerView)view.findViewById(R.id.moviesList);
        layout = (LinearLayout)view.findViewById(R.id.movie_content);
        layout1 = (LinearLayout)view.findViewById(R.id.movie_load);
        res = new GetResFromApi(recyclerView,getContext(), url+ page[0]
                ,"movie","grid",layout,layout1,"movie");
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
