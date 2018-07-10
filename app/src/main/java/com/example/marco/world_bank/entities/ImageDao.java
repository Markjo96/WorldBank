package com.example.marco.world_bank.entities;

import java.sql.Blob;

public class ImageDao {
    private String image_name;
    private byte[] image_data;

    public ImageDao(String image_name, byte[] image_data) {
        this.image_name = image_name;
        this.image_data = image_data;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public byte[] getImage_data() {
        return image_data;
    }

    public void setImage_data(byte[] image_data) {
        this.image_data = image_data;
    }
}


