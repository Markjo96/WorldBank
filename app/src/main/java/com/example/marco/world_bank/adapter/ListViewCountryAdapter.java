package com.example.marco.world_bank.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.marco.world_bank.R;
import com.example.marco.world_bank.activity.TopicActivity;
import com.example.marco.world_bank.async.AsyncGraphParse;
import com.example.marco.world_bank.async.AsyncQuery;
import com.example.marco.world_bank.model.Country;
import com.example.marco.world_bank.model.Graph;

public class ListViewCountryAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Country> countryList = null;
    private ArrayList<Country> arraylist;
    private int choice;
    private String indicatorId;

    public ListViewCountryAdapter(Context context, List<Country> countryList, int choice,String indicatorId) {
        mContext = context;
        this.countryList = countryList;
        this.bd=bd;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(countryList);
        this.choice = choice;
        this.indicatorId = indicatorId;

    }

    public class ViewHolder {
        TextView name;
        TextView population;
        ImageView flag;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Country getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int i, View view, ViewGroup parent) {
        ViewHolder holder;
        View row = null;
        view = null;
        row = view;
        if (view == null) {
            holder = new ViewHolder();
            row = inflater.inflate(i % 2 == 0 ? R.layout.custom_listview_blue : R.layout.custom_listview_white, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) row.findViewById(R.id.tvCountryName);
            /*holder.population = (TextView) row.findViewById(R.id.tvCountryPopulation);
            // Locate the ImageView in listview_item.xml
            holder.flag = (ImageView) row.findViewById(R.id.ivFlag);
            row.setTag(holder);*/
        } else {
            holder = (ViewHolder) row.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(countryList.get(i).getName());



        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                if (choice == 1){
                    //Send choice and isoCode2 to TopicActivity
                    Intent intent = new Intent(mContext, TopicActivity.class);
                    intent.putExtra("ISOCODE",countryList.get(i).getIso2Code());
                    intent.putExtra("CHOICE", choice);
                    mContext.startActivity(intent);
                }
                else{
                    String isoCode2 = countryList.get(i).getIso2Code();
                    String uri = "api.worldbank.org/v2/countries/"+isoCode2+"/indicators/"+
                            indicatorId+"?per_page=100&format=json";
                    AsyncTask<String,Void,String> asyncTask = new AsyncQuery();
                    String json = null;
                    try {
                        json = asyncTask.execute(uri).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    AsyncTask<String,Void,List<Graph>> asyncTaskParse = new AsyncGraphParse();

                    try {
                        List<Graph> graphList = asyncTaskParse.execute(json).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                    System.out.println("Do graphics");
                }
            }
        });

        return row;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        countryList.clear();
        if (charText.length() == 0) {
            countryList.addAll(arraylist);
        } else {
            for (Country country : arraylist) {
                if (country.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    countryList.add(country);
                }
            }
        }
        notifyDataSetChanged();
    }

}
