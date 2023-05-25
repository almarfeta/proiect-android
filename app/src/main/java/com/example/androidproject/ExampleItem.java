package com.example.androidproject;

public class ExampleItem {
    private String mImageUrl;
    private String mName;
    private int mLikes;

    public ExampleItem(String mImageUrl, String mName, int mLikes) {
        this.mImageUrl = mImageUrl;
        this.mName = mName;
        this.mLikes = mLikes;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public int getmLikes() {
        return mLikes;
    }
}
