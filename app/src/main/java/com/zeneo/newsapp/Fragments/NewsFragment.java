package com.zeneo.newsapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zeneo.newsapp.Models.Movies;
import com.zeneo.newsapp.R;
import com.zeneo.newsapp.util.GetResFromApi;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {



    
    RecyclerView recyclerView1 , recyclerView2 , recyclerView3;
    LinearLayout layout,layout1;

    public NewsFragment() {
        // Required empty public constructor
    }

    String url1 = "https://api.themoviedb.org/3/trending/movie/week?api_key=5d173b53167711178472dc9d98603e31";
    String url2 = "https://api.themoviedb.org/3/trending/tv/week?api_key=5d173b53167711178472dc9d98603e31";
    String url3 = "https://api.themoviedb.org/3/trending/person/week?api_key=5d173b53167711178472dc9d98603e31";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        layout = (LinearLayout)view.findViewById(R.id.trend_content);
        layout1 = (LinearLayout)view.findViewById(R.id.trend_load);
        recyclerView1 = (RecyclerView)view.findViewById(R.id.trendmovieslist);
        recyclerView2 = (RecyclerView)view.findViewById(R.id.trendtvlist);
        recyclerView3 = (RecyclerView)view.findViewById(R.id.trendpeoplelist);

        new GetResFromApi(recyclerView1,getContext(),url1 ,"movie","hori",layout,layout1).getDetails();
        new GetResFromApi(recyclerView2,getContext(),url2 ,"TV","hori",layout,layout1).getDetails();
        new GetResFromApi(recyclerView3,getContext(),url3 ,"people","hori",layout,layout1).getDetails();

        return view;
    }

}
