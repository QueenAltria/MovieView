package com.jp.movieview.bean;

/**
 * Created by jp on 2017/4/5.
 * 电影
 */
public class MovieBean {

    String movieName;
    String movieUrl;
    String movieSize;
    String movieHot;
    String movieTime;
    String movieNum;
    String movieMagnet;

    public MovieBean() {
    }

    public MovieBean(String movieName, String movieUrl) {
        this.movieName = movieName;
        this.movieUrl = movieUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getMovieSize() {
        return movieSize;
    }

    public void setMovieSize(String movieSize) {
        this.movieSize = movieSize;
    }

    public String getMovieHot() {
        return movieHot;
    }

    public void setMovieHot(String movieHot) {
        this.movieHot = movieHot;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public String getMovieNum() {
        return movieNum;
    }

    public void setMovieNum(String movieNum) {
        this.movieNum = movieNum;
    }

    public String getMovieMagnet() {
        return movieMagnet;
    }

    public void setMovieMagnet(String movieMagnet) {
        this.movieMagnet = movieMagnet;
    }
}
