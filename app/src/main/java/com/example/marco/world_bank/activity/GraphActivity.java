package com.example.marco.world_bank.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.marco.world_bank.R;
import com.example.marco.world_bank.model.Graph;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphActivity extends AppCompatActivity{

    LineChart chart;
    List<Graph> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.graph_activity);

        chart = findViewById(R.id.chart);
        /*LineChart chart = new LineChart(this);
        RelativeLayout rl = findViewById(R.id.chart);
        rl.addView(chart);*/

        Bundle bundle = getIntent().getExtras();
        arraylist = bundle.getParcelableArrayList("GRAPH_DATA");



        /*mChart.setOnChartGestureListener(GraphActivity.this);
        mChart.setOnChartValueSelectedListener(GraphActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);*/

        ArrayList<Entry>  entries = new ArrayList<>();

        for (Graph data : arraylist){
            entries.add(new Entry(Float.parseFloat(data.getDate()),(float) data.getValue()));
        }

        Collections.sort(entries, new EntryXComparator());

        LineDataSet dataSet = new LineDataSet(entries,"Label");
        dataSet.setColor(Color.parseColor("#36a8ff"));

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        chart.invalidate();



    }
}
