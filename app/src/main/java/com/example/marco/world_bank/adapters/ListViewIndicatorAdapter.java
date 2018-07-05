package com.example.marco.world_bank.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import com.example.marco.world_bank.JsonDB;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.activity.CountryActivity;
import com.example.marco.world_bank.activity.GraphActivity;
import com.example.marco.world_bank.async.AsyncGraphParse;
import com.example.marco.world_bank.async.AsyncQuery;
import com.example.marco.world_bank.entities.Graph;
import com.example.marco.world_bank.entities.Indicator;
import com.example.marco.world_bank.entities.JsonDao;


public class ListViewIndicatorAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private Bundle bd;
    private List<Indicator> indicatorList = null;
    private ArrayList<Indicator> arraylist;
    private int choice;
    private String isoCode2;

    public ListViewIndicatorAdapter(Context context, List<Indicator> indicatorList,int choice,String isoCode2) {
        mContext = context;
        this.indicatorList = indicatorList;
        //this.bd=bd;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Indicator>();
        this.arraylist.addAll(indicatorList);
        this.choice = choice;
        this.isoCode2 = isoCode2;
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
        holder.name.setText(indicatorList.get(i).getName());
        // Listen for ListView Item Click



        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                if (choice == 2){
                    Intent intent = new Intent(mContext, CountryActivity.class);
                    intent.putExtra("INDICATOR_ID", indicatorList.get(i).getId());
                    intent.putExtra("CHOICE",2);
                    mContext.startActivity(intent);
                    System.out.println("Indicator activity");
                } else{

                    String indicatorId = indicatorList.get(i).getId();
                    Log.i("IO",isoCode2);
                    Log.i("IO",indicatorId);
                    String uri = "http://api.worldbank.org/v2/countries/"+isoCode2+"/indicators/"+
                            indicatorId+"?per_page=100&format=json";
                    String json = null;


                    //Query BD
                    JsonDB jsonDB = new JsonDB(mContext);
                    jsonDB.open();
                    //jsonDB.deleteTable();
                    JsonDao jsonDao = new JsonDao(uri,null);
                    Cursor cursor = jsonDB.getJson(jsonDao);
                    if (cursor.moveToNext()){
                        json = cursor.getString(cursor.getColumnIndex("json"));
                    }
                    if(json == null ){
                        AsyncTask<String,Void,String> asyncTask = new AsyncQuery();
                        try {
                            json = asyncTask.execute(uri).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        //Add json to DB
                        jsonDao.setJson(json);
                        jsonDB.addJson(jsonDao);
                    }

                    AsyncTask<String,Void,List<Graph>> asyncTaskParse = new AsyncGraphParse(mContext);
                    List<Graph> graphList = null;
                    try {
                        graphList = asyncTaskParse.execute(json).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(mContext,GraphActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("GRAPH_DATA", (ArrayList<? extends Parcelable>)
                            graphList);
                    intent.putExtras(bundle);
                    System.out.println("Do graphics");
                    mContext.startActivity(intent);
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
