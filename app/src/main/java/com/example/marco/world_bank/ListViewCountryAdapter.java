package com.example.marco.world_bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ListViewCountryAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Country> countryList = null;
    private ArrayList<Country> arraylist;

    public ListViewCountryAdapter(Context context, List<Country> countryList) {
        mContext = context;
        this.countryList = countryList;
        this.bd=bd;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Country>();
        this.arraylist.addAll(countryList);
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
            holder.population = (TextView) row.findViewById(R.id.tvCountryPopulation);
            // Locate the ImageView in listview_item.xml
            holder.flag = (ImageView) row.findViewById(R.id.ivFlag);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(countryList.get(i).getName());
        /*holder.population.setText(countryList.get(i)
                .getPopulation());*/
        // Set the results into ImageView
        /*holder.flag.setImageResource(countryList.get(i)
                .getFlag());*/
        // Listen for ListView Item Click
        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                //if (bd == null){
                    Intent intent = new Intent(mContext, TopicActivity.class);
                    intent.putExtra("name",
                            (countryList.get(i).getName()));
                    mContext.startActivity(intent);
                /*}
                else{
                    System.out.println("Do graphics");
                }*/
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
