package com.example.marco.world_bank.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

public class Indicator implements Parcelable{

    /**
     * id : 1.0.PSev.1.90usd
     * name : Poverty Severity ($1.90 a day)
     * unit :
     * source : {"id":"37","value":"LAC Equity Lab"}
     * sourceNote : The poverty severity index combines information on both poverty and inequality among the poor by averaging the squares of the poverty gaps relative the poverty line
     * sourceOrganization : LAC Equity Lab tabulations of SEDLAC (CEDLAS and the World Bank).
     * topics : [{"id":"11","value":"Poverty "}]
     */

    private String id;
    private String name;
    private String unit;
    private SourceBean source;
    private String sourceNote;
    private String sourceOrganization;
    private List<TopicsBean> topics;

    public Indicator(Parcel source){
        id = source.readString();
        name = source.readString();
        sourceNote = source.readString();
    }
    public Indicator(String id, String name, String sourceNote) {
        this.id = id;
        this.name = name;
        this.sourceNote = sourceNote;
    }

    public Indicator(String id) {
        this.id = id;
    }

    public static Indicator objectFromData(String str) {

        return new Gson().fromJson(str, Indicator.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public SourceBean getSource() {
        return source;
    }

    public void setSource(SourceBean source) {
        this.source = source;
    }

    public String getSourceNote() {
        return sourceNote;
    }

    public void setSourceNote(String sourceNote) {
        this.sourceNote = sourceNote;
    }

    public String getSourceOrganization() {
        return sourceOrganization;
    }

    public void setSourceOrganization(String sourceOrganization) {
        this.sourceOrganization = sourceOrganization;
    }

    public List<TopicsBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBean> topics) {
        this.topics = topics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(sourceNote);
    }

    public static class SourceBean {
        /**
         * id : 37
         * value : LAC Equity Lab
         */

        private String id;
        private String value;

        public static SourceBean objectFromData(String str) {

            return new Gson().fromJson(str, SourceBean.class);
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

    public static class TopicsBean {
        /**
         * id : 11
         * value : Poverty
         */

        private String id;
        private String value;

        public static TopicsBean objectFromData(String str) {

            return new Gson().fromJson(str, TopicsBean.class);
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

    public static final Parcelable.Creator<Indicator> CREATOR=new Parcelable.Creator<Indicator>(){

        @Override
        public Indicator createFromParcel(Parcel source) {
            return new Indicator(source);
        }

        @Override
        public Indicator[] newArray(int size) {
            return new Indicator[size];
        }
    };
}
