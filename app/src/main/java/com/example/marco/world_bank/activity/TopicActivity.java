package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;


import com.example.marco.world_bank.async.AsyncTopicParse;
import com.example.marco.world_bank.adapter.ListViewTopicAdapter;
import com.example.marco.world_bank.Parse;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.model.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TopicActivity extends Activity {

    // Declare Variables
    Parse parse = new Parse(this);
    ListView list;
    ListViewTopicAdapter adapter;
    EditText editsearch;
    String[] name;
    //Bundle bd;
    List<Topic> arraylist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_activity);

        Intent intent = getIntent();
        int choice = intent.getIntExtra("CHOICE",0);
        String isoCode2 = null;
        if (choice == 1){
            isoCode2 = intent.getStringExtra("ISOCODE");
        }

        // Generate sample data
        list =  findViewById(R.id.lvTopics);

        AsyncTask<Void,Void,List<Topic>> asyncTask = new AsyncTopicParse(this);
        List<Topic> topicList = null;
        try {
            topicList = asyncTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


//

        // Pass results to ListViewAdapter Class
        adapter = new ListViewTopicAdapter(this, topicList, choice, isoCode2);
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