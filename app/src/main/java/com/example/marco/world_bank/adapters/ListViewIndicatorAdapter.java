package com.example.marco.world_bank.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.marco.world_bank.DatabaseHelper;
import com.example.marco.world_bank.JsonDB;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.activity.CountryActivity;
import com.example.marco.world_bank.activity.DescriptionActivity;
import com.example.marco.world_bank.activity.GraphActivity;
import com.example.marco.world_bank.activity.IndicatorActivity;
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
                    String uri = "http://api.worldbank.org/v2/countries/"+isoCode2+"/indicators/"+
                            indicatorId+"?per_page=100&format=json";


                    //Query BD
                    /*DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
                    databaseHelper.open();
                    JsonDao jsonDao = new JsonDao(uri,null,null,null);
                    Cursor cursor = databaseHelper.getJson(jsonDao);

                    if (cursor.moveToNext()){
                        json = cursor.getString(cursor.getColumnIndex("json"));
                    }
                    databaseHelper.close();


                    if(json == null ){

                        //Add json to DB
                        DatabaseHelper databaseHelper1 = new DatabaseHelper(mContext);
                        databaseHelper1.open();
                        String coutryName = databaseHelper1.getCountryNameByIso(isoCode2);
                        String indicatorName = databaseHelper.getNameIndicator(indicatorId);

                        JsonDao jsonDao1 = new JsonDao(uri,coutryName,indicatorName,json);
                        databaseHelper1.addJson(jsonDao1);
                        databaseHelper1.close();
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
                    //Check data from internet
                    if (graphList == null){
                        Toast.makeText(mContext,"GRAPH NOT AVALIABLE",Toast.LENGTH_SHORT).show();
                        return;
                    }*/



                    Intent intent = new Intent(mContext,GraphActivity.class);
                    intent.putExtra("ISO",isoCode2);
                    intent.putExtra("INDICATOR_NAME",indicatorList.get(i).getName());
                    intent.putExtra("URI",uri);

                    System.out.println("Do graphics");
                    activity.startActivityForResult(intent,1);
                }
            }
        });



        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
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
