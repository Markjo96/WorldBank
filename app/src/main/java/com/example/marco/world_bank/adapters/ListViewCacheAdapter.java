package com.example.marco.world_bank.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marco.world_bank.activity.DescriptionActivity;
import com.example.marco.world_bank.activity.GraphActivity;
import com.example.marco.world_bank.entities.Cache;
import com.example.marco.world_bank.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        //Inflating the listview so that items in even position have a certain layout
        //and the others in odd position have on other layout.
        row = view;
        if (view == null) {
            holder = new ViewHolder();
            //Inflating the listview so that items in even position have a certain layout
            //and the others in odd position have on other layout.
            row = inflater.inflate(i % 2 == 0 ? R.layout.custom_listview_blue : R.layout.custom_listview_white, null);
            holder.name = row.findViewById(R.id.tvCountryName);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        //Setting the text into listview's item textview with country's and
        //indicator's names.
        String display = cacheList.get(i).getCountry().getName()+", "+cacheList.get(i).
                getIndicator().getName();
        holder.name.setText(display);


        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * When clicking on a item of the listview, if the choice value
                 * is 2, the app goes to the GraphActivity.
                 */
                if (choice == 2){
                    //Opening a new Intent passing the url.
                    String uri = cacheList.get(i).getUrl();
                    Intent intent = new Intent(mContext,GraphActivity.class);
                    intent.putExtra("URI",uri);
                    activity.startActivityForResult(intent,1);

                }else{
                    /*
                     * When clicking on a item of the listview, if the choice value
                     * is 1, the app goes to the DescriptionActivity.
                     */
                    Intent intent = new Intent(mContext,DescriptionActivity.class);
                    //The keyName is a string used in the Db for saved images,
                    //the separator '^' is necessary to be able to obtain country's
                    //and indicator's name coming back from Db.
                    String keyName = cacheList.get(i).getCountry().getName()+"^"+
                            cacheList.get(i).getIndicator().getName();
                    //Opening a new Intent passing the keyName.
                    intent.putExtra("KEY_NAME",keyName);
                    mContext.startActivity(intent);


                }
            }
        });

        return row;
    }

    //Method to implement the autocomplete search.
    public void filter(String charText) {
        //Using toLowerCase allows the user to search either
        //in lower case and in upper case.
        charText = charText.toLowerCase(Locale.getDefault());
        cacheList.clear();
        //If the user has not written in the search bar, the listview
        //is full.
        if (charText.length() == 0) {
            cacheList.addAll(arraylist);
        } else {
            //If the user is writing, it checks if the word written
            //is contained in one or more of the listview's items,
            //searching by country's or indicator's names.
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
    
}
