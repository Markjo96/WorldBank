package com.example.marco.world_bank.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.marco.world_bank.model.Country;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class AsyncCountryParse extends AsyncTask<Void,Void,List<Country>> {
    private Context context;
    InputStream is;

    public AsyncCountryParse(Context context) {
        this.context = context;
    }

    @Override
    protected List<Country> doInBackground(Void... voids) {
        String json;
        try {
            is = context.getAssets().open("country");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            json = new String(buffer, "UTF-8");
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Country>>(){}.getType();
        List<Country> list = gson.fromJson(json,listType);
        return list;
    }
}
