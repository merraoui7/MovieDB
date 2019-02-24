package com.zeneo.newsapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zeneo.newsapp.Activities.HomeActivity;
import com.zeneo.newsapp.Activities.MoviesActivity;
import com.zeneo.newsapp.Activities.PeopleActivity;
import com.zeneo.newsapp.Activities.TVShowsActivity;
import com.zeneo.newsapp.DATABASE.DataContract;
import com.zeneo.newsapp.DATABASE.DataHelper;
import com.zeneo.newsapp.Models.Movies;
import com.zeneo.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.ViewHolder> {

    List<Movies> list ;
    Context context ;
    SQLiteDatabase db,db2;
    DataHelper.FavoriteHelper sqlfavorite;
    DataHelper.WatchListHelper sqlwatchlist;


    List<Boolean> selectedItems = new ArrayList<>();


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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).getTitle());
        if (list.get(position).getImgurl() != null){
            Glide.with(context).load(list.get(position).getImgurl()).apply(centerCropTransform()
                    .error(R.drawable.bg_null)
            ).into(holder.imageView);

        }



        final Activity activity = (HomeActivity)context;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getType().equals("movie")||list.get(position).getType().equals("castm")||list.get(position).getType().equals("crewm")){
                    Intent i = new Intent(context,MoviesActivity.class);
                    i.putExtra("id",String.valueOf(list.get(position).getMovie_id()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if (list.get(position).getType().equals("TV")||list.get(position).getType().equals("castt")||list.get(position).getType().equals("crewt")){
                    Intent i = new Intent(context,TVShowsActivity.class);
                    i.putExtra("id",String.valueOf(list.get(position).getMovie_id()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if (list.get(position).getType().equals("people")){
                    Intent i = new Intent(context,PeopleActivity.class);
                    i.putExtra("id",String.valueOf(list.get(position).getMovie_id()));
                    context.startActivity(i);
                } else if (list.get(position).getType().equals("seasons")){
                    ((TVShowsActivity)context).getEpisodes(list.get(position).getSeason_number());
                }
            }
        });

        for (int i = 0 ; i<list.size() ; i++){
            selectedItems.add(false);
        }

        if(selectedItems.get(position)){
            holder.layout2.setVisibility(View.VISIBLE);
        }else{
            holder.layout2.setVisibility(View.GONE);
        }

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(selectedItems.get(position)){
                    holder.layout.setSelected(false);
                    selectedItems.set(position,false);
                    holder.layout2.setVisibility(View.GONE);
                    Log.i("TAG",String.valueOf(selectedItems.get(position)));

                }else{
                    for (int i = 0; i <list.size();i++){
                        if(selectedItems.get(i)){
                            selectedItems.set(i,false);
                            notifyItemChanged(i);
                        }
                    }
                    holder.layout.setSelected(true);
                    selectedItems.set(position,true);
                    holder.layout2.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });
        sqlfavorite = new DataHelper.FavoriteHelper(context);
        db = sqlfavorite.getWritableDatabase();
        sqlwatchlist = new DataHelper.WatchListHelper(context);
        db2 = sqlwatchlist.getWritableDatabase();



        boolean isExist = sqlfavorite.CheckIsDataAlreadyInDBorNot(DataContract.FavoriteEntry.TABLE_NAME, String.valueOf(list.get(position).getMovie_id()));
        if (isExist){
            holder.favorite.setImageResource(R.drawable.favorite_dark_enable);
        }else {
            holder.favorite.setImageResource(R.drawable.favorite_dark);
        }
        boolean count3 = sqlwatchlist.CheckIsDataAlreadyInDBorNot(DataContract.WatchListEntry.TABLE_NAME, String.valueOf(list.get(position).getMovie_id()));
        if (count3){
            holder.watchlist.setImageResource(R.drawable.dark_watchlist_added);
        }else {
            holder.watchlist.setImageResource(R.drawable.dark_watchlist_add);
        }

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isExist = sqlfavorite.CheckIsDataAlreadyInDBorNot(DataContract.FavoriteEntry.TABLE_NAME, String.valueOf(list.get(position).getMovie_id()));
                String title = list.get(position).getTitle();
                String imageurl = list.get(position).getImgurl();
                if (isExist){
                    sqlfavorite.delete(DataContract.FavoriteEntry.TABLE_NAME, String.valueOf(list.get(position).getMovie_id()),"TV");
                }else {
                    sqlfavorite.insert(DataContract.FavoriteEntry.TABLE_NAME,title,imageurl, String.valueOf(list.get(position).getMovie_id()),"TV");
                }
                notifyItemChanged(position);
            }
        });
        holder.watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean count3 = sqlwatchlist.CheckIsDataAlreadyInDBorNot(DataContract.WatchListEntry.TABLE_NAME,String.valueOf(list.get(position).getMovie_id()));

                String title = list.get(position).getTitle();
                String imageurl = list.get(position).getImgurl();
                if (count3){
                    sqlwatchlist.delete(DataContract.WatchListEntry.TABLE_NAME,String.valueOf(list.get(position).getMovie_id()),"TV");
                }else {
                    sqlwatchlist.insert(DataContract.WatchListEntry.TABLE_NAME,title,imageurl,String.valueOf(list.get(position).getMovie_id()),"TV");
                }
                notifyItemChanged(position);

            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView,watchlist,favorite;
        LinearLayout layout;
        LinearLayout layout2;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.hortitle);
            imageView = (ImageView) itemView.findViewById(R.id.horposter);
            layout = (LinearLayout)itemView.findViewById(R.id.mov_lt);
            layout2 =(LinearLayout)itemView.findViewById(R.id.horaddtodata);
            watchlist = (ImageView)itemView.findViewById(R.id.watch_list);
            favorite = (ImageView)itemView.findViewById(R.id.favorite);

        }
    }
}
