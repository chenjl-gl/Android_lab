package com.example.carrie.lab3_2;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


/**
 * Created by carrie on 2017/10/21.
 */

public class Showproduct extends AppCompatActivity {
    String[] Bottomch = new String[]{"一键下单","分享商品","不感兴趣","查看更多商品促销信息"};
//    private final Receiver dynamicreceiver = new Receiver();
    private final MyWidget dynamicreceiver = new MyWidget();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productinfo);

        initproduct();
        ListView listtv = (ListView)findViewById(R.id.ltv);
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this,R.layout.bottom_choose,Bottomch);
        listtv.setAdapter(adapter);

        Intent intent = getIntent();
        final String item = intent.getStringExtra("item");
        final String price = intent.getStringExtra("Price");
        final String letter = intent.getStringExtra("letter");
        final String type = intent.getStringExtra("Type");
        final String info = intent.getStringExtra("Info");
        final int[] flag = {0};
        final int img;
        switch (item) {
            case "Enchated Forest":
                img = R.mipmap.pic_enchatedforest;
                break;
            case "Arla Milk":
                img = R.mipmap.pic_arla;
                break;
            case "Devondale Milk":
                img = R.mipmap.pic_devondale;
                break;
            case "Kindle Oasis":
                img = R.mipmap.pic_kindle;
                break;
            case "waitrose 早餐麦片":
                img = R.mipmap.pic_waitrose;
                break;
            case "Mcvitie's 饼干":
                img = R.mipmap.pic_mcvitie;
                break;
            case "Ferrero Rocher":
                img = R.mipmap.pic_ferrero;
                break;
            case "Maltesers":
                img = R.mipmap.pic_maltesers;
                break;
            case "Lindt":
                img = R.mipmap.pic_lindt;
                break;
            case "Borggreve":
                img = R.mipmap.pic_borggreve;
                break;
            default:
                img = R.mipmap.pic_borggreve;
                break;
        }

        ImageButton back = (ImageButton)findViewById(R.id.back);
        View.OnClickListener backlast = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Intent shopcarintent = new Intent();
                if(flag[0] == 0){
//                    setResult(0,shopcarintent);
                    finish();
                }
                else{
//                    shopcarintent.putExtra("letter",letter);
//                    shopcarintent.putExtra("price",price);
//                    shopcarintent.putExtra("item",item);
//                    setResult(0,shopcarintent);
                    finish();
                }
            }
        };
        back.setOnClickListener(backlast);

        final ImageButton star = (ImageButton)findViewById(R.id.star);
        star.setImageResource(R.mipmap.empty_star);
        View.OnClickListener starlast = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(star.getTag()=="1"){
                    star.setImageResource(R.mipmap.empty_star);
                    star.setTag("0");
                }
                else{
                    star.setImageResource(R.mipmap.full_star);
                    star.setTag("1");
                }
            }
        };
        star.setOnClickListener(starlast);
        registerRec();
        ImageView shopcaradd = (ImageView)findViewById(R.id.shoplist_add);
        View.OnClickListener shopcarclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "商品已添加到购物车", Toast.LENGTH_SHORT).show();

                Event event = new Event(item,price,letter,type,info);
                EventBus.getDefault().post(event);

                final String DYNAMICACTION = "com.example.MyWidgetDynamicFilter";
                Intent intentbroadcast = new Intent(DYNAMICACTION);
                intentbroadcast.putExtra("notification_item", item);
                intentbroadcast.putExtra("notification_price", price);
                intentbroadcast.putExtra("notification_img", img);
                intentbroadcast.putExtra("letter",letter);
                sendBroadcast(intentbroadcast);
                if(flag[0] == 0){
                    flag[0] = 1;
                }
            }
        };
        shopcaradd.setOnClickListener(shopcarclick);
    }

    public void registerRec(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.example.MyWidgetDynamicFilter");
        registerReceiver(dynamicreceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(dynamicreceiver);
        super.onDestroy();
    }

    private void initproduct(){
        Intent intent = getIntent();
        final String item = intent.getStringExtra("item");
        final String price = intent.getStringExtra("Price");
        final String type = intent.getStringExtra("Type");
        final String info = intent.getStringExtra("Info");


        final TextView name = (TextView)findViewById(R.id.pro_name);
        final TextView pricetv = (TextView)findViewById(R.id.price);
        final TextView typetv = (TextView)findViewById(R.id.type);
        final TextView infotv = (TextView)findViewById(R.id.pro_info);
        name.setText(item);
        pricetv.setText(price);
        typetv.setText(type);
        infotv.setText(info);

        ImageView pic = (ImageView)findViewById(R.id.pro_pic);
        final int img;
        switch (item) {
            case "Enchated Forest":
                img = R.mipmap.pic_enchatedforest;

                break;
            case "Arla Milk":
                img = R.mipmap.pic_arla;
                break;
            case "Devondale Milk":
                img = R.mipmap.pic_devondale;
                break;
            case "Kindle Oasis":
                img = R.mipmap.pic_kindle;
                break;
            case "waitrose 早餐麦片":
                img = R.mipmap.pic_waitrose;
                break;
            case "Mcvitie's 饼干":
                img = R.mipmap.pic_mcvitie;
                break;
            case "Ferrero Rocher":
                img = R.mipmap.pic_ferrero;
                break;
            case "Maltesers":
                img = R.mipmap.pic_maltesers;
                break;
            case "Lindt":
                img = R.mipmap.pic_lindt;
                break;
            case "Borggreve":
                img = R.mipmap.pic_borggreve;
                break;
            default:
                img = R.mipmap.pic_borggreve;
                break;
        }
        pic.setImageResource(img);
    }

}
