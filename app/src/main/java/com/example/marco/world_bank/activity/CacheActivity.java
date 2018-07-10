package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.DatabaseHelper1;
import com.example.marco.world_bank.JsonDB;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.adapters.ListViewCacheAdapter;
import com.example.marco.world_bank.entities.Cache;
import com.example.marco.world_bank.entities.Country;
import com.example.marco.world_bank.entities.Indicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CacheActivity extends Activity {
    private ListView list;
    private ListViewCacheAdapter adapter;
    private EditText editsearch;
    private TextView tvInfoCache;
    private Button btnClearCache;
    private List<Cache> cacheList = new ArrayList<>();
    private int choice;
    private Context context = this;
    private Activity activity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cache_activity);

        tvInfoCache = findViewById(R.id.tvInfoCache);
        list =  findViewById(R.id.lvCaches);
        tvInfoCache.setVisibility(View.INVISIBLE);

        Intent  intent = getIntent();
        choice = intent.getIntExtra("CHOICE",0);
        if (choice == 2){
            btnClearCache = findViewById(R.id.btnClearCache);
            btnClearCache.setVisibility(View.VISIBLE);
            btnClearCache.setOnClickListener(listenerClearCache);
        }




        //prendo dal db le info dell'immagine
        if (choice == 1){
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.open();
            cacheList = databaseHelper.getAllImg();
            databaseHelper.close();
        }else{
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.open();
            cacheList = databaseHelper.getAllJson();
            databaseHelper.close();
        }


        //controllo che la lista non sia vuota
        if (cacheList.isEmpty()){
            tvInfoCache.setVisibility(View.VISIBLE);
            tvInfoCache.setText("No item available!");
            //nascondi edit text
            return;
        }


        adapter = new ListViewCacheAdapter(this,cacheList,choice,activity);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml

        editsearch = findViewById(R.id.txtSearch);



        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                if (choice == 1){
                    adapter.filterSavedImg(text);
                }else{
                    adapter.filterOfflineUpload(text);
                }

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


    View.OnClickListener listenerClearCache = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.open();
            databaseHelper.deleteAllJson();
            databaseHelper.close();

            Intent intent = new Intent(context,MainActivity.class);
            Toast.makeText(context,"Cache cleared!",Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        }
    };

}

