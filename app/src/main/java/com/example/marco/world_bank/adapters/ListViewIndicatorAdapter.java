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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.marco.world_bank.R;
import com.example.marco.world_bank.activity.CountryActivity;
import com.example.marco.world_bank.activity.DescriptionActivity;
import com.example.marco.world_bank.activity.GraphActivity;
import com.example.marco.world_bank.entities.Indicator;


public class ListViewIndicatorAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Indicator> indicatorList = null;
    private ArrayList<Indicator> arraylist;
    private int choice;
    private String isoCode2;
    private ProgressBar pbIndicator;
    private Activity activity;

    public ListViewIndicatorAdapter(Context context, List<Indicator> indicatorList,int choice,
                                    String isoCode2, Activity activity ) {
        mContext = context;
        this.indicatorList = indicatorList;
        //this.bd=bd;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Indicator>();
        this.arraylist.addAll(indicatorList);
        this.choice = choice;
        this.isoCode2 = isoCode2;
        this.pbIndicator = pbIndicator;
        this.activity = activity;
    }

    public class ViewHolder {
        TextView name;}

    @Override
    public int getCount() {
        return indicatorList.size();
    }

    @Override
    public Indicator getItem(int position) {
        return indicatorList.get(position);
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
            //Inflating the listview so that items have a given layout.
            row = inflater.inflate(R.layout.custom_listview_white, null);
            // Locate the TextViews in listview_item.xml
            holder.name =  row.findViewById(R.id.tvCountryName);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(indicatorList.get(i).getName());



        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /* When clicking on a item of the listview, if the choice value
                 * is 2, the app goes to the CountryActivity.
                 */
                if (choice == 2){
                    //Opening a new Intent passing the indicator's id and the choice's value.
                    Intent intent = new Intent(mContext, CountryActivity.class);
                    intent.putExtra("INDICATOR_ID", indicatorList.get(i).getId());
                    intent.putExtra("CHOICE",2);
                    mContext.startActivity(intent);
                    /* When clicking on a item of the listview, if the choice value
                     * is 1, the app goes to the GraphActivity.
                     */
                } else{

                    String indicatorId = indicatorList.get(i).getId();
                    String uri = "http://api.worldbank.org/v2/countries/"+isoCode2+"/indicators/"+
                            indicatorId+"?per_page=100&format=json";
                    //Opening a new Intent passing the created url, the country's isocode
                    //and the indicator's name.
                    Intent intent = new Intent(mContext,GraphActivity.class);
                    intent.putExtra("ISO",isoCode2);
                    intent.putExtra("INDICATOR_NAME",indicatorList.get(i).getName());
                    intent.putExtra("URI",uri);

                    activity.startActivityForResult(intent,1);
                }
            }
        });

        /*
         * When long clicking on a item of the listview, the app goes
         * to the DescriptionActivity.
         */
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
                //Opening a new Intent passing the indicator's name, its description
                //and the choice's value.
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("NAME",indicatorList.get(i).getName());
                intent.putExtra("NOTE",indicatorList.get(i).getSourceNote());
                intent.putExtra("CHOICE",1);
                mContext.startActivity(intent);
                return true;
            }
        });

        return row;
    }

    //Method to implement the autocomplete search
    public void filter(String charText) {
        //Using toLowerCase allows the user to search either
        //in lower case and in upper case.
        charText = charText.toLowerCase(Locale.getDefault());
        indicatorList.clear();
        //If the user has not written in the search bar, the listview
        //is full.
        if (charText.length() == 0) {
            indicatorList.addAll(arraylist);
        } else {
            //If the user is writing, it checks if the word written
            //is contained in one or more of the listview's items,
            //searching by the indicator's name.
            for (Indicator indicator : arraylist) {
                if (indicator.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    indicatorList.add(indicator);
                }
            }
        }
        notifyDataSetChanged();
    }

}
