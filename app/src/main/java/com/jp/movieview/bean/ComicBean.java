package com.jp.movieview.bean;

/**
 * Created by jp on 2017/4/19
 */
public class ComicBean {
    public static final String TAG = "ComicBean";

    String title;
    String href_url;
    String em;
    String src_url;
    String update_time;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref_url() {
        return href_url;
    }

    public void setHref_url(String href_url) {
        this.href_url = href_url;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public String getSrc_url() {
        return src_url;
    }

    public void setSrc_url(String src_url) {
        this.src_url = src_url;
    }
}
