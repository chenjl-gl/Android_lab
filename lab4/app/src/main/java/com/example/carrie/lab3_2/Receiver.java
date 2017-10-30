package com.example.carrie.lab3_2;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.Pair;
import android.widget.Toast;

/**
 * Created by carrie on 2017/10/28.
 */

public class Receiver extends BroadcastReceiver{
    private static final String STATICACTION = "com.example.MyStaticFilter";
    private static final String DYNAMICACTION = "com.example.MyDynamicFilter";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent){
        if (intent.getAction().equals(STATICACTION)){
            String item = intent.getStringExtra("notification_item");
            String price = intent.getStringExtra("notification_price");
            int img_src = intent.getIntExtra("notification_img",R.mipmap.pic_enchatedforest);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),img_src);
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("新商品热卖").setContentText(item+"仅售"+price)
                    .setTicker("您有一条新信息").setLargeIcon(bitmap)
                    .setSmallIcon(img_src).setAutoCancel(true);
            Intent mintent = new Intent(context,Showproduct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            mintent.putExtra("letter",intent.getStringExtra("letter"));
            mintent.putExtra("Price",price);
            mintent.putExtra("item",item);
            mintent.putExtra("Type",intent.getStringExtra("type"));
            mintent.putExtra("Info",intent.getStringExtra("info"));
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,mintent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(mPendingIntent);
            Notification notification = builder.build();
            manager.notify(1,notification);
        }
        else if(intent.getAction().equals(DYNAMICACTION)){
            String item = intent.getStringExtra("notification_item");
            String price = intent.getStringExtra("notification_price");
            int img_src = intent.getIntExtra("notification_img",R.mipmap.pic_enchatedforest);
            Pair<Pair<String,String>,Integer> no_info = Pair.create(Pair.create(item,price),img_src);
            assert no_info != null;
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),img_src);
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("马上下单").setContentText(item+"已添加到购物车")
                    .setTicker("您有一条新信息").setLargeIcon(bitmap)
                    .setSmallIcon(img_src).setAutoCancel(true);
            String letter = intent.getStringExtra("letter");
            Intent shopcarintent = new Intent(context,MainActivity.class);
            shopcarintent.putExtra("letter",letter);
            shopcarintent.putExtra("price",price);
            shopcarintent.putExtra("item",item);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,shopcarintent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(mPendingIntent);
            Notification notification = builder.build();
            manager.notify(1,notification);
        }
    }
}
