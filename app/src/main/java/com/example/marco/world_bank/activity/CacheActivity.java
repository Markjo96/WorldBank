package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.marco.world_bank.R;
import com.example.marco.world_bank.adapters.ListViewCacheAdapter;
import com.example.marco.world_bank.entities.Cache;

import java.util.List;
import java.util.Locale;

public class CacheActivity extends Activity {
    ListView list;
    ListViewCacheAdapter adapter;
    EditText editsearch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cache_activity);
        List<Cache> cacheList = null;

        adapter = new ListViewCacheAdapter(this,cacheList);
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

