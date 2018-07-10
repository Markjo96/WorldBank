package com.example.marco.world_bank.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.async.AsyncCountryParse;
import com.example.marco.world_bank.async.AsyncIndicatorParse;
import com.example.marco.world_bank.async.AsyncTopicParse;
import com.example.marco.world_bank.entities.Country;
import com.example.marco.world_bank.entities.Indicator;
import com.example.marco.world_bank.entities.Topic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    Button btnCountry, btnTopic, btnPrevImg, btnData;
    ImageView ivWorldBank;
    Context context = this;
    InputStream is;

    public static final String EXTRA_MESSAGE =
            "com.example.marco.worldbank.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCountry = findViewById(R.id.btnCountry);
        btnTopic = findViewById(R.id.btnTopic);
        btnPrevImg = findViewById(R.id.btnPrevImg);
        btnData = findViewById(R.id.btnData);

        btnCountry.setOnClickListener(listener);
        btnTopic.setOnClickListener(listener);
        btnPrevImg.setOnClickListener(listener);
        btnData.setOnClickListener(listener);


    }
    /*public void gotoCountrySearchView(View view) {
        Intent i = new Intent(this,
                CountryActivity.class);
        startActivity(i);}*/

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnCountry:
                    Intent intentCountry = new Intent(MainActivity.this,
                            CountryActivity.class);
                    intentCountry.putExtra("CHOICE", 1);
                    startActivity(intentCountry);

                    //Insert data

                    //fill country
                    /*Log.i("IOOOOOOOOOO","inizio");
                    List<Indicator> countries = new ArrayList<>();


                    String json = null;
                    try {
                        InputStream is = context.getAssets().open("indicator");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        json = new String(buffer, "UTF-8");
                    }catch (IOException e) {
                        e.printStackTrace();
                    }



                    AsyncTask<String,Void,List<Indicator>> asyncTask = new AsyncIndicatorParse(context);
                    try {
                        countries = asyncTask.execute(json).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    databaseHelper.open();
                    for (Indicator country:countries) {
                        databaseHelper.addIndicator(country);
                    }
                    databaseHelper.close();
                    Log.i("IOOOOOOOOOO","fine");*/

                    /*DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    databaseHelper.open();
                    databaseHelper.deleteAllIndicator();
                    databaseHelper.close();*/


                    break;
                case R.id.btnTopic:
                    //start activity
                    Intent intentTopic = new Intent(context, TopicActivity.class);
                    intentTopic.putExtra("CHOICE", 2);
                    startActivity(intentTopic);
                    break;
                case R.id.btnPrevImg:
                    Intent intentPrevImg = new Intent(context, CacheActivity.class);
                    intentPrevImg.putExtra("CHOICE",1);
                    startActivity(intentPrevImg);
                    break;
                case R.id.btnData:
                    Intent intentCache = new Intent(context, CacheActivity.class);
                    intentCache.putExtra("CHOICE",2);
                    startActivity(intentCache);
                    break;

            }
        }
    };
}


