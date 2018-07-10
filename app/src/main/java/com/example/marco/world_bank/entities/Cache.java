package com.example.marco.world_bank.entities;

public class Cache {
    private Country country;
    private Indicator indicator;
    private String url;

    public Cache(Country country, Indicator indicator, String url) {
        this.country = country;
        this.indicator = indicator;
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }
}
