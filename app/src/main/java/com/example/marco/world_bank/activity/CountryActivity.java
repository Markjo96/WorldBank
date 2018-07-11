package com.example.marco.world_bank.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;


import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.adapters.ListViewCountryAdapter;
import com.example.marco.world_bank.Parse;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.async.AsyncCountryParse;
import com.example.marco.world_bank.entities.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CountryActivity extends Activity {

    // Declare Variables
    ListView list;
    ListViewCountryAdapter adapter;
    EditText editsearch;
    String[] name;
    String[] population;
    int[] flag;
    Intent intent ;
    //Bundle bd;
    List<Country> countryList = new ArrayList<>();
    Parse parse = new Parse(this);
    private Context context = this;
    private Activity activity = this;

    /**
     * Allows to take a list of countries and put it, through ListViewCountryadapter, in country_activity's list view.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_activity);

        Intent intent = getIntent();
        int choice = intent.getIntExtra("CHOICE",0);
        String indicatorId = null;
      // Verify choice value, if choice=2 is identified this path topic->indicator->country
        if (choice == 2){
            indicatorId = intent.getStringExtra("INDICATOR_ID");
        }
        list =  findViewById(R.id.lvCountries);

        //Query Table Country
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.open();
        countryList = databaseHelper.getAllCounty();
        databaseHelper.close();



        /*List<Country> countryList = null;
        AsyncTask<Void,Void,List<Country>> asyncTask = new AsyncCountryParse(this);
        try {
            countryList = asyncTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        // Pass results to ListViewAdapter Class
        adapter = new ListViewCountryAdapter(this, countryList, choice,indicatorId,activity);
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