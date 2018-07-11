package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.adapters.ListViewCacheAdapter;
import com.example.marco.world_bank.entities.Cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CacheActivity extends Activity {
    //Declare Variables
    private ListView list;
    private ListViewCacheAdapter adapter;
    private EditText editsearch;
    private TextView tvInfoCache;
    private Button btnClearCache;
    private List<Cache> cacheList = new ArrayList<>();
    private int choice;
    private Context context = this;
    private Activity activity = this;

    /**
     * Param CHOICE is used to understand in which activity we are.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cache_activity);

        tvInfoCache = findViewById(R.id.tvInfoCache);
        list =  findViewById(R.id.lvCaches);
        tvInfoCache.setVisibility(View.INVISIBLE);
        editsearch = findViewById(R.id.txtSearch);
        editsearch.setVisibility(View.VISIBLE);
        btnClearCache = findViewById(R.id.btnClearCache);
        btnClearCache.setVisibility(View.VISIBLE);
        btnClearCache.setOnClickListener(listenerClearCache);

        Intent  intent = getIntent();
        choice = intent.getIntExtra("CHOICE",0);





        /*
         * If choice=1 the cache activity is used to show the saved images, so is called the method getAllImg
           from the class DatabaseHelper and the return value is put in a cacheList.
         */
        if (choice == 1){
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.open();
            cacheList = databaseHelper.getAllImg();
            databaseHelper.close();
            /*
            If choice=2 the cache activity is used to show the already done searches, so is called the method getAllJson
            from the class DatabaseHelper and the return value is put in a cacheList.
             */
        }else{
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.open();
            cacheList = databaseHelper.getAllJson();
            databaseHelper.close();
        }



        //Check the content of caheList.
        if (cacheList.isEmpty()){
            editsearch.setVisibility(View.GONE);
            tvInfoCache.setVisibility(View.VISIBLE);
            tvInfoCache.setText("No item available!");
            //nascondi edit text
            return;
        }


        adapter = new ListViewCacheAdapter(this,cacheList,choice,activity);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml





        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                //if choice=1 the autocomplete search it is applied to list of saved image
                if (choice == 1){
                    adapter.filterSavedImg(text);
                //if choice=2 the autocomplete search it is applied to list of done searches.
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
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context);
            builder.setTitle("CLEAR CACHE").
                    setMessage("Do you want delete all record definitively?").
                    setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(context);
                            databaseHelper.open();
                            //if choice=2 all the searches made, saved in cache activity, is deleted.
                            if (choice == 2){
                                databaseHelper.deleteAllJson();
                            //if choice=1 all the images saved in cache activity is deleted.
                            }else{
                                databaseHelper.deleteAllImage();
                            }

                            databaseHelper.close();

                            Intent intent = new Intent(context,MainActivity.class);
                            Toast.makeText(context,"Cache cleared!",Toast.LENGTH_SHORT).show();
                            context.startActivity(intent);
                        }
                    }).
                    setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

        }
    };

}

