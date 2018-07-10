package com.example.marco.world_bank.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.renderscript.Float4;

import com.google.gson.Gson;

public class Graph implements Parcelable {

    /**
     * indicator : {"id":"SP.POP.TOTL","value":"Population, total"}
     * country : {"id":"BR","value":"Brazil"}
     * countryiso3code : BRA
     * date : 2017
     * value : 209288278
     * unit :
     * obs_status :
     * decimal : 0
     */

    private IndicatorBean indicator;
    private CountryBean country;
    private String countryiso3code;
    private String date;
    private Float value;
    private String unit;
    private String obs_status;
    private int decimal;

    public Graph(Parcel source) {
        date = source.readString();
        value = source.readFloat();
        country = new CountryBean();
        indicator = new IndicatorBean();
        country.value  = source.readString();
        indicator.value = source.readString();
    }

    public static Graph objectFromData(String str) {

        return new Gson().fromJson(str, Graph.class);
    }

    public IndicatorBean getIndicator() {
        return indicator;
    }

    public void setIndicator(IndicatorBean indicator) {
        this.indicator = indicator;
    }

    public CountryBean getCountry() {
        return country;
    }

    public void setCountry(CountryBean country) {
        this.country = country;
    }

    public String getCountryiso3code() {
        return countryiso3code;
    }

    public void setCountryiso3code(String countryiso3code) {
        this.countryiso3code = countryiso3code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getObs_status() {
        return obs_status;
    }

    public void setObs_status(String obs_status) {
        this.obs_status = obs_status;
    }

    public int getDecimal() {
        return decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        if (value == null){
            dest.writeFloat(0);
        }else{
            dest.writeFloat(value);
        }
        dest.writeString(country.getValue());
        dest.writeString(indicator.getValue());
    }

    public static class IndicatorBean {
        /**
         * id : SP.POP.TOTL
         * value : Population, total
         */

        private String id;
        private String value;

        public static IndicatorBean objectFromData(String str) {

            return new Gson().fromJson(str, IndicatorBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class CountryBean {
        /**
         * id : BR
         * value : Brazil
         */

        private String id;
        private String value;

        public static CountryBean objectFromData(String str) {

            return new Gson().fromJson(str, CountryBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static final Parcelable.Creator<Graph> CREATOR=new Parcelable.Creator<Graph>(){

        @Override
        public Graph createFromParcel(Parcel source) {
            return new Graph(source);
        }

        @Override
        public Graph[] newArray(int size) {
            return new Graph[size];
        }
    };
}
