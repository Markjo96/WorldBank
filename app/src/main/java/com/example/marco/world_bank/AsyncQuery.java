package com.example.marco.world_bank;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncQuery extends AsyncTask<String,Void,String> {
    public static  String REQUEST_METOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

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
            urlConnection.setRequestMethod(REQUEST_METOD);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            if(urlConnection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                System.out.println("CONNECTION LOST!");
            }
            if(urlConnection.getResponseMessage().equals(200)){
                System.out.println("CONNECTION LOST!");
            }
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bin = new BufferedReader(in);
            String inputLine;
            while((inputLine=bin.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            in.close();
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();

        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
