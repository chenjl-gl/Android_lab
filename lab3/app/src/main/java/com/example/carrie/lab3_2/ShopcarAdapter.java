package com.example.carrie.lab3_2;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by carrie on 2017/10/21.
 */

public class ShopcarAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Pair<Pair<String,String>,String>> sc_product = new ArrayList<>();

    public ShopcarAdapter(Context context, ArrayList<Pair<Pair<String,String>,String>> data){
            this.context = context;
            this.sc_product = data;
    }
    @Override
    public int getCount() {
        return sc_product == null ? 0 : sc_product.size();
    }

    @Override
    public Object getItem(int position) {
        return sc_product == null ? null : sc_product.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder holder;
        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shopcar_layout, null);
            holder = new ViewHolder();
            holder. letter = (TextView)convertView.findViewById(R.id.shopcar_letter);
            holder.item = (TextView)convertView.findViewById(R.id.shopcar_item);
            holder.price = (TextView)convertView.findViewById(R.id.shopcar_price);
            convertView.setTag(holder);
        } else {
            convertView = view;
            holder = (ViewHolder) convertView.getTag();
        }
        holder.letter.setText(sc_product.get(position).first.first);
        holder.item.setText(sc_product.get(position).first.second);
        holder.price.setText(sc_product.get(position).second);
        return convertView;
    }
    private class ViewHolder {
        TextView letter;
        TextView item;
        TextView price;
    }
}
