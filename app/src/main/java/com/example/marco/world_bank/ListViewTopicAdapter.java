package com.example.marco.world_bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class ListViewTopicAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Topic> topicList = null;
    private ArrayList<Topic> arraylist;
    private int choice;

    public ListViewTopicAdapter(Context context, List<Topic> topicList, int choice) {
        this.mContext = context;
        this.topicList = topicList;
        //this.bd=bd;
        this.inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Topic>();
        this.arraylist.addAll(topicList);
        this.choice=choice;
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
                String uri = "http://api.worldbank.org/v2/topics/"+topicId+"/indicators?per_page=16700&format=json";
                AsyncTask<String,Void,String> at1 = new AsyncQuery();
                String json = null;
                try {
                    json = at1.execute(uri).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                //System.out.println(json);
                AsyncTask<String,Void,List<Indicator>> at = new AsyncIndicatorParse(mContext);

                List<Indicator> indicatorList = null;
                try {
                    indicatorList = at.execute(json).get();
                } catch (InterruptedException|ExecutionException e) {
                    e.printStackTrace();
                }
                //List<Indicator> indicatorList= parse.parseJsonIndicator();

                Intent indicatorIntent = new Intent(mContext,IndicatorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) indicatorList);
                indicatorIntent.putExtras(bundle);
                indicatorIntent.putExtra("CHOICE",choice);

                indicatorIntent.putExtra("SIZE",indicatorList.size());

                mContext.startActivity(indicatorIntent);

                //return;
                //topicList.get(i);
                // Send single item click data to SingleItemView Class
                //if (bd == null){
                /*Intent intent = new Intent(mContext, TopicActivity.class);
                intent.putExtra("name",
                        (topicList.get(i).getName()));
                mContext.startActivity(intent);*/
                //System.out.println(topicList.get(i).getValue());
                /*}
                else{
                    System.out.println("Do graphics");
                }*/
            }
        });

        return row;
    }

    public final String getJSON(String uri){
        return new AsyncQuery().execute(uri).toString();
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
