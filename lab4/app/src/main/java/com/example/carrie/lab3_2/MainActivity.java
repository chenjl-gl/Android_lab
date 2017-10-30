package com.example.carrie.lab3_2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.example.carrie.lab3_2.R.id.shopcar;

public class MainActivity extends AppCompatActivity {
    private RecyclerView shoplist;
    private shoplistAdapter shoplistadapter;
    private RecyclerView.LayoutManager shoplistlayoutmanager;
    private ShopcarAdapter mShopcarAdapter;
     private ListView shopcar;
    String[] Item = new String[]{"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
    String[] Letter = new String[]{"E", "A", "D", "K", "W", "M", "F", "M", "L", "B"};
    String[] Price = new String[]{"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.90", "¥ 132.59", "¥ 141.43", "¥ 139.43", "¥ 28.90"};
    String[] Type = new String[]{"作者", "产地", "产地", "版本", "重量", "产地", "重量", "重量", "重量", "重量"};
    String[] Info = new String[]{"Johanna Basford", "德国", "澳大利亚", "8GB", "2Kg", "英国", "300g", "118g", "249g", "640g"};
    int[] imgs = new int[]{R.mipmap.pic_enchatedforest,R.mipmap.pic_arla,R.mipmap.pic_devondale,R.mipmap.pic_kindle,R.mipmap.pic_waitrose,R.mipmap.pic_mcvitie,R.mipmap.pic_ferrero,R.mipmap.pic_maltesers,R.mipmap.pic_lindt,R.mipmap.pic_borggreve};

    final ArrayList<String> Itemlist = new ArrayList<>();
    final ArrayList<Pair<String,String>> data = new ArrayList<>();
    final ArrayList<Pair<Pair<String,String>,String>> details = new ArrayList<>();
    final ArrayList<Pair<Pair<String,String>,String>> sc_product = new ArrayList<>();
    final ArrayList<Pair<Pair<Pair<String,String>,Pair<Integer,String>>,Pair<String,String>>> Notificaiton_pro = new ArrayList<>();
    final int[] flag = {0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        sc_product.add(Pair.create(Pair.create("*","购物车"),"价格"));

        //给容器传数据
        for (int i = 0; i < 10; i++) {
            Itemlist.add(Item[i]);
            data.add(Pair.create(Letter[i],Item[i]));
            details.add(Pair.create(Pair.create(Price[i],Type[i]),Info[i]));
            Notificaiton_pro.add(Pair.create(Pair.create(Pair.create(Item[i],Price[i]),Pair.create(imgs[i],Letter[i])),Pair.create(Type[i],Info[i])));
        }

        //初始化商品列表
        initshoplist();
        initshoplistview();
        //初始化购物车
        initshopcar();
        //定义两个界面之间的相互转换
        int[] flag = {0};
        twoviewchange();
        sendBroadcast(Boardcast());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Event event){
        Toast.makeText(getApplicationContext(),"onmessage" , Toast.LENGTH_SHORT).show();
        String item = event.getitem();
        String letter = event.getletter();
        String price = event.getprice();
        sc_product.add(Pair.create(Pair.create(letter,item),price));
        mShopcarAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onNewIntent(Intent intent){
        final FloatingActionButton changetocar = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        shoplist.setVisibility(View.INVISIBLE);
        shopcar.setVisibility(View.VISIBLE);
        changetocar.setImageResource(R.mipmap.mainpage);
        flag[0] = 1;

    }

    @Override
    protected void onDestroy(){
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initshoplist() {
        shoplistlayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        shoplistadapter = new shoplistAdapter(data);

        shoplistAdapter.OnItemClickListener shoplistclick = new shoplistAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,Showproduct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("item",data.get(position).second);
                intent.putExtra("Price",details.get(position).first.first);
                intent.putExtra("Type",details.get(position).first.second);
                intent.putExtra("Info",details.get(position).second);
                intent.putExtra("letter",data.get(position).first);
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
                Toast.makeText(getApplicationContext(), "移除第"+(position+1)+"个商品", Toast.LENGTH_SHORT).show();
                data.remove(position);
                details.remove(position);
                shoplistadapter.notifyDataSetChanged();
            }
        };
        shoplistadapter.setOnItemClickListener(shoplistclick);
    }

    private void initshoplistview() {
        shoplist = (RecyclerView) findViewById(R.id.shoppinglist);
        shoplist.setLayoutManager(shoplistlayoutmanager);
        shoplist.setAdapter(shoplistadapter);
    }

    void initDialog(String name,final int position){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("移除商品");
        alertDialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.show().dismiss();
            }});
        alertDialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sc_product.remove(position);
                mShopcarAdapter.notifyDataSetChanged();
            }});
        alertDialog.setMessage("从购物车中移除"+name+"?");
        alertDialog.create();
        alertDialog.show();
    }

    private void twoviewchange(){
        shopcar = (ListView)findViewById(R.id.shopcar);
        final FloatingActionButton changetocar = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        View.OnClickListener toshopcar = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(flag[0] == 0){
                    shoplist.setVisibility(View.INVISIBLE);
                    shopcar.setVisibility(View.VISIBLE);
                    changetocar.setImageResource(R.mipmap.mainpage);
                    flag[0] = 1;
                }
                else{
                    shoplist.setVisibility(View.VISIBLE);
                    shopcar.setVisibility(View.INVISIBLE);
                    changetocar.setImageResource(R.mipmap.shoplist);
                    flag[0] = 0;
                }
            }
        };
        changetocar.setOnClickListener(toshopcar);
    }



    private void initshopcar(){
        final ListView shopcar = (ListView)findViewById(R.id.shopcar);
        shopcar.setVisibility(View.INVISIBLE);
        mShopcarAdapter = new ShopcarAdapter(MainActivity.this,sc_product);
        shopcar.setAdapter(mShopcarAdapter);
        AdapterView.OnItemClickListener shopcarlistener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0 ){
                    String shopcaritem = sc_product.get(position).first.second;
                    int i = Itemlist.indexOf(shopcaritem);
                    Intent intent = new Intent(MainActivity.this,Showproduct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("item",shopcaritem);
                    intent.putExtra("Price",sc_product.get(position).second);
                    intent.putExtra("Type",details.get(i).first.second);
                    intent.putExtra("Info",details.get(i).second);
                    intent.putExtra("letter",sc_product.get(position).first.first);
                    startActivity(intent);
                }
            }

        };
        AdapterView.OnItemLongClickListener deletepro = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0 ) initDialog(sc_product.get(position).first.second,position);
                return true;
            }
        };
        shopcar.setOnItemClickListener(shopcarlistener);
        shopcar.setOnItemLongClickListener(deletepro);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if(resultCode == 0 && requestCode == 0){
//            String letter = intent.getStringExtra("letter");
//            String  price= intent.getStringExtra("price");
//            String item = intent.getStringExtra("item");
//            if(letter != null && price != null && item != null){
//                sc_product.add(Pair.create(Pair.create(letter,item),price));
//                mShopcarAdapter.notifyDataSetChanged();
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, intent);
//    }

    private static final String STATICACTION = "com.example.MyStaticFilter";
    private Intent Boardcast(){
        int n = 10;
        Random random = new Random();
        int i = random.nextInt(n);
        Intent intentbroadcast = new Intent(STATICACTION);
        intentbroadcast.putExtra("notification_item", Notificaiton_pro.get(i).first.first.first);
        intentbroadcast.putExtra("notification_price", Notificaiton_pro.get(i).first.first.second);
        intentbroadcast.putExtra("notification_img", Notificaiton_pro.get(i).first.second.first);
        intentbroadcast.putExtra("letter", Notificaiton_pro.get(i).first.second.second);
        intentbroadcast.putExtra("type", Notificaiton_pro.get(i).second.first);
        intentbroadcast.putExtra("info", Notificaiton_pro.get(i).second.second);
        return intentbroadcast;
    }


}
