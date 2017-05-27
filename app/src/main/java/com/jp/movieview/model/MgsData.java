package com.jp.movieview.model;

/**
 * Created by jp on 2017/5/24
 */
public class MgsData {
    public final String TAG = getClass().getName();

    private String img_url;
    private String href_url;
    private String name;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getHref_url() {
        return href_url;
    }

    public void setHref_url(String href_url) {
        this.href_url = href_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
