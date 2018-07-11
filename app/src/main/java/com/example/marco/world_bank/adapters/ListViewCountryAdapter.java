package com.example.marco.world_bank.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
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

import com.example.marco.world_bank.R;
import com.example.marco.world_bank.activity.GraphActivity;
import com.example.marco.world_bank.activity.TopicActivity;
import com.example.marco.world_bank.entities.Country;

public class ListViewCountryAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Country> countryList = null;
    private ArrayList<Country> arraylist;
    private int choice;
    private String indicatorId;
    private Activity activity;

    public ListViewCountryAdapter(Context context, List<Country> countryList, int choice,
                                  String indicatorId,Activity activity) {
        mContext = context;
        this.countryList = countryList;
        this.bd=bd;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(countryList);
        this.choice = choice;
        this.indicatorId = indicatorId;
        this.activity = activity;

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
        //Using two views so that the listview doesn't loose memory
        //of the single item layout when scrolling.
        row = view;
        if (view == null) {
            holder = new ViewHolder();
            //Inflating the listview so that items in even position have a certain layout
            //and the others in odd position have on other layout.
            row = inflater.inflate(i % 2 == 0 ? R.layout.custom_listview_blue : R.layout.custom_listview_white, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) row.findViewById(R.id.tvCountryName);

        } else {
            holder = (ViewHolder) row.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(countryList.get(i).getName());



        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /*
                 * When clicking on a item of the listview, if the choice value
                 * is 1, the app goes to the TopicActivity.
                 */
                if (choice == 1){
                    //Send choice and isoCode2 to TopicActivity
                    //Opening a new Intent passing the isocode and the choice's value.
                    Intent intent = new Intent(mContext, TopicActivity.class);
                    intent.putExtra("ISOCODE",countryList.get(i).getIso2Code());
                    intent.putExtra("CHOICE", choice);
                    mContext.startActivity(intent);
                }
                /*
                 * When clicking on a item of the listview, if the choice value
                 * is 2, the app goes to the GraphActivity.
                 */
                else{
                    String isoCode2 = countryList.get(i).getIso2Code();
                    String uri = "http://api.worldbank.org/v2/countries/"+isoCode2+"/indicators/"+
                            indicatorId+"?per_page=100&format=json";
                    //Opening a new Intent passing the created url, the country's isocode
                    //and the indicator's id.
                    Intent intent = new Intent(mContext,GraphActivity.class);
                    intent.putExtra("URI",uri);
                    intent.putExtra("ISO",isoCode2);
                    intent.putExtra("INDICATOR_ID",indicatorId);
                    activity.startActivityForResult(intent,1);
                }
            }
        });

        return row;
    }

    //Method to implement the autocomplete search
    public void filter(String charText) {
        //Using toLowerCase allows the user to search either
        //in lower case and in upper case.
        charText = charText.toLowerCase(Locale.getDefault());
        countryList.clear();
        //If the user has not written in the search bar, the listview
        //is full.
        if (charText.length() == 0) {
            countryList.addAll(arraylist);
        } else {
            //If the user is writing, it checks if the word written
            //is contained in one or more of the listview's items,
            //searching by the country's name.
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
