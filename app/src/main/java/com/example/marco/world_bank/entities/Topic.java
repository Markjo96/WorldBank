package com.example.marco.world_bank.entities;

public class Topic{

    private String id;
    private String value;
    private String sourceNote;

    public Topic(String id, String value, String sourceNote) {
        this.id = id;
        this.value = value;
        this.sourceNote = sourceNote;
    }

    public Topic(String id) {
        this.id = id;
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

    public String getSourceNote() {
        return sourceNote;
    }

    public void setSourceNote(String sourceNote) {
        this.sourceNote = sourceNote;
    }



}
