package com.zeneo.newsapp.Models;

public class Movies {

    private String title;
    private String imgurl;
    private int movie_id;
    private String rating;
    private String overview;
    private String type;



    public Movies(String title, String imgurl, int movie_id, String rating, String overview) {
        this.title = title;
        this.imgurl = imgurl;
        this.movie_id = movie_id;
        this.rating = rating;
        this.overview = overview;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRating() {

        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Movies(String title, String imgurl, int movie_id, String type) {
        this.title = title;
        this.imgurl = imgurl;
        this.movie_id = movie_id;
        this.type = type;
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
