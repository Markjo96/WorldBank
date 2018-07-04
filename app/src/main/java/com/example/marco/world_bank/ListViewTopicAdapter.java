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

import com.example.marco.world_bank.R;
import com.example.marco.world_bank.Topic;

public class ListViewTopicAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Topic> topicList = null;
    private ArrayList<Topic> arraylist;

    public ListViewTopicAdapter(Context context, List<Topic> topicList) {
        mContext = context;
        this.topicList = topicList;
        //this.bd=bd;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Topic>();
        this.arraylist.addAll(topicList);
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
            holder.name.setY(30);
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
                String uri = "http://api.worldbank.org/v2/topics/"+topicId+"/indicators?format=json";
                String json = getJSON(uri);

                Parse parse = new Parse(mContext);
                List<Indicator> indicatorList= parse.parseJsonIndicator();
                ArrayList<String> listId = new ArrayList<>();
                ArrayList<String> listValue = new ArrayList<>();
                ArrayList<String> listNote = new ArrayList<>();
                for (int i=0;i<indicatorList.size();i++){
                    String indicatorId = indicatorList.get(i).getId();
                    String indicatorValue = indicatorList.get(i).getName();
                    String indicatorNote = indicatorList.get(i).getSourceNote();

                    listId.add(indicatorId);
                    listValue.add(indicatorValue);
                    listNote.add(indicatorNote);
                }
                Intent indicatorIntent = new Intent(mContext,IndicatorActivity);
                indicatorIntent.putStringArrayListExtra("ID",listId);
                indicatorIntent.putStringArrayListExtra("VALUE",listValue);
                indicatorIntent.putStringArrayListExtra("NOTE",listNote);

                mContext.startActivity(indicatorIntent);

                return;
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
        return new AsyncQuery().doInBackground(uri);
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
