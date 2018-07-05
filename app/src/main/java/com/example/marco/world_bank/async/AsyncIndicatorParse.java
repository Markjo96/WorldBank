package com.example.marco.world_bank.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.marco.world_bank.model.Indicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

public class AsyncIndicatorParse extends AsyncTask<String,Void,List<Indicator>> {
    private Context context;
    public AsyncIndicatorParse(Context context) {
        this.context = context;
    }

    @Override
    protected List<Indicator> doInBackground(String... strings) {
        String json;
        json = strings[0];
        JSONArray jsonArray = null;
        try {
            jsonArray = (new JSONArray(json)).getJSONArray(1);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Indicator>>(){}.getType();
        List<Indicator> list = gson.fromJson(String.valueOf(jsonArray),listType);
        return list;
    }

    @Override
    protected void onPostExecute(List<Indicator> indicators) {
        super.onPostExecute(indicators);
    }
}
