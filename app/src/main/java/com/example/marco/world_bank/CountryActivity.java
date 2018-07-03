package com.example.marco.world_bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    List<Country> arraylist = new ArrayList<>();
    Parse parse = new Parse(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_activity);

        // Generate sample data

        /*name = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
                "Russia", "Japan" };

        population = new String[] { "1,354,040,000", "1,210,193,422",
                "315,761,000", "237,641,326", "193,946,886", "182,912,000",
                "170,901,000", "152,518,015", "143,369,806", "127,360,000" };

        flag = new int[] {R.mipmap.china, R.mipmap.india,
                R.mipmap.unitedstates, R.mipmap.indonesia,
                R.mipmap.brazil, R.mipmap.pakistan, R.mipmap.nigeria,
                R.mipmap.bangladesh, R.mipmap.russia, R.mipmap.japan };*/

        // Locate the ListView in listview_main.xml
        list =  findViewById(R.id.lvCountries);
        //intent = getIntent();
        //bd = intent.getExtras();
        //System.out.println(intent);

        /*for (int i = 0; i < name.length; i++)
        {
            Country country = new Country(name[i],
                    population[i], flag[i]);
            // Binds all strings into an array
            arraylist.add(country);
        }*/
        arraylist=parse.parseJsonCountry();
        // Pass results to ListViewAdapter Class
        adapter = new ListViewCountryAdapter(this, arraylist);
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