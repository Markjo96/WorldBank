package com.example.marco.world_bank.entities;

import android.content.ContentValues;

public class JsonDao {

    private String url;
    private String countryName;
    private String indicatorName;
    private String json;

    public JsonDao(String url, String countryName, String indicatorName, String json) {
        this.url = url;
        this.countryName = countryName;
        this.indicatorName = indicatorName;
        this.json = json;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
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
