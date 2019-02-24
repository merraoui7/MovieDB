package com.zeneo.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.zeneo.newsapp.Activities.MoviesActivity;
import com.zeneo.newsapp.Activities.TVShowsActivity;
import com.zeneo.newsapp.R;


import com.zeneo.newsapp.Models.Movies;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class RateListAdapter extends RecyclerView.Adapter<RateListAdapter.ViewHolder> {

    List<Movies> list;
    Context context;

    public RateListAdapter(List<Movies> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RateListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.zeneo.newsapp.R.layout.toprated_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateListAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getImgurl()).apply(centerCropTransform()
                .error(R.drawable.bg_null)).into(holder.imageView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getType().equals("movie")){
                    Intent i = new Intent(context,MoviesActivity.class);
                    i.putExtra("id",String.valueOf(list.get(position).getMovie_id()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if (list.get(position).getType().equals("TV")){
                    Intent i = new Intent(context,TVShowsActivity.class);
                    i.putExtra("id",String.valueOf(list.get(position).getMovie_id()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });
        holder.title.setText(list.get(position).getTitle());
        holder.rating.setText(list.get(position).getRating());
        holder.desc.setText(list.get(position).getOverview());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title , rating , desc;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.ratimg);
            title = (TextView)itemView.findViewById(R.id.rattitle);
            rating = (TextView)itemView.findViewById(R.id.rattxt);
            desc = (TextView)itemView.findViewById(R.id.ratdesc);
            layout = (LinearLayout)itemView.findViewById(R.id.trated_layout);
        }
    }
}
