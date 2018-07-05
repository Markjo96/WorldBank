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
import android.widget.TextView;
import android.view.View.OnClickListener;


public class ListViewIndicatorAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Indicator> indicatorList = null;
    private ArrayList<Indicator> arraylist;
    private int choice;

    public ListViewIndicatorAdapter(Context context, List<Indicator> indicatorList,int choice) {
        mContext = context;
        this.indicatorList = indicatorList;
        //this.bd=bd;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Indicator>();
        this.arraylist.addAll(indicatorList);
        this.choice = choice;
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
        row = view;
        if (view == null) {
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.custom_listview_white, null);
            // Locate the TextViews in listview_item.xml
            holder.name =  row.findViewById(R.id.tvCountryName);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(indicatorList.get(i).getId());
        // Listen for ListView Item Click



        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                if (choice == 2){
                Intent intent = new Intent(mContext, CountryActivity.class);
                intent.putExtra("name",
                        (indicatorList.get(i).getName()));
                mContext.startActivity(intent);
                System.out.println("Indicator activity");
                } else{
                    System.out.println("Do graphics");
                }
            }
        });

        return row;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        indicatorList.clear();
        if (charText.length() == 0) {
            indicatorList.addAll(arraylist);
        } else {
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
