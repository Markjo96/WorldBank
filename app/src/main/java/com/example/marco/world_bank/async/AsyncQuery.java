package com.example.marco.world_bank.async;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.ScreenShot;
import com.example.marco.world_bank.entities.Graph;
import com.example.marco.world_bank.entities.ImageDao;
import com.example.marco.world_bank.entities.JsonDao;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;



public class AsyncQuery extends AsyncTask<String,Void,List<Graph>> {
    public static  String REQUEST_METOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private InputStreamReader in;
    private BufferedReader bin;
    private List<Graph> graphs = new ArrayList<>();
    private Context context;
    private ProgressBar pb;
    private LineChart chart;
    private Button btnGraph;
    private Button btnStop;
    private Button btnReturn;
    private String iso;
    private String indicatorName;
    private String indicatorId;
    //LineChart chart1;

    public AsyncQuery(Context context, ProgressBar pb, LineChart chart, Button btnGraph,
                      Button btnStop,Button btnReturn,String iso,String indicatorId,String indicatorName) {
        this.context = context;
        this.pb = pb;
        this.chart = chart;
        this.btnGraph = btnGraph;
        this.btnStop = btnStop;
        this.iso = iso;
        this.indicatorId = indicatorId;
        this.indicatorName = indicatorName;
        this.btnReturn = btnReturn;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected List<Graph> doInBackground(String... strings) {

        pb.setVisibility(View.VISIBLE);

        StringBuilder stringBuilder = new StringBuilder();

        String uri = strings[0];

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.open();
        JsonDao jsonDao = new JsonDao(uri,null,null,null);
        Cursor cursor = databaseHelper.getJson(jsonDao);
        String json = null;
        if (cursor.moveToNext()){
            json =cursor.getString(cursor.getColumnIndex("json"));
        }
        databaseHelper.close();

        if (json==null){
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
                if(!urlConnection.getResponseMessage().equals(200)){
                    System.out.println("CONNECTION LOST BOH!");
                }
                in = new InputStreamReader(urlConnection.getInputStream());
                bin = new BufferedReader(in);
                String inputLine;
                //int tot =0;
                while((inputLine=bin.readLine()) != null){
                    //tot++;
                    //pb.setProgress(tot);
                    stringBuilder.append(inputLine);
                }
            } catch (SocketTimeoutException e){
                Toast.makeText(context,"INTERNET NOT AVAILABLE",Toast.LENGTH_SHORT).show();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
                try {
                    if (in != null) {
                        in.close();
                        bin.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(stringBuilder.toString());
            json =  stringBuilder.toString();

            DatabaseHelper databaseHelper1 = new DatabaseHelper(context);
            databaseHelper1.open();
            String countryName = null;
            if(indicatorId == null){
                countryName = databaseHelper1.getCountryNameByIso(iso);
            }else{
                indicatorName = databaseHelper1.getNameIndicatorByIndicatorId(indicatorId);
                countryName = databaseHelper1.getCountryNameByIso(iso);
            }

            JsonDao jsonDao1 = new JsonDao(uri,countryName,indicatorName,json);
            databaseHelper1.addJson(jsonDao1);
            databaseHelper1.close();

        }


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
            graphs = gson.fromJson(String.valueOf(jsonArray),listType);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
        }

        return graphs;
    }




    @Override
    protected void onPostExecute(List<Graph> result) {
        super.onPostExecute(result);
        pb.setVisibility(View.GONE);
        btnStop.setVisibility(View.GONE);

        ArrayList<Entry>  entries = new ArrayList<>();

        if (result== null || result.isEmpty()){
            Toast.makeText(context,"GRAPH NOT AVAILABLE",Toast.LENGTH_SHORT).show();
            return;
        }

        for (Graph data : graphs){
            if (data.getValue()!=null && data.getDate()!=null){
                entries.add(new Entry(Float.parseFloat(data.getDate()),data.getValue()));
            }
        }

        if (entries== null || entries.isEmpty()){
            Toast.makeText(context,"GRAPH NOT AVAILABLE",Toast.LENGTH_SHORT).show();
            return;
        }

        Collections.sort(entries, new EntryXComparator());

        LineDataSet dataSet = new LineDataSet(entries,"Label");
        dataSet.setColor(Color.parseColor("#36a8ff"));

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        chart.invalidate();

        btnReturn.setVisibility(View.VISIBLE);
        btnGraph.setVisibility(View.VISIBLE);
        btnGraph.setOnClickListener(listener);




    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String nameCountry = graphs.get(0).getCountry().getValue();
            String nameIndicator = graphs.get(0).getIndicator().getValue();
            String image_name = nameCountry+"^"+nameIndicator;

            Bitmap bitmap = chart.getChartBitmap();
            byte[] listByte = ScreenShot.getBytes(bitmap);
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.open();
            if (!databaseHelper.checkExistImage(image_name)){
                ImageDao imageDao = new ImageDao(image_name,listByte);
                databaseHelper.addImage(imageDao);
                Toast.makeText(context,"ScreenShot Saved!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"ScreenShot alredy Saved!",Toast.LENGTH_SHORT).show();
            }
            databaseHelper.close();


        }
    };


}
