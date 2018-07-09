package com.example.marco.world_bank.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.marco.world_bank.R;


public class MainActivity extends AppCompatActivity {

    Button btnCountry, btnTopic, btnPrevImg, btnData;
    ImageView ivWorldBank;
    Context context = this;

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
        //ivWorldBank = findViewById(R.id.ivWorldBank);

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
                    intentCountry.putExtra("CHOICE",1);
                    startActivity(intentCountry);
                    break;
                case R.id.btnTopic:

                    //start activity
                    Intent intentTopic = new Intent(context,TopicActivity.class);
                    intentTopic.putExtra("CHOICE",2);
                    startActivity(intentTopic);
                    break;
                case R.id.btnPrevImg:

                    Intent intentSavedImage = new Intent(context,SavedImageActivity.class);
                    break;
                case R.id.btnData:
                    Intent intentCache = new Intent(context,CacheActivity.class);
                    break;

            }
        }
    };


    /*private class GetData extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {

            StringBuilder stringBuilder = new StringBuilder();
            String uri = strings[0];
            URL url = null;
            try {
                url = new URL(uri);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                String inputLine;
                while((inputLine=bin.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }

            return stringBuilder.toString();
        }*/
    }
