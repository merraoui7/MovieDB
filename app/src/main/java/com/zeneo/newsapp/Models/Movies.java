package com.zeneo.newsapp.Models;

public class Movies {

    private String title;
    private String imgurl;
    private int movie_id;

    public Movies(String title, String imgurl, int movie_id) {
        this.title = title;
        this.imgurl = imgurl;
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
