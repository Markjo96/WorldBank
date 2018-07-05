package com.example.marco.world_bank.entities;

import android.content.ContentValues;

public class JsonDao {

    private String url;
    private String json;

    public JsonDao(String url, String json) {
        this.url = url;
        this.json = json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("url", url);
        cv.put("json", json);
        return cv;
    }
}
