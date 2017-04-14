package com.jp.movieview.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jp on 2017/4/10.
 */
public class YandeBean implements Parcelable {

    private String preview_url;
    private String src_url;
    private String name;
    private String size;
    private Bitmap bitmap;
    private String realUrl;
    private ArrayList<String> tagList;

    private int width;
    private int height;

    public YandeBean() {
    }

    public ArrayList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        this.tagList = tagList;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public String getSrc_url() {
        return src_url;
    }

    public void setSrc_url(String src_url) {
        this.src_url = src_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.preview_url);
        parcel.writeString(this.src_url);
        parcel.writeString(this.name);
        parcel.writeString(this.size);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
    }

    protected YandeBean(Parcel in) {
        this.preview_url = in.readString();
        this.src_url = in.readString();
        this.name = in.readString();
        this.size = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<YandeBean> CREATOR = new Parcelable.Creator<YandeBean>() {
        @Override
        public YandeBean createFromParcel(Parcel source) {
            return new YandeBean(source);
        }

        @Override
        public YandeBean[] newArray(int size) {
            return new YandeBean[size];
        }
    };
}
