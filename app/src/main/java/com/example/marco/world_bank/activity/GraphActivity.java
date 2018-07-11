package com.example.marco.world_bank.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.DatabaseHelper1;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.ScreenShot;
import com.example.marco.world_bank.async.AsyncQuery;
import com.example.marco.world_bank.entities.Graph;
import com.example.marco.world_bank.entities.ImageDao;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphActivity extends AppCompatActivity{

    private LineChart chart;
    private Button btnGraph;
    private Button btnStop;
    private Button btnReturn;
    private List<Graph> arraylist = new ArrayList<>();
    private ProgressBar pb;
    private Context context = this;
    private AsyncTask<String,Void,List<Graph>> asyncTask;

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.graph_activity);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("URI");
        String isoCode = intent.getStringExtra("ISO");
        String indicatorName = intent.getStringExtra("INDICATOR_NAME");
        String indicatorId = intent.getStringExtra("INDICATOR_ID");

        chart = findViewById(R.id.chart);
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(listenerBtnReturn);
        btnStop = findViewById(R.id.btnStop);
        btnStop.setVisibility(View.VISIBLE);
        btnStop.setOnClickListener(listenerBtnStop);
        btnGraph = findViewById(R.id.btnGraph);
        btnGraph.setVisibility(View.GONE);
        pb = findViewById(R.id.pb);

        //set up and execute asyncTask
        asyncTask = new AsyncQuery(context,pb,chart,btnGraph,btnStop,btnReturn,isoCode,indicatorId,
                indicatorName);
        asyncTask.execute(uri);

    }

   /*
   The Async Task thread is killed if the BtnStop is pressed
    */
    View.OnClickListener listenerBtnStop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (! asyncTask.isCancelled()){
                asyncTask.cancel(true);
                Toast.makeText(context,"Graph request stopped!",Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);
                btnReturn.setVisibility(View.VISIBLE);
                finish();
            }else{
                Toast.makeText(context,"Graph request already stopped!",Toast.LENGTH_SHORT).show();
            }
        }
    };
    //Return to main activity
    View.OnClickListener listenerBtnReturn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,MainActivity.class);
            context.startActivity(intent);
        }
    };



}
