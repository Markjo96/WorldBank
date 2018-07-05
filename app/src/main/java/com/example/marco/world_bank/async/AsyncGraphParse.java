package com.example.marco.world_bank.async;

import android.os.AsyncTask;

import com.example.marco.world_bank.model.Graph;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

public class AsyncGraphParse extends AsyncTask<String,Void,List<Graph>> {


    @Override
    protected List<Graph> doInBackground(String... strings) {
        String json = strings[0];
        JSONArray jsonArray;
        try {
            jsonArray = (new JSONArray(json)).getJSONArray(1);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Graph>>(){}.getType();
        List<Graph> list = gson.fromJson(String.valueOf(jsonArray),listType);
        return list;
    }
}
