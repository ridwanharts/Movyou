package com.jangkriek.ridwanharts.movyou.favorit.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.jangkriek.ridwanharts.movyou.favorit.database.FavoriteHelper;

import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.AUTHORITY;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.CONTENT_URI;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE = 101;
    private static final int FAVORITE_ID = 102;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_NAME, FAVORITE);
        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_NAME+ "/#", FAVORITE_ID);
    }
    private FavoriteHelper favoriteHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = new FavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri,  String[] projection, String selection,  String[] selectionArgs,String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case FAVORITE:
                cursor = favoriteHelper.queryProvider();
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor!=null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long add;
        switch (uriMatcher.match(uri)){
            case FAVORITE:
                add = favoriteHelper.insertProvider(values);
                break;

            default:
                add = 0;
                break;
        }

        if (add>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI+"/"+add);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delete;
        switch (uriMatcher.match(uri)){
            case FAVORITE_ID:
                delete = favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;

            default:
                delete = 0;
                break;
        }

        if (delete>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values,String selection, String[] selectionArgs) {
        int update;
        switch (uriMatcher.match(uri)){
            case FAVORITE:
                update = favoriteHelper.updateProvider(uri.getLastPathSegment(),values);
                break;

            default:
                update = 0;
                break;
        }

        if (update>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return update;
    }
}
