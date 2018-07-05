package com.example.marco.world_bank.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.marco.world_bank.entities.Topic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class AsyncTopicParse extends AsyncTask<Void,Void,List<Topic>> {

    private Context context;

    public AsyncTopicParse(Context context) {
        this.context = context;
    }
    InputStream is;
    @Override
    protected List<Topic> doInBackground(Void... voids) {

        String json;
        try {
            is = context.getAssets().open("topic");
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
        Type listType = new TypeToken<List<Topic>>(){}.getType();
        List<Topic> list = gson.fromJson(json,listType);
        return list;
    }
}
