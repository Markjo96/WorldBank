package com.example.marco.world_bank;

import com.google.gson.Gson;

import java.util.List;

public class Indicator {

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
}
