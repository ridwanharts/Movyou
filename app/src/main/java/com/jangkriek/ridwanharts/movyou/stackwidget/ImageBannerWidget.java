package com.jangkriek.ridwanharts.movyou.stackwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jangkriek.ridwanharts.movyou.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImageBannerWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.jangkriek.ridwanharts.TOAST_ACTION";
    public static final String EXTRA_ITEM= "com.jangkriek.ridwanharts.EXTRA_ITEM";
    public static final String UPDATE= "com.jangkriek.ridwanharts.UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent i = new Intent(context, StackWidgetService.class);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.image_banner_widget);
        views.setRemoteAdapter(R.id.stack_view, i);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastI = new Intent(context, ImageBannerWidget.class);
        toastI.setAction(ImageBannerWidget.TOAST_ACTION);
        toastI.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastI, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
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
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(TOAST_ACTION)){
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM,0);
            Toast.makeText(context,"Touched Movie "+viewIndex, Toast.LENGTH_SHORT).show();
        }

        super.onReceive(context, intent);
    }
}

