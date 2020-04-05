package com.example.androidtest;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int id;
    private int userid;
    private String title;

    @SerializedName("body")
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post(int id, int userid, String title, String text) {
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.text = text;
    }
}
