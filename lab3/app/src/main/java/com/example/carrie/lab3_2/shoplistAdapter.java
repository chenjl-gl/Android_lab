package com.example.carrie.lab3_2;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by carrie on 2017/10/20.
 */

public class shoplistAdapter extends RecyclerView.Adapter<shoplistAdapter.ViewHolder>{
    private ArrayList<Pair<String,String>> Data;

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public shoplistAdapter(ArrayList<Pair<String,String>> data) {
        this.Data = data;
    }

    public void updateData(ArrayList<Pair<String,String>> data) {
        this.Data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        holder = new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_layout, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        holder.letter.setText(Data.get(position).first);
        holder.item.setText(Data.get(position).second);
        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Data == null ? 0 : Data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView letter;
        TextView item;

        public ViewHolder(View itemView) {
            super(itemView);
            letter = (TextView) itemView.findViewById(R.id.first_letter);
            item = (TextView) itemView.findViewById(R.id.item_name);
        }
    }


}


