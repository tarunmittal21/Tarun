package com.perraulthealth.perraulthealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.perraulthealth.perraulthealth.R;
import com.perraulthealth.perraulthealth.model.ItemSlideMenu;

import java.util.List;

public class SlidingMenuAdapter extends BaseAdapter {

    private Context context;
    private List<ItemSlideMenu> Item;

    public SlidingMenuAdapter (Context context ,  List<ItemSlideMenu> Item){
        this.context = context;
        this.Item = Item;
    }
    @Override
    public int getCount() {
        return Item.size();
    }

    @Override
    public Object getItem(int position) {
        return Item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.signout_sliding_menu  ,null);
        ImageView im = (ImageView)v.findViewById(R.id.imageViewSignout);
        TextView tv =  (TextView)v.findViewById(R.id.textViewSignout);

        ItemSlideMenu item = Item.get(position);
        im.setImageResource(item.getImgId());
        tv.setText(item.getTitle());
        return v;
    }
}
