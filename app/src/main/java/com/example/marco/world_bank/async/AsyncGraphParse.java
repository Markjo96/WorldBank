package com.example.marco.world_bank.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.marco.world_bank.entities.Graph;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

public class AsyncGraphParse extends AsyncTask<String,Void,List<Graph>> {

    private List<Graph> list;
    private Context context;

    public AsyncGraphParse(Context context) {
        this.context = context;
    }

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
        try{
            list = gson.fromJson(String.valueOf(jsonArray),listType);
            return list;
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            Toast.makeText(context,"JSON ERRATO",Toast.LENGTH_LONG).show();
        }
        return list;

    }
}
