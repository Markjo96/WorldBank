package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.adapters.ListViewIndicatorAdapter;
import com.example.marco.world_bank.Parse;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.entities.Indicator;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IndicatorActivity extends Activity {

    // Declare Variables
    Parse parse = new Parse(this);
    ListView list;
    ListViewIndicatorAdapter adapter;
    ProgressBar pbIndicator;
    EditText editsearch;
    String[] name;
    //Bundle bd;
    List<Indicator> indicatorList = new ArrayList<>();
    private Context context = this;
    private Activity activity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicator_activity);


        Bundle bundle = getIntent().getExtras();
        int choice = getIntent().getIntExtra("CHOICE",0);
        String isoCode2 = getIntent().getStringExtra("ISOCODE");
        String topicId = getIntent().getStringExtra("TOPIC_ID");

        //Select all Indicators from topic id previously passed from topic activity
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.open();
        indicatorList = databaseHelper.getIndicatorByTopicId(topicId);
        databaseHelper.close();



        // Generate sample data
        list =  findViewById(R.id.lvIndicators);

        // Pass results to ListViewAdapter Class
        adapter = new ListViewIndicatorAdapter(this,indicatorList, choice, isoCode2,activity);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        editsearch =  findViewById(R.id.txtSearch);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

}
