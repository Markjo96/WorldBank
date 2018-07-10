package com.example.marco.world_bank.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.marco.world_bank.R;
import com.example.marco.world_bank.activity.DescriptionActivity;
import com.example.marco.world_bank.activity.IndicatorActivity;
import com.example.marco.world_bank.async.AsyncIndicatorParse;
import com.example.marco.world_bank.async.AsyncQuery;
import com.example.marco.world_bank.entities.Indicator;
import com.example.marco.world_bank.entities.Topic;

public class ListViewTopicAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Topic> topicList = null;
    private ArrayList<Topic> arraylist;
    private int choice;
    private String isoCode2;

    public ListViewTopicAdapter(Context context, List<Topic> topicList, int choice,String isoCode2) {
        this.mContext = context;
        this.topicList = topicList;
        //this.bd=bd;
        this.inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Topic>();
        this.arraylist.addAll(topicList);
        this.choice=choice;
        this.isoCode2 = isoCode2;
    }

    public class ViewHolder {
        TextView name;}

    @Override
    public int getCount() {
        return topicList.size();
    }

    @Override
    public Topic getItem(int position) {
        return topicList.get(position);
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
            row = inflater.inflate(i % 2 == 0 ? R.layout.custom_listview_white : R.layout.custom_listview_blue, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) row.findViewById(R.id.tvCountryName);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(topicList.get(i).getValue());
        // Listen for ListView Item Click


        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String topicId = topicList.get(i).getId();
                Intent indicatorIntent = new Intent(mContext,IndicatorActivity.class);
                indicatorIntent.putExtra("TOPIC_ID",topicId);
                indicatorIntent.putExtra("CHOICE",choice);
                if (choice == 1){
                    indicatorIntent.putExtra("ISOCODE",isoCode2);
                }

                mContext.startActivity(indicatorIntent);

            }
        });

        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("NAME",arraylist.get(i).getValue());
                intent.putExtra("NOTE",arraylist.get(i).getSourceNote());
                intent.putExtra("CHOICE",1);
                mContext.startActivity(intent);
                return true;
            }
        });

        return row;
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        topicList.clear();
        if (charText.length() == 0) {
            topicList.addAll(arraylist);
        } else {
            for (Topic topic : arraylist) {
                if (topic.getValue().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    topicList.add(topic);
                }
            }
        }
        notifyDataSetChanged();
    }

}
