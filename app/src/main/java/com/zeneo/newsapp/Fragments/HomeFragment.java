package com.zeneo.newsapp.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.zeneo.newsapp.Models.Movies;
import com.zeneo.newsapp.R;
import com.zeneo.newsapp.util.GetResFromApi;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    List<Movies> firstlist = new ArrayList<>();
    List<Movies> seclist = new ArrayList<>();
    RecyclerView firstrecyclerView , secRecyclerView;
    LinearLayout layout,layout1;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        firstrecyclerView = (RecyclerView)view.findViewById(R.id.poprec);
        secRecyclerView = (RecyclerView)view.findViewById(R.id.poptvrec);
        layout = (LinearLayout)view.findViewById(R.id.home_content);
        layout1 = (LinearLayout)view.findViewById(R.id.home_load);

        firstrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx > 0) {
                    // Recycle view scrolling down...
                    if(recyclerView.canScrollHorizontally(RecyclerView.FOCUS_LEFT) == false){
                        Toast.makeText(getContext(), "Reached the end of recycler view", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        new GetResFromApi(firstrecyclerView,getContext(),
                "https://api.themoviedb.org/3/discover/movie?api_key=5d173b53167711178472dc9d98603e31&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1"
        ,"movie","hori",layout,layout1).getDetails();
        new GetResFromApi(secRecyclerView,getContext(),
                "https://api.themoviedb.org/3/discover/tv?api_key=5d173b53167711178472dc9d98603e31&language=en-US&sort_by=popularity.desc&first_air_date_year=2019&page=1&timezone=America%2FNew_York&include_null_first_air_dates=false"
                ,"TV","hori",layout,layout1).getDetails();



        return view;
    }



}
