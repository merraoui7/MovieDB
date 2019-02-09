package com.zeneo.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.zeneo.newsapp.Activities.MoviesActivity;
import com.zeneo.newsapp.Activities.PeopleActivity;
import com.zeneo.newsapp.Activities.TVShowsActivity;
import com.zeneo.newsapp.Models.Movies;
import com.zeneo.newsapp.R;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.ViewHolder> {

    List<Movies> list ;
    Context context;

    public PopularListAdapter(List<Movies> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizon_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).getTitle());
        if (list.get(position).getImgurl() != null){
            Glide.with(context).load(list.get(position).getImgurl()).apply(centerCropTransform()
                    .error(R.drawable.bg_null)
            ).into(holder.imageView);

        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getType().equals("movie")){
                    Intent i = new Intent(context,MoviesActivity.class);
                    i.putExtra("id",String.valueOf(list.get(position).getMovie_id()));
                    context.startActivity(i);
                } else if (list.get(position).getType().equals("TV")){
                    Intent i = new Intent(context,TVShowsActivity.class);
                    i.putExtra("id",String.valueOf(list.get(position).getMovie_id()));
                    context.startActivity(i);
                } else if (list.get(position).getType().equals("people")){
                    Intent i = new Intent(context,PeopleActivity.class);
                    i.putExtra("id",String.valueOf(list.get(position).getMovie_id()));
                    context.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.hortitle);
            imageView = (ImageView) itemView.findViewById(R.id.horposter);
            layout = (LinearLayout)itemView.findViewById(R.id.mov_lt);

        }
    }
}
