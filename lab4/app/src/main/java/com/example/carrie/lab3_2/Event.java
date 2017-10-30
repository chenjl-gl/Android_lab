package com.example.carrie.lab3_2;

import android.support.v4.util.Pair;

/**
 * Created by carrie on 2017/10/29.
 */

public class Event {
    private String mitem;
    private String mprice;
    private String mletter;
    private String mtype;
    private String minfo;
    public  Event(String item,String price,String letter,String type,String info) {
        mitem = item;
        mprice = price;
        mletter = letter;
        mtype = type;
        minfo = info;
    }
    public String getitem(){
        return mitem;
    }
    public String getletter(){
        return mletter;
    }
    public String gettype(){
        return mtype;
    }
    public String getinfo(){
        return minfo;
    }
    public String getprice(){
        return mprice;
    }
}
