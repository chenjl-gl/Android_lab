package com.example.carrie.lab3_2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.Pair;
import android.text.Layout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {
    private static final String DYNAMICACTION = "com.example.MyWidgetDynamicFilter";
    private static final String STATICACTION = "com.example.MyWidgetStaticFilter";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.my_widget);
        Intent mintent = new Intent(context,MainActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,mintent,PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.widget,mPendingIntent);
        ComponentName componentName = new ComponentName(context,MyWidget.class);
        appWidgetManager.updateAppWidget(componentName,updateViews);
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        if(intent.getAction().equals(STATICACTION)){
            String item = intent.getStringExtra("notification_item");
            String price = intent.getStringExtra("notification_price");
            int img_src = intent.getIntExtra("notification_img",R.mipmap.pic_enchatedforest);
            RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.my_widget);
            Intent mintent = new Intent(context,Showproduct.class);
            mintent.addCategory(Intent.CATEGORY_DEFAULT);
            mintent.putExtra("letter",intent.getStringExtra("letter"));
            mintent.putExtra("Price",price);
            mintent.putExtra("item",item);
            mintent.putExtra("Type",intent.getStringExtra("type"));
            mintent.putExtra("Info",intent.getStringExtra("info"));
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,mintent,PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setTextViewText(R.id.appwidget_text,item+"仅售"+price+"!");
            updateViews.setImageViewResource(R.id.appwidget_image,img_src);
            updateViews.setOnClickPendingIntent(R.id.widget,mPendingIntent);
            ComponentName componentName = new ComponentName(context,MyWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componentName,updateViews);
        }
        else if(intent.getAction().equals(DYNAMICACTION)){
            String item = intent.getStringExtra("notification_item");
            String price = intent.getStringExtra("notification_price");
            int img_src = intent.getIntExtra("notification_img",R.mipmap.pic_enchatedforest);
            Pair<Pair<String,String>,Integer> no_info = Pair.create(Pair.create(item,price),img_src);
            assert no_info != null;
            RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.my_widget);
            Intent mintent = new Intent(context,MainActivity.class);
            mintent.addCategory(Intent.CATEGORY_DEFAULT);
            mintent.putExtra("letter",intent.getStringExtra("letter"));
            mintent.putExtra("Price",price);
            mintent.putExtra("item",item);
            mintent.putExtra("Type",intent.getStringExtra("type"));
            mintent.putExtra("Info",intent.getStringExtra("info"));
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,mintent,PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setTextViewText(R.id.appwidget_text,item+"已添加到购物车");
            updateViews.setImageViewResource(R.id.appwidget_image,img_src);
            updateViews.setOnClickPendingIntent(R.id.widget,mPendingIntent);
            ComponentName componentName = new ComponentName(context,MyWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componentName,updateViews);
        }
    }
}

