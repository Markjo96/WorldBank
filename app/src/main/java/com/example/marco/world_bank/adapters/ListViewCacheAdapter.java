package com.example.marco.world_bank.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marco.world_bank.DatabaseHelper1;
import com.example.marco.world_bank.JsonDB;
import com.example.marco.world_bank.activity.DescriptionActivity;
import com.example.marco.world_bank.activity.GraphActivity;
import com.example.marco.world_bank.async.AsyncGraphParse;
import com.example.marco.world_bank.entities.Cache;
import com.example.marco.world_bank.R;
import com.example.marco.world_bank.entities.Graph;
import com.example.marco.world_bank.entities.ImageDao;
import com.example.marco.world_bank.entities.JsonDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class ListViewCacheAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Cache> cacheList = null;
    private ArrayList<Cache> arraylist;
    private int choice;
    private Activity activity;

    public ListViewCacheAdapter(Context mContext, List<Cache> cacheList, int choice, Activity activity) {
        this.mContext = mContext;
        this.cacheList=cacheList;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(cacheList);
        inflater = LayoutInflater.from(mContext);
        this.choice = choice;
        this.activity = activity;
    }




    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return cacheList.size();
    }

    @Override
    public Object getItem(int position) {
        return cacheList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        ViewHolder holder;
        View row = null;
        view = null;
        row = view;
        if (view == null) {
            holder = new ViewHolder();
            row = inflater.inflate(i % 2 == 0 ? R.layout.custom_listview_blue : R.layout.custom_listview_white, null);
            // Locate the TextViews in listview_item.xml
            // Locate the ImageView in listview_item.xml
            holder.name = row.findViewById(R.id.tvCountryName);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        String display = cacheList.get(i).getCountry().getName()+", "+cacheList.get(i).
                getIndicator().getName();
        holder.name.setText(display);


        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choice == 2){
                    String uri = cacheList.get(i).getUrl();
                    Intent intent = new Intent(mContext,GraphActivity.class);
                    intent.putExtra("URI",uri);
                    activity.startActivityForResult(intent,1);

                }else{

                    Intent intent = new Intent(mContext,DescriptionActivity.class);
                    String keyName = cacheList.get(i).getCountry().getName()+"^"+
                            cacheList.get(i).getIndicator().getName();
                    intent.putExtra("KEY_NAME",keyName);
                    mContext.startActivity(intent);


                    /*String image_name = cacheList.get(i).getCountry().getName() +"^"+cacheList.
                            get(i).getIndicator().getName();
                    ImageDao imageDao = new ImageDao(image_name, null);
                    DatabaseHelper1 databaseHelper1 = new DatabaseHelper1(mContext);
                    databaseHelper1.open();
                    Cursor cursor = databaseHelper1.getImg(imageDao);
                    byte[] bytes = new byte[0];
                    if (cursor.moveToNext()){
                        bytes = cursor.getBlob(cursor.getColumnIndex("image_data"));
                    }

                    Intent intent = new Intent(mContext, DescriptionActivity.class);
                    intent.putExtra("BYTES",bytes);
                    mContext.startActivity(intent);*/


                }
            }
        });

        return row;
    }

    // Filter Class
    public void filterSavedImg(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        cacheList.clear();
        if (charText.length() == 0) {
            cacheList.addAll(arraylist);
        } else {
            for (Cache cache : arraylist) {
                if (cache.getCountry().getName().toLowerCase(Locale.getDefault()).
                        contains(charText) || cache.getIndicator().getName().
                        toLowerCase(Locale.getDefault()).contains(charText)) {
                    cacheList.add(cache);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterOfflineUpload(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        cacheList.clear();
        if (charText.length() == 0) {
            cacheList.addAll(arraylist);
        } else {
            for (Cache cache : arraylist) {
                if (cache.getUrl().toLowerCase(Locale.getDefault()).contains(charText)) {
                    cacheList.add(cache);
                }
            }
        }
        notifyDataSetChanged();
    }

}
