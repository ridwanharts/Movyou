package com.jangkriek.ridwanharts.movyou.stackwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jangkriek.ridwanharts.movyou.R;
import com.jangkriek.ridwanharts.movyou.favorit.DetilMovie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mcontext;
    private Cursor cursor;
    private int appWidgetId;


    public StackRemoteViewsFactory(Context context, Intent intent){
        mcontext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {
        if(cursor!=null){
            cursor.close();
        }
        long bind = Binder.clearCallingIdentity();
        cursor = mcontext.getContentResolver().query(CONTENT_URI,null,null,null,null);
        Binder.restoreCallingIdentity(bind);
    }

    @Override
    public void onDestroy() {
        if(cursor != null)cursor.close();

    }

    @Override
    public int getCount() {
        if(cursor==null)return 0;
        else return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(position)){
            return null;
        }
        DetilMovie itemList = getItem(position);
        RemoteViews remoteViews = new RemoteViews(mcontext.getPackageName(),R.layout.widget_item);

        Bitmap img = null;
        try {
            img = Glide.with(mcontext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w500/"+itemList.getmGambar())
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            remoteViews.setImageViewBitmap(R.id.imageWidget, img);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String judul = itemList.getmJudul();
        String tanggalRilis = itemList.getmTanggal();
        remoteViews.setTextViewText(R.id.judulWidget, judul);
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date tanggal = formatTanggal.parse(tanggalRilis);
            SimpleDateFormat formatBaru = new SimpleDateFormat("dd MMM yyyy");
            String rilis = formatBaru.format(tanggal);
            remoteViews.setTextViewText(R.id.tanggalWidget,rilis);

        }catch (ParseException p){
            p.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.imageWidget, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (cursor.moveToPosition(position)){
            return cursor.getLong(0);
        }else return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private DetilMovie getItem(int i){
        if (!cursor.moveToPosition(i)){
            throw new IllegalStateException("Invalid Position");
        }
        return new DetilMovie(cursor);
    }
}
