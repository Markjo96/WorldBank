package com.example.marco.world_bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IndicatorActivity extends Activity {

    // Declare Variables
    Parse parse = new Parse(this);
    ListView list;
    ListViewIndicatorAdapter adapter;
    EditText editsearch;
    String[] name;
    //Bundle bd;
    List<Indicator> arraylist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicator_activity);

        // Generate sample data
        list =  findViewById(R.id.lvIndicators);

        arraylist =  parse.parseJsonIndicator();

//

        // Pass results to ListViewAdapter Class
        adapter = new ListViewIndicatorAdapter(this, arraylist);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.txtSearch);

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
