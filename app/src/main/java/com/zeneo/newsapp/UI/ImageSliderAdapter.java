package com.zeneo.newsapp.UI;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zeneo.newsapp.R;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {

    List<String> list;
    Context context;
    ImageView image;
    WindowManager windowManager;

    public ImageSliderAdapter(List<String> list, Context context, WindowManager windowManager) {
        this.list = list;
        this.context = context;
        this.windowManager = windowManager;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slider_images_item, null);
        image = (ImageView) view.findViewById(R.id.slider_img);
        Glide.with(context).load(list.get(position)).into(image);
        setImagesSize((ViewPager)container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setImagesSize(ViewPager vp) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        image.setMaxHeight((int) (width / 0.7));

    }

}