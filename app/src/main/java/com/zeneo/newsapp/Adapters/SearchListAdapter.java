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
import com.zeneo.newsapp.Activities.MoviesActivity;
import com.zeneo.newsapp.Activities.PeopleActivity;
import com.zeneo.newsapp.Activities.TVShowsActivity;
import com.zeneo.newsapp.Models.Search;
import com.zeneo.newsapp.R;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private List<Search> list;
    private Context context;

    public SearchListAdapter(List<Search> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.mediaType.setText(list.get(position).getMedia_type());
        holder.rating.setText(list.get(position).getRating());
        holder.desc.setText(list.get(position).getDesc());
        Glide.with(context).load(list.get(position).getImgurl())
                .apply(centerCropTransform().error(R.drawable.bg_null))
                .into(holder.imageView);
        final Intent[] intent = new Intent[1];
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getMedia_type().equals("TV")){
                    intent[0] = new Intent(context,TVShowsActivity.class);
                } else if(list.get(position).getMedia_type().equals("movie")){
                    intent[0] = new Intent(context,MoviesActivity.class);

                } else if(list.get(position).getMedia_type().equals("people")){
                    intent[0] = new Intent(context,PeopleActivity.class);
                }
                intent[0].putExtra("id",String.valueOf(list.get(position).getId()));
                intent[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent[0]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,mediaType,rating,desc;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.search_img);
            title = (TextView)itemView.findViewById(R.id.search_title);
            mediaType = (TextView)itemView.findViewById(R.id.search_type);
            rating = (TextView)itemView.findViewById(R.id.search_rate);
            desc = (TextView)itemView.findViewById(R.id.search_desc);
            layout = (LinearLayout)itemView.findViewById(R.id.search_layout);

        }
    }
}
