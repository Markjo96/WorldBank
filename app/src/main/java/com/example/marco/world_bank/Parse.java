package com.example.marco.world_bank;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Parse {

    Context context;
    //MARKJO96

    public Parse(Context context) {
        this.context = context;
    }
    

    public List<Country> parseJsonCountry(){
        String json;
        try {
            InputStream is = context.getAssets().open("country");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Country>>(){}.getType();
        List<Country> list = gson.fromJson(json,listType);
        return list;
    }

    public List<Topic> parseJsonTopic(){
        String json;
        try {
            InputStream is = context.getAssets().open("topic");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Topic>>(){}.getType();
        List<Topic> list = gson.fromJson(json,listType);
        return list;
    }

    public List<Indicator> parseJsonIndicator(){
        String json;
        try {
            InputStream is = context.getAssets().open("indicator");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Indicator>>(){}.getType();
        List<Indicator> list = gson.fromJson(json,listType);
        return list;
    }

}
