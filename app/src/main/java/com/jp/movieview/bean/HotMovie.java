package com.jp.movieview.bean;

/**
 * Created by jp on 2017/4/7.
 */
public class HotMovie {

    String url;
    String name;
    String img_url;

    public HotMovie(String name, String img_url) {
        this.name = name;
        this.img_url = img_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
