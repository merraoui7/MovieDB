package com.zeneo.newsapp.Models;

public class Movies {

    private String title;
    private String imgurl;
    private int movie_id;
    private String rating;
    private String overview;
    private String type;
    private int season_number;



    public Movies(String title, String imgurl, int movie_id, String rating, String overview, String type) {
        this.title = title;
        this.imgurl = imgurl;
        this.movie_id = movie_id;
        this.rating = rating;
        this.overview = overview;
        this.type = type;
    }

    public Movies(String title, String imgurl, int movie_id,String type, int season_number) {
        this.title = title;
        this.imgurl = imgurl;
        this.movie_id = movie_id;
        this.type = type;
        this.season_number = season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public int getSeason_number() {
        return season_number;
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
