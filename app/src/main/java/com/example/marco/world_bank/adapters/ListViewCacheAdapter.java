package com.example.marco.world_bank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marco.world_bank.entities.Cache;
import com.example.marco.world_bank.entities.Image;
import com.example.marco.world_bank.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewCacheAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Cache> cacheList = null;
    private ArrayList<Cache> arraylist;

    public ListViewCacheAdapter(Context mContext, List<Cache> cacheList) {
        this.mContext = mContext;
        this.cacheList=cacheList;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(cacheList);
        inflater = LayoutInflater.from(mContext);
    }


    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        return row;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        cacheList.clear();
        if (charText.length() == 0) {
            cacheList.addAll(arraylist);
        } else {
            for (Cache cache : arraylist) {
                if (cache.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    cacheList.add(cache);
                }
            }
        }
        notifyDataSetChanged();
    }
}
